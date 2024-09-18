package com.example.demo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.AdvancedSearchFormOfBcd;
import com.example.demo.form.AdvancedSearchFormOfCc;
import com.example.demo.form.AdvancedSearchFormOfIdb;
import com.example.demo.service.BreakdownCdService;
import com.example.demo.service.CategoryDetailService;
import com.example.demo.service.ConstructionContractService;
import com.example.demo.service.EstimateTypeService;
import com.example.demo.service.InformationDbService;
import com.example.demo.service.InformationItemService;
import com.example.demo.service.PurposeOutlineService;

import lombok.RequiredArgsConstructor;

/**
 * 詳細検索コントローラークラス
 */
@Controller
@RequestMapping("/advanced-search")
@RequiredArgsConstructor
public class AdvancedSearchController {

    /** メソッド認可メモ */
    // SecurityConfigにて下記のように設定してあるため、ADMIN以外このクラスは利用できない
    // 【管理者権限設定】URL:/employee/**は管理者しかアクセスできない
    // .requestMatchers("/employee/**").hasAuthority("ADMIN")
    // よって、@PreAuthorize("hasAuthority('ADMIN')")によるメソッド認可は指定しない

    /** 【DI】 */
    // @RequiredArgsConstructorによりfinalで修飾されたフィールドだけを引数に受け取るコンストラクタを自動生成する
    // これにより「@Autowired」を使ったコンストラクタインジェクションの記述は不要となる
    private final EstimateTypeService estimateTypeService;
    private final CategoryDetailService categoryDetailService;
    private final PurposeOutlineService purposeOutlineService;
    private final ConstructionContractService constructionContractService;
    private final BreakdownCdService breakdownCdService;
    private final InformationItemService informationItemService;
    private final InformationDbService informationDbService;
    /** 【検索画面表示】 */
    @GetMapping("/search")
    public String search(@ModelAttribute AdvancedSearchFormOfCc formOfCc,
                         @ModelAttribute AdvancedSearchFormOfBcd formOfBcd,
                         @ModelAttribute AdvancedSearchFormOfIdb formOfIdb,
                         Model model) {

        // 検索処理用フォームクラス（AdvancedSearchFormOfCc）を織り込んで一覧画面へ遷移する。
        // @ModelAttributeの引数省略型を利用しているため、下記のように、Model名はクラス名のローワーキャメルケースとなる
        // model.addAttribute("advancedSearchFormOfCc", form); →form.htmlへ引き継ぐModel名となる

        /** 内訳種別区分設定Mapを取得 */
        Map<String, Integer> estimateTypeMap = estimateTypeService.getEstimateTypeMap();
        // Modelに格納
        model.addAttribute("estimateTypeMap", estimateTypeMap);

        /** 内訳種目区分設定Mapを取得 */
        Map<String, Integer> categoryDetailMap = categoryDetailService.getCategoryDetailMap();
        // Modelに格納
        model.addAttribute("categoryDetailMap", categoryDetailMap);

        /** 用途概略区分設定Mapを取得 */
        Map<String, Integer> purposeOutlineMap = purposeOutlineService.getPurposeOutlineMap();
        // Modelに格納
        model.addAttribute("purposeOutlineMap", purposeOutlineMap);

        /** 内訳情報区分設定Mapを取得 */
        Map<String, Integer> informationItemlMap = informationItemService.getInformationItemMap();
        // Modelに格納
        model.addAttribute("informationItemlMap", informationItemlMap);

        /** 検索画面へ遷移 */
        // 画面遷移（アドレス指定）
        return "advanced-search/search";

    }

    /** 【検索処理実行（工事契約用）（GETメソッドで実装　※POSTメソッドでは戻る時にフォームの再送を求められる）】 */
    @GetMapping("/result-of-cc")
    public String getResultOfCc(AdvancedSearchFormOfCc form, Model model) {

        /** 検索結果画面へ遷移 */
        // Modelに格納
        model.addAttribute("constructionContract", constructionContractService.findAllByAdvancedSearchForm(
                form.getCcEtId(),
                form.getContractCondition(),
                form.getEstimateYear(),
                form.getSiteAddress(),
                form.getContractorName()
                ));
        model.addAttribute("listSize", constructionContractService.findAllByAdvancedSearchForm(
                form.getCcEtId(),
                form.getContractCondition(),
                form.getEstimateYear(),
                form.getSiteAddress(),
                form.getContractorName()
                ).size());
        // 画面遷移（アドレス指定）
        return "advanced-search/result-of-cc";

    }

    /** 【検索処理実行（内訳種目用）（GETメソッドで実装　※POSTメソッドでは戻る時にフォームの再送を求められる）】 */
    @GetMapping("/result-of-bcd")
    public String getResultOfBcd(AdvancedSearchFormOfBcd form, Model model) {

        /** 検索結果画面へ遷移 */
        // Modelに格納
        model.addAttribute("breakdownCd", breakdownCdService.findAllByAdvancedSearchForm(
                form.getBcdCdId(),
                form.getBcdPdId(),
                form.getBcdTypeName()
                ));
        model.addAttribute("listSize", breakdownCdService.findAllByAdvancedSearchForm(
                form.getBcdCdId(),
                form.getBcdPdId(),
                form.getBcdTypeName()
                ).size());
        // 画面遷移（アドレス指定）
        return "advanced-search/result-of-bcd";

    }

    /** 【検索処理実行（内訳情報用）（GETメソッドで実装　※POSTメソッドでは戻る時にフォームの再送を求められる）】 */
    @GetMapping("/result-of-idb")
    public String getResultOfBcd(AdvancedSearchFormOfIdb form, Model model) {

        /** 検索結果画面へ遷移 */
        // Modelに格納
        model.addAttribute("informationDb", informationDbService.findAllByAdvancedSearchForm(
                form.getIdbIiId(),
                form.getIdbDataText(),
                form.getUpperIdbDataDouble(),
                form.getLowerIdbDataDouble()
                ));
        model.addAttribute("listSize", informationDbService.findAllByAdvancedSearchForm(
                form.getIdbIiId(),
                form.getIdbDataText(),
                form.getUpperIdbDataDouble(),
                form.getLowerIdbDataDouble()
                ).size());
        // 画面遷移（アドレス指定）
        return "advanced-search/result-of-idb";

    }

}