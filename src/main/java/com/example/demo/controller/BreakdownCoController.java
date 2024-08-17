package com.example.demo.controller;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.constraints.ErrorKinds;
import com.example.demo.constraints.ErrorMessage;
import com.example.demo.entity.BreakdownCo;
import com.example.demo.entity.ConstructionContract;
import com.example.demo.form.BreakdownCoForm;
import com.example.demo.helper.BreakdownCoHelper;
import com.example.demo.service.BreakdownCoService;
import com.example.demo.service.CategoryOutlineService;
import com.example.demo.service.ConstructionContractService;
import com.example.demo.service.impl.LoginUserDetails;

import lombok.RequiredArgsConstructor;

/**
* 内訳頭紙コントローラークラス
*/
@Controller
@RequestMapping("/breakdown-co")
@RequiredArgsConstructor
public class BreakdownCoController {

    /** メソッド認可メモ */
    // 下記メソッドには@PreAuthorize("hasAuthority('EDITOR')")を付し、編集者権限を有するユーザーのみ実行可能とする
    // 　【登録画面表示】、【登録処理実行】、【更新画面表示】、【更新処理実行】
    // 下記メソッドには@PreAuthorize("hasAuthority('ADMIN')")を付し、管理者権限を有するユーザーのみ実行可能とする
    // 　【削除処理実行】
    // これらのメソッド認可を設定しておかないと、URLにメソッド名で実行可能となってしまう。よって、権限による認可を付す
    // 権限のないユーザーがURLにメソッド名を書いてで実行すると、405エラーが発生し、405.htmlに画面遷移する

    /** 【DI】 */
    // @RequiredArgsConstructorによりfinalで修飾されたフィールドだけを引数に受け取るコンストラクタを自動生成する
    // これにより「@Autowired」を使ったコンストラクタインジェクションの記述は不要となる
    private final BreakdownCoService service;
    // 他テーブルのデータを取得するため、他テーブルを扱うサービインターフェスをDI
    private final ConstructionContractService constructionContractService;
    private final CategoryOutlineService categoryOutlineService;

