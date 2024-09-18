package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.BreakdownCd;

/**
 * 内訳種目リポジトリインターフェース（MyBatis用マッパー）
 */
@Mapper
public interface BreakdownCdMapper {

    /** 【合計取得】 */
    BreakdownCd sumSelectById(Integer bcdBcoId);

    /** 【特定取得】 */
    List<BreakdownCd> selectAllById(Integer bcdBcoId);

    /** 【詳細検索取得】 */
    List<BreakdownCd> selectAllByAdvancedSearchForm(Integer bcdCdId, Integer bcdPdId, String bcdTypeName);

    /** 【一件取得】 */
    BreakdownCd selectById(Integer bcdId);

    /** 【登録実行】 */
    void insert(BreakdownCd breakdownCd);

    /** 【更新実行】 */
    void update(BreakdownCd breakdownCd);

    /** 【削除実行】 */
    void delete(Integer bcdId);

}