package com.topie.data.core.api;

import com.github.pagehelper.PageInfo;
import com.topie.data.common.utils.PageConvertUtil;
import com.topie.data.common.utils.ResponseUtil;
import com.topie.data.common.utils.Result;
import com.topie.data.core.service.ICommonQueryService;
import com.topie.data.database.core.model.CommonQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/commonQuery")
public class CommonQueryController {

    @Autowired
    private ICommonQueryService iCommonQueryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(CommonQuery commonQuery,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<CommonQuery> pageInfo = iCommonQueryService.selectByFilterAndPage(commonQuery, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(CommonQuery commonQuery) {
        int result = iCommonQueryService.saveNotNull(commonQuery);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(CommonQuery commonQuery) {
        iCommonQueryService.updateNotNull(commonQuery);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        CommonQuery commonQuery = iCommonQueryService.selectByKey(id);
        return ResponseUtil.success(commonQuery);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iCommonQueryService.delete(id);
        return ResponseUtil.success();
    }

}