    /** 【特定取得】 */
    @GetMapping("/{id}/specify")
    public String specify(@PathVariable("id") Integer bcoCcId,
            Model model, RedirectAttributes redirectAttributes) {

        /** ローカルフィールド定義、及び、初期化 */
        Long longDirectConstructionPrice = null;     // 直接工事費
        Long longCommonExpensePrice = null;          // 共通費
        Long longTotalConstructionPrice = null;      // 工事価格
        Long longTaxPrice = null;                    // 消費税相当額
        Long longTotalPriceWithTax = null;           // 工事費（税込）
        Long longSumDirectConstructionPrice = null;  // 「建築+電気設備+機械設備+昇降機設備」の合計
        Long longSumCommonExpensePrice = null;       // 「共通仮設費+現場管理費+一般管理費等」の合計

        /** 現在表示している工事契約の「直接工事費」を取得 */
        // 対象データを取得
        BreakdownCo directConstructionPrice = service.priceFindById(bcoCcId, (Integer)1050);
        // 対象データの有無確認
        if (directConstructionPrice != null) {
            // 対象データがある場合
            // ローカルフィールドに格納
            longDirectConstructionPrice = directConstructionPrice.getBcoPrice();
        } else {
            // 対象データがない場合
            //Nullの場合はゼロを代入して、以下の計算でエラーが出ない様にする
            longDirectConstructionPrice = 0L;
        }

        /** 現在表示している工事契約の「共通費」を取得 */
        // 対象データを取得
        BreakdownCo commonExpensePrice = service.priceFindById(bcoCcId, (Integer)1090);
        // 対象データの有無確認
        if (commonExpensePrice != null) {
            // 対象データがある場合
            // ローカルフィールドに格納
            longCommonExpensePrice = commonExpensePrice.getBcoPrice();
        } else {
            // 対象データがない場合
            //Nullの場合はゼロを代入して、以下の計算でエラーが出ない様にする
            longCommonExpensePrice = 0L;
        }

        /** 現在表示している工事契約の「工事価格」を取得 */
        // 対象データを取得
        BreakdownCo totalConstructionPrice = service.priceFindById(bcoCcId, (Integer)1100);
        // 対象データの有無確認
        if (totalConstructionPrice != null) {
            // 対象データがある場合
            // ローカルフィールドに格納
            longTotalConstructionPrice = totalConstructionPrice.getBcoPrice();
        } else {
            // 対象データがない場合
            //Nullの場合はゼロを代入して、以下の計算でエラーが出ない様にする
            longTotalConstructionPrice = 0L;
        }

        /** 現在表示している工事契約の「消費税相当額」を取得 */
        // 対象データを取得
        BreakdownCo taxPrice = service.priceFindById(bcoCcId, (Integer)1110);
        // 対象データの有無確認
        if (taxPrice != null) {
            // 対象データがある場合
            // ローカルフィールドに格納
            longTaxPrice = taxPrice.getBcoPrice();
        } else {
            // 対象データがない場合
            //Nullの場合はゼロを代入して、以下の計算でエラーが出ない様にする
            longTaxPrice = 0L;
        }

        /** 現在表示している工事契約の「工事費」を取得 */
        // 対象データを取得
        BreakdownCo totalPriceWithTax = service.priceFindById(bcoCcId, (Integer)1120);
        // 対象データの有無確認
        if (totalPriceWithTax != null) {
            // 対象データがある場合
            // ローカルフィールドに格納
            longTotalPriceWithTax = totalPriceWithTax.getBcoPrice();
        } else {
            // 対象データがない場合
            //Nullの場合はゼロを代入して、以下の計算でエラーが出ない様にする
            longTotalPriceWithTax = 0L;
        }

        /** 「直接工事費－（建築+電気設備+機械設備+昇降機設備）」の検算結果を取得 */
        // 現在表示している工事契約の「建築+電気設備+機械設備+昇降機設備」の合計金額を取得
        // 対象データを取得
        BreakdownCo sumDirectConstructionPrice = service.findSumById(bcoCcId, (Integer)1010, (Integer)1040);
        // 対象データの有無確認
        if (sumDirectConstructionPrice != null) {
            // 対象データがある場合
            // ローカルフィールドに格納
            longSumDirectConstructionPrice = sumDirectConstructionPrice.getSumBcoPrice();
        } else {
            // 対象データがない場合
            //Nullの場合はゼロを代入して、以下の計算でエラーが出ない様にする
            longSumDirectConstructionPrice = 0L;
        }
        // 上記合計金額より直接工事費を減算して差額を算出
        Long defDirectConstructionPrice = longSumDirectConstructionPrice - longDirectConstructionPrice;
        // Modelに格納
        model.addAttribute("defDirectConstructionPrice", defDirectConstructionPrice);

        /** 「共通費－（共通仮設費+現場管理費+一般管理費等）」の検算結果を取得 */
        // 現在表示している工事契約の「共通仮設費+現場管理費+一般管理費等」の合計金額を取得
        BreakdownCo sumCommonExpensePrice = service.findSumById(bcoCcId, (Integer)1060, (Integer)1080);
        // 対象データの有無確認
        if (sumCommonExpensePrice != null) {
            // 対象データがある場合
            // ローカルフィールドに格納
            longSumCommonExpensePrice = sumCommonExpensePrice.getSumBcoPrice();
        } else {
            // 対象データがない場合
            //Nullの場合はゼロを代入して、以下の計算でエラーが出ない様にする
            longSumCommonExpensePrice = 0L;
        }
        // 上記合計金額より直接工事費を減算して差額を算出
        Long defSumCommonExpensePrice = longSumCommonExpensePrice - longCommonExpensePrice;
        // Modelに格納
        model.addAttribute("defSumCommonExpensePrice", defSumCommonExpensePrice);

        /** 「工事価格－（直接工事費+共通費）」の検算結果を取得 */
        // 上記合計金額より工事価格から直接工事費と共通費を減算して差額を算出
        Long defTotalConstructionPrice = longTotalConstructionPrice -
                ( longDirectConstructionPrice + longCommonExpensePrice);
        // Modelに格納
        model.addAttribute("defTotalConstructionPrice", defTotalConstructionPrice);

        /** 「工事費－（工事価格+消費税相当額）」の検算結果を取得 */
        // 上記合計金額より工事費から工事価格と消費税相当額を減算して差額を算出
        // 対象データを取得
        Long defTotalPriceWithTax = longTotalPriceWithTax -
                ( longTotalConstructionPrice + longTaxPrice);
        // Modelに格納
        model.addAttribute("defTotalPriceWithTax", defTotalPriceWithTax);

        /** 特定画面へ遷移 */
        // GETメソッドでid入力可能のため、URLでidを直入力された場合の、対象データの有無チェックを行う
        // 対象データを取得
        ConstructionContract targetConstructionContract = constructionContractService.findById(bcoCcId);
        // 対象データの有無確認
        if (targetConstructionContract != null) {
            // 対象データがある場合
            // Modelに格納
            model.addAttribute("projectName", constructionContractService.findById(bcoCcId).getProjectName());
            model.addAttribute("breakdownCo", service.findAllById(bcoCcId,
                    longDirectConstructionPrice, longTotalConstructionPrice));
            model.addAttribute("ccId", bcoCcId);
            // 画面遷移（アドレス指定）
            return "breakdown-co/specify";
        } else {
            // 対象データがない場合
            //　エラーのフラッシュメッセージをRedirectAttributesに格納
            redirectAttributes.addFlashAttribute("errorMessage", "対象データがありません");
            // リダイレクト（アドレス指定）
            return "redirect:/construction-contract/list";
        }

    }

