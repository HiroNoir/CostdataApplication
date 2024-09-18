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
public class AdvancedSearchFormOfBcd {

    /** 内訳種目区分 */
    private Integer bcdCdId;

    /** 用途概略区分 */
    private Integer bcdPoId;

    /** 用途詳細区分 */
    private Integer bcdPdId;

    /** 種目名称 */
    private String bcdTypeName;

}