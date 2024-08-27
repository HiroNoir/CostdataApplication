package com.example.demo.service;

import java.util.List;

import com.example.demo.constraints.ErrorKinds;
import com.example.demo.entity.InformationDb;
import com.example.demo.service.impl.LoginUserDetails;

/**
 * 内訳情報サービスインターフェース
 */
public interface InformationDbService {

    /** 【分析用特定取得 ※躯体数量と歩掛り】 */
    List<InformationDb> quantityFindById(Integer idbBcdId, Double targetArea, Double targetConcreteVolume);

    /** 【分析用特定取得 ※建築+電気設備+機械設備+昇降機設備】 */
    List<InformationDb> priceFindById(Integer idbBcdId, Long directConstructionPrice);

    /** 【分析用合計取得 ※建築+電気設備+機械設備+昇降機設備】 */
    InformationDb sumFindById(Integer idbBcdId);

    /** 【特定取得】 */
    List<InformationDb> findAllById(Integer idbBcdId);

    /** 【一件取得】 */
    InformationDb findById(Integer idbBcdId, Integer idbIiId);

    /** 【登録実行】 */
    ErrorKinds insert(InformationDb informationDb, LoginUserDetails loginUserDetails);

    /** 【更新実行】 */
    ErrorKinds update(InformationDb informationDb, LoginUserDetails loginUserDetails);

    /** 【削除実行】 */
    ErrorKinds delete(Integer idbBcdId, Integer idbIiId);

}