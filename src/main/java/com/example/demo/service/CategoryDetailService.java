package com.example.demo.service;

import java.util.Map;

import com.example.demo.entity.CategoryDetail;

/**
 * 内訳種目区分設定サービスインターフェース
 */
public interface CategoryDetailService {

    /** 【内訳種目区分のMapを生成（全件取得）】 */
    Map<String, Integer> getCategoryDetailMap();

    /** 【内訳種目区分のMapを生成（特定取得）】 */
    Map<String, Integer> getCategoryDetailMapById(Integer cdCoId);

    /** 【一件取得】 */
    CategoryDetail findById(Integer cdId);

}