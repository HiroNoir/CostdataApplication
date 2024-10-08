package com.example.demo.form;

import com.example.demo.entity.ContractCondition;
import com.example.demo.entity.DesignContract;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 工事契約フォームクラス
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstructionContractForm {

    /**
     * 主キー
     * 工事契約ID
     */
    private Integer ccId;

    /** 設計契約 */
    private Integer ccDcId;

    /** 設計契約（更新処理時form.html表示用） */
    private DesignContract designContract;

    /** 内訳種別区分 */
    @NotNull(message = "内訳種別区分を選択してください。")
    private Integer ccEtId;

    /** 工事契約状態 */
    @NotNull(message = "工事契約状態を選択してください。")
    private ContractCondition contractCondition;

    /** 積算時期（年） */
    @NotBlank(message = "積算時期（年）は必須です。")
    @Size(max = 10, message = "{max}文字以下で入力してください。")
    private String estimateYear;

    /** 積算時期（月） */
    @NotBlank(message = "積算時期（月）は必須です。")
    @Size(max = 10, message = "{max}文字以下で入力してください。")
    private String estimateMonth;

    /** 発注工期 */
    @NotBlank(message = "発注工期は必須です。")
    @Size(max = 10, message = "{max}文字以下で入力してください。")
    private String constructionPeriod;

    /** 工事名称 */
    @NotBlank(message = "工事名称は必須です。")
    @Size(max = 100, message = "{max}文字以下で入力してください。")
    private String projectName;

    /** 工事場所 */
    @NotBlank(message = "工事場所は必須です。")
    @Size(max = 100, message = "{max}文字以下で入力してください。")
    private String siteAddress;

    /** 敷地面積 */
    @NotNull(message = "敷地面積は必須です。")
    private Double siteArea;

    /** 別途工事 */
    @NotBlank(message = "別途工事は必須です。")
    @Size(max = 100, message = "{max}文字以下で入力してください。")
    private String separeteConstruction;

    /** 予定価格 */
    @NotNull(message = "予定価格は必須です。")
    private Long plannedPrice;

    /** 落札価格 */
    @NotNull(message = "落札価格は必須です。")
    private Long contractPrice;

    /** 施工業者 */
    @NotBlank(message = "施工業者は必須です。")
    @Size(max = 30, message = "{max}文字以下で入力してください。")
    private String contractorName;

    /** 経緯等コメント */
    @NotBlank(message = "経緯等コメントは必須です。")
    private String remarksSection;

    /** 発注図（抜粋） */
    @NotBlank(message = "発注図（抜粋）は必須です。")
    private String blueprintAddress;

    /** 新規判定 */
    // 新規登録の場合はTrue、更新の場合はfalse
    private Boolean isNew;

}