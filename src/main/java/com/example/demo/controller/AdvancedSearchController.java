package com.example.demo.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.form.AdvancedSearchFormOfCc;
import com.example.demo.form.SimpleSearchForm;
import com.example.demo.service.ConstructionContractService;
import com.example.demo.service.EstimateTypeService;

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
    private final ConstructionContractService constructionContractService;

    /** 【検索画面表示】 */
    @GetMapping("/search")
    public String search(@ModelAttribute AdvancedSearchFormOfCc form, Model model) {

        // 検索処理用フォームクラス（AdvancedSearchFormOfCc）を織り込んで一覧画面へ遷移する。
        // @ModelAttributeの引数省略型を利用しているため、下記のように、Model名はクラス名のローワーキャメルケースとなる
        // model.addAttribute("advancedSearchFormOfCc", form); →form.htmlへ引き継ぐModel名となる

        /** 内訳種別区分設定Mapを取得 */
        Map<String, Integer> estimateTypeMap = estimateTypeService.getEstimateTypeMap();
        // Modelに格納
        model.addAttribute("estimateTypeMap", estimateTypeMap);

        /** 検索画面へ遷移 */
        // Modelに格納

        // 画面遷移（アドレス指定）
        return "advanced-search/search";

    }

    /** 【検索処理実行（工事契約用）】 */
    @PostMapping("/result-of-cc")
    public String getResultOfCc(AdvancedSearchFormOfCc form, Model model) {

        /** 検索結果画面へ遷移 */
        // Modelに格納
        model.addAttribute("constructionContract", constructionContractService.findAllByAdvancedSearchForm(
                form.getCcEtId(),
                form.getEstimateYear(),
                form.getSiteAddress(),
                form.getContractorName()
                ));
        model.addAttribute("listSize", constructionContractService.findAllByAdvancedSearchForm(
                form.getCcEtId(),
                form.getEstimateYear(),
                form.getSiteAddress(),
                form.getContractorName()
                ).size());
        // 画面遷移（アドレス指定）
        return "advanced-search/result-of-cc";

    }

}