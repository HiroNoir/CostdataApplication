package com.example.demo.service;

import java.util.Map;

/**
 * 内訳情報区分設定サービスインターフェース
 */
public interface InformationItemService {

    /** 【内訳情報区分設定のMapを生成（特定取得）】 */
    Map<String, Integer> getInformationItemMapById(Integer iiCoId);

}