package com.example.demo.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 詳細検索フォームクラス（内訳種目テーブル用）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedSearchFormOfIdb {

    /** 内訳情報区分 */
    private Integer idbIiId;

    /** 文字情報 */
    private String idbDataText;

    /** 数量情報(上限) */
    private Double upperIdbDataDouble;

    /** 数量情報(下限) */
    private Double lowerIdbDataDouble;

}