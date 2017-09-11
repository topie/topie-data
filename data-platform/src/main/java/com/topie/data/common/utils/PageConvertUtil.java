package com.topie.data.common.utils;

import com.github.pagehelper.PageInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cgj on 2015/10/30.
 */
public class PageConvertUtil {

    public static Map grid(PageInfo<?> info) {
        Map map = new HashMap();
        map.put("total", info.getTotal());
        map.put("data", info.getList());
        return map;
    }

    public static Map grid(SimplePageInfo<?> info) {
        Map map = new HashMap();
        map.put("total", info.getTotal());
        map.put("data", info.getList());
        return map;
    }

    public static Map grid(List<?> list) {
        Map map = new HashMap();
        map.put("total", list.size());
        map.put("data", list);
        return map;
    }

    public static Map chart(List<?> list) {
        Map map = new HashMap();
        map.put("data", list);
        return map;
    }
}