    /**　【一件取得】 */
    @GetMapping("/{id1}/{id2}/detail")
    public String detail(@PathVariable("id1") Integer bcoId,
                         @PathVariable("id2") Integer bcoCcId,
            Model model, RedirectAttributes redirectAttributes) {

        /** 詳細画面へ遷移 */
        // GETメソッドでid入力可能のため、URLでidを直入力された場合の、対象データの有無チェックを行う
        // 対象データを取得
        BreakdownCo targetBreakdownCo = service.findById(bcoId);
        // 対象データの有無確認
        if (targetBreakdownCo != null) {
            // 対象データがある場合
            // Modelに格納
            model.addAttribute("breakdownCo", service.findById(bcoId));
            // 画面遷移（アドレス指定）
            return "breakdown-co/detail";
        } else {
            // 対象データがない場合
            //　エラーのフラッシュメッセージをRedirectAttributesに格納
            redirectAttributes.addFlashAttribute("errorMessage", "対象データがありません");
            // リダイレクト（アドレス指定）
            return "redirect:/breakdown-co/" + bcoCcId + "/specify";
        }

    }

    /** 【登録画面表示】　*/
    @GetMapping("/{id}/create")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String create(@PathVariable("id") Integer bcoCcId,
            @ModelAttribute BreakdownCoForm form,
            Model model, RedirectAttributes redirectAttributes) {

        // @ModelAttributeの引数省略型を利用しているため、下記のように、Model名はクラス名のローワーキャメルケースとなる
        // model.addAttribute("breakdownCoForm", form);　→form.htmlへ引き継ぐModel名となる
        // 更新画面表示・更新処理実行のメソッドにおいても上記と同様のModel名とする

        /** 内訳頭紙区分設定Mapを取得 */
        Map<String, Integer> categoryOutlineMap = categoryOutlineService.getCategoryOutlineMap();
        // Modelに格納
        model.addAttribute("categoryOutlineMap", categoryOutlineMap);

        /** 登録画面へ遷移 */
        // GETメソッドでid入力可能のため、URLでidを直入力された場合の、対象データの有無チェックを行う
        // 対象データを取得
        ConstructionContract targetConstructionContract = constructionContractService.findById(bcoCcId);
        // 対象データの有無確認
        if (targetConstructionContract != null) {
            // 対象データがある場合
            // form.htmlに引き継ぐべきパラメータをformに格納
            form.setConstructionContract(targetConstructionContract);
            form.setBcoCcId(bcoCcId);
            // 登録画面としてform.htmlが実行されるよう設定
            form.setIsNew(true);
            // 画面遷移（アドレス指定）
            return "breakdown-co/form";
        } else {
            // 対象データがない場合
            //　エラーのフラッシュメッセージをRedirectAttributesに格納
            redirectAttributes.addFlashAttribute("errorMessage", "対象データがありません");
            // ダイレクト（アドレス指定）
            return "redirect:/breakdown-co/" + bcoCcId + "/specify";
        }

    }

