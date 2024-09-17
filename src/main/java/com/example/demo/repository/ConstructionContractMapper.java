package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.ConstructionContract;

/**
 * 工事契約リポジトリインターフェース（MyBatis用マッパー）
 */
@Mapper
public interface ConstructionContractMapper {

    /** 【工事発注前全権取得】 */
    List<ConstructionContract> selectAllOfBefore();

    /** 【全件取得】 */
    List<ConstructionContract> selectAll();

    /** 【特定取得】 */
    List<ConstructionContract> selectAllById(Integer ccDcId);

    /** 【簡易検索取得】 */
    List<ConstructionContract> selectAllBySimpleSearchForm(String keyword);

    /** 【詳細検索取得】 */
    List<ConstructionContract> selectAllByAdvancedSearchForm(Integer ccEtId, String estimateYear, String siteAddress, String contractorName);

    /** 【一件取得】 */
    ConstructionContract selectById(Integer ccId);

    /** 【登録実行】 */
    void insert(ConstructionContract constructionContract);

    /** 【更新実行】 */
    void update(ConstructionContract constructionContract);

    /** 【削除実行】 */
    void delete(Integer ccId);

}