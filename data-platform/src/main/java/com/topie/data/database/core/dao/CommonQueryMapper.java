package com.topie.data.database.core.dao;

import com.topie.data.database.core.model.CommonQuery;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface CommonQueryMapper extends Mapper<CommonQuery> {

    List<Map> selectColumnsByTable(@Param("table") String table);
}
