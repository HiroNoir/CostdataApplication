package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.PurposeDetail;

/**
 * 用途詳細区分設定リポジトリインターフェース（MyBatis用マッパー）
 */
@Mapper
public interface PurposeDetailMapper {

    /** 【特定取得】 */
    List<PurposeDetail> selectAllById(Integer pdPoId);

    /** 【一件取得】 */
    PurposeDetail selectById(Integer pdId);
}