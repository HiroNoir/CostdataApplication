package com.example.demo.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 内訳情報エンティティクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformationDb {

    /**
     * 複合主キー1
     * 内訳種目ID
     */
    private Integer idbBcdId;

    /**
     * 工事契約
     * 工事契約テーブル連携用エンティティ（SQLのSELECT文で利用）
     */
    private ConstructionContract constructionContract;

    /**
     * 内訳頭紙区分
     * 内訳頭紙区分設定テーブル連携用エンティティ（SQLのSELECT文で利用）
     */
    private CategoryOutline categoryOutline;

    /**
     * 内訳種目区分
     * 内訳頭紙区分設定テーブル連携用エンティティ（SQLのSELECT文で利用）
     */
    private CategoryDetail categoryDetail;

    /**
     * 内訳頭紙
     * 工事契約テーブル連携用エンティティ（SQLのSELECT文で利用）
     */
    private BreakdownCo breakdownCo;

    /**
     * 内訳種目
     * 内訳種目テーブルとの連携用エンティティ（SQLのSELECT文で利用）
     */
    private BreakdownCd breakdownCd;

    /**
     * 複合主キー2
     * 内訳情報区分 上段：データベース保存用エンティティ（SQLのINSERT文・UPDATE文で利用）
     * 下段：内訳頭紙区分設定テーブル連携用エンティティ（SQLのSELECT文で利用）
     */
    private Integer idbIiId;
    private InformationItem informationItem;

    /** 文字情報 */
    private String idbDataText;

    /** 数量情報 */
    private Double idbDataDouble;

    /** 金額情報 */
    private Long idbDataBigint;

    /**
     * 合計金額 ※建築+電気設備+機械設備+昇降機設備（分析用）
     * ※データベース保存用エンティティではないが、Mapper.xmlのSQLで 計算した値を格納して、specify.htmlで表示されるために利用する。
     * よって、FormクラスやHelperクラスではこのエンティティは不要とする。
     */
    private Long sumIdbPrice;

    /**
     * ㎡単価
     * ※データベース保存用エンティティではないが、Mapper.xmlのSQLで計算した値を格納して、specify.htmlで表示されるために利用する。
     * よって、FormクラスやHelperクラスではこのエンティティは不要とする。
     */
    private String idbUnitPricePerSquareMeter;

    /**
     * 割合（種目別の対直接工事費合計）
     * ※データベース保存用エンティティではないが、Mapper.xmlのSQLで計算した値を格納して、specify.htmlで表示されるために利用する。
     * よって、FormクラスやHelperクラスではこのエンティティは不要とする。
     */
    private String idbRatio;

    /**
     * 躯体歩掛（延床面積1m2当り）
     * ※データベース保存用エンティティではないが、Mapper.xmlのSQLで計算した値を格納して、specify.htmlで表示されるために利用する。
     * よって、FormクラスやHelperクラスではこのエンティティは不要とする。
     */
    private String idbUnitQuantityPerSquareMeter;

    /**
     * 躯体歩掛（コンクリート体積1m3当り）
     * ※データベース保存用エンティティではないが、Mapper.xmlのSQLで計算した値を格納して、specify.htmlで表示されるために利用する。
     * よって、FormクラスやHelperクラスではこのエンティティは不要とする。
     */
    private String idbUnitQuantityPerConcrete;

    /** 作成日時 */
    private LocalDateTime idbCreatedAt;

    /** 更新日時 */
    private LocalDateTime idbUpdatedAt;

    /**
     * 最終編集者
     * 上段：データベース保存用エンティティ（SQLのINSERT文・UPDATE文で利用）
     * 下段：従業員テーブル連携用エンティティ（SQLのSELECT文で利用）
     */
    private String idbLatestEditor;
    private Employee employee;

    /**
     * 削除フラグ
     * データベース保存用エンティティのため、Formクラス及びHelperクラスへの追記不要
     */
    private boolean idbDeleteFlg;

}