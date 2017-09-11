package com.topie.data.core.api;

import com.github.pagehelper.PageInfo;
import com.topie.data.common.utils.PageConvertUtil;
import com.topie.data.common.utils.ResponseUtil;
import com.topie.data.common.utils.Result;
import com.topie.data.core.service.INoticeService;
import com.topie.data.database.core.model.Notice;
import com.topie.data.security.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Controller
@RequestMapping("/api/core/notice")
public class NoticeController {

    @Autowired
    private INoticeService iNoticeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(Notice notice,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Notice> pageInfo = iNoticeService.selectByFilterAndPage(notice, pageNum, pageSize);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insert(Notice notice) {
        if (notice.getPosition() == 1 && StringUtils.isEmpty(notice.getBannerUri())) {
            ResponseUtil.error("公告位置为轮播时，轮播图必须上传！");
        }
        if (StringUtils.isEmpty(notice.getcUser())) {
            String currentUser = SecurityUtil.getCurrentUserName();
            notice.setcUser(currentUser);
        }
        if (notice.getcTime() == null) notice.setcTime(new Date());
        if (notice.getpTime() == null) notice.setpTime(new Date());
        int result = iNoticeService.saveNotNull(notice);
        return result > 0 ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(Notice notice) {
        iNoticeService.updateNotNull(notice);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/updateToOnline", method = RequestMethod.POST)
    @ResponseBody
    public Result updateToOnline(@RequestParam(value = "id") Integer id) {
        Notice notice = iNoticeService.selectByKey(id);
        notice.setIsOnline(true);
        iNoticeService.updateNotNull(notice);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/updateToOffline", method = RequestMethod.POST)
    @ResponseBody
    public Result updateToOffline(@RequestParam(value = "id") Integer id) {
        Notice notice = iNoticeService.selectByKey(id);
        notice.setIsOnline(false);
        iNoticeService.updateNotNull(notice);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Result load(@PathVariable(value = "id") Integer id) {
        Notice notice = iNoticeService.selectByKey(id);
        return ResponseUtil.success(notice);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public Result delete(@RequestParam(value = "id") Integer id) {
        iNoticeService.delete(id);
        return ResponseUtil.success();
    }

}
