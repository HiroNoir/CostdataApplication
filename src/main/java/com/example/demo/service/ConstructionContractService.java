package com.example.demo.service;

import java.util.List;

import com.example.demo.constraints.ErrorKinds;
import com.example.demo.entity.ConstructionContract;
import com.example.demo.service.impl.LoginUserDetails;

/**
 * 工事契約サービスインターフェース
 */
public interface ConstructionContractService {

    /** 【工事発注前全権取得】 */
    List<ConstructionContract> findAllOfBefore();

    /** 【全件取得】 */
    List<ConstructionContract> findAll();

    /** 【特定取得】 */
    List<ConstructionContract> findAllById(Integer ccDcId);

    /** 【簡易検索取得】 */
    List<ConstructionContract> findAllBySimpleSearchForm(String keyword);

    /** 【詳細検索取得】 */
    List<ConstructionContract> findAllByAdvancedSearchForm(Integer ccEtId, String estimateYear, String siteAddress, String contractorName);

    /** 【一件取得】 */
    ConstructionContract findById(Integer ccId);

    /** 【登録実行】 */
    ErrorKinds insert(ConstructionContract constructionContract, LoginUserDetails loginUserDetails);

    /** 【更新実行】 */
    ErrorKinds update(ConstructionContract constructionContract, LoginUserDetails loginUserDetails);

    /** 【削除実行】 */
    ErrorKinds delete(Integer ccId);

}