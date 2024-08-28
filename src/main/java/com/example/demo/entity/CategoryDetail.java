package com.example.demo.entity;

import lombok.Data;

/**
 * 内訳種目区分設定エンティティクラス
 */
@Data
public class CategoryDetail {

    /**
     * 主キー
     * 内訳種目区分ID
     */
    private Integer cdId;

    /** 内訳頭紙区分 */
    private Integer coId;

    /** 内訳種目区分名 */
    private String cdTypeName;

}