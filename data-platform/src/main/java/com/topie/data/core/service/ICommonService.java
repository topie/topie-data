package com.topie.data.core.service;

import java.util.List;
import java.util.Map;

/**
 * Created by chenguojun on 2017/6/27.
 */
public interface ICommonService {

    List<Map> selectByCommonTableBySql(String sql);
}
