package com.example.demo.service;

import java.util.Map;

import com.example.demo.entity.CategoryOutline;

/**
 * 内訳頭紙区分設定サービスインターフェース
 */
public interface CategoryOutlineService {

    /** 【内訳頭紙区分のMapを生成（全件取得）】 */
    Map<String, Integer> getCategoryOutlineMap();

    /** 【一件取得】 */
    CategoryOutline findById(Integer coId);

}