    /** 【登録処理実行】 */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String add(@Validated BreakdownCoForm form, BindingResult bindingRusult,
            Model model, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal LoginUserDetails loginUserDetails) {

        /** 引き継ぐべきパラメータをformより取得 */
        Integer bcoCcId = form.getBcoCcId();

        /** Entityクラスによる入力チェック　*/
        if (bindingRusult.hasErrors()) {
            // 入力チェックにエラーがあるため登録画面へ遷移してエラー内容を表示させる
            // 画面遷移（メソッド指定）
            return create(bcoCcId, form, model, redirectAttributes);
        }

        /** 登録処理実行（ErrorKindsクラスによる入力チェック共） */
        // FormクラスからEntityクラスへ変換
        BreakdownCo entity = BreakdownCoHelper.convertEntity(form);
        // 登録処理をしてErrorKindsクラスで定義された種別の結果を受け取る
        ErrorKinds result = service.insert(entity, loginUserDetails);
        // ErrorMessageクラスで定義されたエラーが含まれていれば詳細画面に遷移してエラーメッセージを表示する
        if (ErrorMessage.contains(result)) {
            // エラーメッセージをModelに格納
            model.addAttribute(ErrorMessage.getErrorName(result),
                               ErrorMessage.getErrorValue(result));
            // 画面遷移（メソッド指定）
            return create(bcoCcId, form, model, redirectAttributes);
        }
        // フラッシュメッセージをRedirectAttributesに格納
        redirectAttributes.addFlashAttribute("message", "新しいデータが作成されました");
        // PRGパターン：リダイレクト（アドレス指定）
        return "redirect:/breakdown-co/" + bcoCcId + "/specify";

    }

    /** 【更新画面表示】 */
    @GetMapping("/{id1}/{id2}/edit")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String edit(@PathVariable("id1") Integer bcoId,
                       @PathVariable("id2") Integer bcoCcId,
            Model model, RedirectAttributes redirectAttributes) {

        /** 更新処理実行時入力チェックからのエラーメッセージ表示処理　*/
        // idがnullの場合は更新処理実行時の入力チェックでひっかかったため再度更新画面へ遷移する
        if(bcoId == null) {
            // 画面遷移（アドレス指定）
            return "breakdown-co/form";
        }

        /** 内訳頭紙区分設定Mapを取得 */
        Map<String, Integer> categoryOutlineMap = categoryOutlineService.getCategoryOutlineMap();
        // Modelに格納
        model.addAttribute("categoryOutlineMap", categoryOutlineMap);

        /** 更新画面へ遷移 */
        // GETメソッドでid入力可能のため、URLでidを直入力された場合の、対象データの有無チェックを行う
        // 対象データを取得
        BreakdownCo targetBreakdownCo = service.findById(bcoId);
        // 対象データの有無確認
        if (targetBreakdownCo != null) {
            // 対象データがある場合
            // EntityクラスからFormクラスへ変換
            BreakdownCoForm form = BreakdownCoHelper.convertForm(targetBreakdownCo);
            // Modelに格納
            //　登録画面表示の@ModelAttribute引数省略型に合せ、Model名はFormクラス名のローワーキャメルケースとする
            model.addAttribute("breakdownCoForm", form);
            // form.htmlに引き継ぐべきパラメータをformに格納
            form.setBcoCcId(bcoCcId);
            // 更新画面としてform.htmlが実行されるよう設定
            form.setIsNew(false);
            // 画面遷移（アドレス指定）
            return "breakdown-co/form";
        } else {
            // 対象データがない場合
            // エラーのフラッシュメッセージをRedirectAttributesに格納
            redirectAttributes.addFlashAttribute("errorMessage", "対象データがありません");
            // リダイレクト（アドレス指定）
            return "redirect:/breakdown-co/" + bcoCcId +"/specify";
        }

    }

