package com.example.demo.controller;

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
import com.example.demo.entity.DesignContract;
import com.example.demo.form.DesignContractForm;
import com.example.demo.helper.DesignContractHelper;
import com.example.demo.service.DesignContractService;
import com.example.demo.service.impl.LoginUserDetails;

import lombok.RequiredArgsConstructor;

/**
* 設計契約コントローラークラス
*/
@Controller
@RequestMapping("/design-contract")
@RequiredArgsConstructor
public class DesignContractController {

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
    private final DesignContractService service;
    // 他テーブルのデータを取得するため、他テーブルを扱うサービインターフェスをDI

    /** 【全件取得】 */
    @GetMapping("/list")
    public String list(Model model) {

        /** 一覧画面へ遷移 */
        // Modelに格納
        model.addAttribute("designContract", service.findAll());
        // 画面遷移（アドレス指定）
        return "design-contract/list";

    }

    /**　【一件取得】 */
    @GetMapping("/{id}/detail")
    public String detail(@PathVariable("id") Integer dcId,
            Model model, RedirectAttributes redirectAttributes) {

        /** 詳細画面へ遷移 */
        // GETメソッドでid入力可能のため、URLでidを直入力された場合の、対象データの有無チェックを行う
        // 対象データを取得
        DesignContract targetDesignContract = service.findById(dcId);
        // 対象データの有無確認
        if (targetDesignContract != null) {
            // 対象データがある場合
            // Modelに格納
            model.addAttribute("designContract", service.findById(dcId));
            // 画面遷移（アドレス指定）
            return "design-contract/detail";
        } else {
            // 対象データがない場合
            //　エラーのフラッシュメッセージをRedirectAttributesに格納
            redirectAttributes.addFlashAttribute("errorMessage", "対象データがありません");
            // リダイレクト（アドレス指定）
            return "redirect:/design-contract/list";
        }

    }

    /** 【登録画面表示】　*/
    @GetMapping("/create")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String create(@ModelAttribute DesignContractForm form) {

        // @ModelAttributeの引数省略型を利用しているため、下記のように、Model名はクラス名のローワーキャメルケースとなる
        // model.addAttribute("designContractForm", form);　→form.htmlへ引き継ぐModel名となる
        // 更新画面表示・更新処理実行のメソッドにおいても上記と同様のModel名とする

        /** 登録画面へ遷移 */
        // 登録画面としてform.htmlが実行されるよう設定
        form.setIsNew(true);
        // 画面遷移（アドレス指定）
        return "design-contract/form";

    }

    /** 【登録処理実行】 */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String add(@Validated DesignContractForm form, BindingResult bindingRusult,
            Model model, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal LoginUserDetails loginUserDetails) {

        /** Entityクラスによる入力チェック　*/
        if (bindingRusult.hasErrors()) {
            // 入力チェックにエラーがあるため登録画面へ遷移してエラー内容を表示させる
            // 画面遷移（メソッド指定）
            return create(form);
        }

        /** 登録処理実行（ErrorKindsクラスによる入力チェック共） */
        // FormクラスからEntityクラスへ変換
        DesignContract entity = DesignContractHelper.convertEntity(form);
        // 登録処理をしてErrorKindsクラスで定義された種別の結果を受け取る
        ErrorKinds result = service.insert(entity, loginUserDetails);
        // ErrorMessageクラスで定義されたエラーが含まれていれば詳細画面に遷移してエラーメッセージを表示する
        if (ErrorMessage.contains(result)) {
            // エラーメッセージをModelに格納
            model.addAttribute(ErrorMessage.getErrorName(result),
                               ErrorMessage.getErrorValue(result));
            // 画面遷移（メソッド指定）
            return create(form);
        }
        // フラッシュメッセージをRedirectAttributesに格納
        redirectAttributes.addFlashAttribute("message", "新しいデータが作成されました");
        // PRGパターン：リダイレクト（アドレス指定）
        return "redirect:/design-contract/list";

    }

