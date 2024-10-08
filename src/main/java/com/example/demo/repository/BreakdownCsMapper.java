package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.BreakdownCs;

/**
 * 内訳科目リポジトリインターフェース（MyBatis用マッパー）
 */
@Mapper
public interface BreakdownCsMapper {

    /** 【分析用特定取得　※直仮+土工+地業+躯体+仕上】 */
    List<BreakdownCs> selectAllByIdCategorizedByGroup(Integer bcsBcdId, Long directConstructionPrice);

    /** 【合計取得】 */
    BreakdownCs sumSelectById(Integer bcsBcdId);

    /** 【特定取得】 */
    List<BreakdownCs> selectAllById(Integer bcsBcdId);

    /** 【一件取得】 */
    BreakdownCs selectById(Integer bcsBcdId, Integer bcsCsId);

    /** 【登録実行】 */
    void insert(BreakdownCs breakdownCs);

    /** 【更新実行】 */
    void update(BreakdownCs breakdownCs);

    /** 【削除実行】 */
    void delete(Integer bcsBcdId, Integer bcsCsId);

}