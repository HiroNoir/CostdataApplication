package com.example.demo.entity;

/**
 * 工事契約状態クラス（列挙型）
 */
public enum ContractCondition {

    /** 列挙型権限の定義 */
    // 権限と文字列の配列を作成
    BEFORE("工事発注前"), AFTER("工事発注後"), CANCEL("工事発注中止");

    // 列挙型権限の文字列を定義
    private String name;

    // 列挙型権限の文字列を格納
    private ContractCondition(String name) {
        this.name = name;
    }

    /** 文字列取得メソッドの設定 */
    public String getValue() {
        return this.name;

    }

}