package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.DesignContract;

/**
 * 設計契約リポジトリインターフェース（MyBatis用マッパー）
 */
@Mapper
public interface DesignContractMapper {

    /** 【全件取得】 */
    List<DesignContract> selectAll();

    /** 【検索取得】 */
    List<DesignContract> selectAllByKeyword(String keyword);

    /** 【一件取得】 */
    DesignContract selectById(Integer dcId);

    /** 【登録実行】 */
    void insert(DesignContract designContract);

    /** 【更新実行】 */
    void update(DesignContract designContract);

    /** 【削除実行】 */
    void delete(Integer dcId);

}