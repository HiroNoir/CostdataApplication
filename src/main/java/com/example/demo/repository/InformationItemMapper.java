package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.InformationItem;

/**
 * 用途情報区分設定リポジトリインターフェース（MyBatis用マッパー）
 */
@Mapper
public interface InformationItemMapper {

    /** 【特定取得】 */
    List<InformationItem> selectAllById(Integer iiCoId);

}