    /**　【更新処理実行】 */
    @PostMapping("/{id1}/{id2}/revice")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String revice(@PathVariable("id1") Integer bcoId,
                         @PathVariable("id2") Integer bcoCcId,
            @Validated BreakdownCoForm form, BindingResult bindingRusult,
            Model model, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal LoginUserDetails loginUserDetails) {

        /** Entityクラスによる入力チェック　*/
        if (bindingRusult.hasErrors()) {
            // 入力チェックにエラーがあるため更新画面へ遷移してエラー内容を表示させる
            // Modelに格納
            //　登録画面表示の@ModelAttribute引数省略型に合せ、Model名はFormクラス名のローワーキャメルケースとする
            model.addAttribute("breakdownCoForm", form);
            // 画面遷移（メソッド指定）
            return edit(null, bcoCcId, model, redirectAttributes);
        }

        /** 更新処理実行（ErrorKindsクラスによる入力チェック共） */
        // FormクラスからEntityクラスへ変換
        BreakdownCo targetBreakdownCo = BreakdownCoHelper.convertEntity(form);
        // 更新処理をしてErrorKindsクラスで定義された種別の結果を受け取る
        ErrorKinds result = service.update(targetBreakdownCo, loginUserDetails);
        // ErrorMessageクラスで定義されたエラーが含まれていれば詳細画面に遷移してエラーメッセージを表示する
        if (ErrorMessage.contains(result)) {
            // エラーメッセージをModelに格納
            model.addAttribute(ErrorMessage.getErrorName(result),
                               ErrorMessage.getErrorValue(result));
            // 更新画面へ引き継ぐデータをModelに格納
            model.addAttribute("breakdownCoForm", service.findById(bcoId));
            // 画面遷移（メソッド指定）
            return edit(bcoId, bcoCcId, model, redirectAttributes);
        }
        // フラッシュメッセージをRedirectAttributesに格納
        redirectAttributes.addFlashAttribute("message", "データが更新されました");
        // PRGパターン：リダイレクト（アドレス指定）
        return "redirect:/breakdown-co/" + bcoCcId +"/specify";

    }

    /** 【削除処理実行】 */
    @PostMapping("/{id1}/{id2}/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String remove(@PathVariable("id1") Integer bcoId,
                         @PathVariable("id2") Integer bcoCcId,
            Model model, RedirectAttributes redirectAttributes) {

        /** 削除処理実行（ErrorKindsクラスによる入力チェック共） */
        // 削除処理をしてErrorKindsクラスで定義された種別の結果を受け取る
        ErrorKinds result = service.delete(bcoId);
        // ErrorMessageクラスで定義されたエラーが含まれていれば詳細画面に遷移してエラーメッセージを表示する
        if (ErrorMessage.contains(result)) {
            // エラーメッセージをModelに格納
            model.addAttribute(ErrorMessage.getErrorName(result),
                               ErrorMessage.getErrorValue(result));
            // 詳細画面へ引き継ぐデータをModelに格納
            model.addAttribute("breakdownCoForm", service.findById(bcoId));
            // 画面遷移（メソッド指定）
            return edit(bcoId, bcoCcId, model, redirectAttributes);
        }
        // フラッシュメッセージをRedirectAttributesに格納
        redirectAttributes.addFlashAttribute("message", "データが削除されました（論理削除）");
        // PRGパターン：リダイレクト（アドレス指定）
        return "redirect:/breakdown-co/" + bcoCcId + "/specify";

    }

}