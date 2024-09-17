package com.example.demo.form;

import com.example.demo.entity.ContractCondition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 詳細検索フォームクラス（工事契約テーブル用）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedSearchFormOfCc {

    /** 内訳種別区分 */
    private Integer ccEtId;

    /** 工事契約状態 */
    private ContractCondition contractCondition;

    /** 積算時期（年） */
    private String estimateYear;

    /** 工事場所 */
    private String siteAddress;

    /** 施工業者 */
    private String contractorName;

}