    /** 【更新画面表示】 */
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String edit(@PathVariable("id") Integer dcId,
            Model model, RedirectAttributes redirectAttributes) {

        /** 更新処理実行時入力チェックからのエラーメッセージ表示処理　*/
        // idがnullの場合は更新処理実行時の入力チェックでひっかかったため再度form.htmlへ遷移する
        if(dcId == null) {
            // 画面遷移（アドレス指定）
            return "design-contract/form";
        }

        /** 更新画面へ遷移 */
        // GETメソッドでid入力可能のため、URLでidを直入力された場合の、対象データの有無チェックを行う
        // 対象データを取得
        DesignContract targetDesignContract = service.findById(dcId);
        // 対象データの有無確認
        if (targetDesignContract != null) {
            // 対象データがある
            // EntityクラスからFormクラスへ変換
            DesignContractForm form = DesignContractHelper.convertForm(targetDesignContract);
            // Modelに格納
            //　登録画面表示の@ModelAttribute引数省略型に合せ、Model名はFormクラス名のローワーキャメルケースとする
            model.addAttribute("designContractForm", form);
            // 更新画面としてform.htmlが実行されるよう設定
            form.setIsNew(false);
            // 画面遷移（アドレス指定）
            return "design-contract/form";
        } else {
            // 対象データがない場合
            // エラーのフラッシュメッセージをRedirectAttributesに格納
            redirectAttributes.addFlashAttribute("errorMessage", "対象データがありません");
            // リダイレクト（アドレス指定）
            return "redirect:/design-contract/list";
        }

    }

    /**　【更新処理実行】 */
    @PostMapping("/{id}/revice")
    @PreAuthorize("hasAuthority('EDITOR')")
    public String revice(@PathVariable("id") Integer dcId,
            @Validated DesignContractForm form, BindingResult bindingRusult,
            Model model, RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal LoginUserDetails loginUserDetails) {

        /** Entityクラスによる入力チェック　*/
        if (bindingRusult.hasErrors()) {
            // 入力チェックにエラーがあるため更新画面へ遷移してエラー内容を表示させる
            // Modelに格納
            //　登録画面表示の@ModelAttribute引数省略型に合せ、Model名はFormクラス名のローワーキャメルケースとする
            model.addAttribute("designContractForm", form);
            // 画面遷移（メソッド指定）
            return edit(null, model, redirectAttributes);
        }

        /** 更新処理実行（ErrorKindsクラスによる入力チェック共） */
        // FormクラスからEntityクラスへ変換
        DesignContract targetDesignContract = DesignContractHelper.convertEntity(form);
        // 更新処理をしてErrorKindsクラスで定義された種別の結果を受け取る
        ErrorKinds result = service.update(targetDesignContract, loginUserDetails);
        // ErrorMessageクラスで定義されたエラーが含まれていれば詳細画面に遷移してエラーメッセージを表示する
        if (ErrorMessage.contains(result)) {
            // エラーメッセージをModelに格納
            model.addAttribute(ErrorMessage.getErrorName(result),
                               ErrorMessage.getErrorValue(result));
            // 更新画面へ引き継ぐデータをModelに格納
            model.addAttribute("designContractForm", service.findById(dcId));
            // 画面遷移（メソッド指定）
            return edit(dcId, model, redirectAttributes);
        }
        // フラッシュメッセージをRedirectAttributesに格納
        redirectAttributes.addFlashAttribute("message", "データが更新されました");
        // PRGパターン：リダイレクト（アドレス指定）
        return "redirect:/design-contract/list";

    }

    /** 【削除処理実行】 */
    @PostMapping("/{id}/remove")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String remove(@PathVariable("id") Integer dcId,
            Model model, RedirectAttributes redirectAttributes) {

        /** 削除処理実行（ErrorKindsクラスによる入力チェック共） */
        // 削除処理をしてErrorKindsクラスで定義された種別の結果を受け取る
        ErrorKinds result = service.delete(dcId);
        // ErrorMessageクラスで定義されたエラーが含まれていれば詳細画面に遷移してエラーメッセージを表示する
        if (ErrorMessage.contains(result)) {
            // エラーメッセージをModelに格納
            model.addAttribute(ErrorMessage.getErrorName(result),
                               ErrorMessage.getErrorValue(result));
            // 詳細画面へ引き継ぐデータをModelに格納
            model.addAttribute("designContractForm", service.findById(dcId));
            // 画面遷移（メソッド指定）
            return detail(dcId, model, redirectAttributes);
        }
        // フラッシュメッセージをRedirectAttributesに格納
        redirectAttributes.addFlashAttribute("message", "データが削除されました（論理削除）");
        // PRGパターン：リダイレクト（アドレス指定）
        return "redirect:/design-contract/list";

    }

}