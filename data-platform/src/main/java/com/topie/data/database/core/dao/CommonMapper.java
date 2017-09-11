package com.topie.data.database.core.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguojun on 2017/6/27.
 */
public interface CommonMapper {

    List<Map> selectByCommonTableBySql(@Param("fullSql") String sql);
}
