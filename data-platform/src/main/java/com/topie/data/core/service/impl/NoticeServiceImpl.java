package com.topie.data.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.topie.data.common.baseservice.impl.BaseService;
import com.topie.data.core.service.INoticeService;
import com.topie.data.database.core.model.Notice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by chenguojun on 2017/4/19.
 */
@Service
public class NoticeServiceImpl extends BaseService<Notice> implements INoticeService {

    @Override
    public PageInfo<Notice> selectByFilterAndPage(Notice notice, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> list = selectByFilter(notice);
        return new PageInfo<>(list);
    }

    @Override
    public List<Notice> selectByFilter(Notice notice) {
        Example example = new Example(Notice.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotEmpty(notice.getTitle())) criteria.andLike("title", "%" + notice.getTitle() + "%");
        if (notice.getType() != null) criteria.andEqualTo("type", notice.getType());
        if (notice.getPosition() != null) criteria.andEqualTo("position", notice.getPosition());
        if (notice.getIsOnline() != null) criteria.andEqualTo("isOnline", notice.getIsOnline());
        if (StringUtils.isNotEmpty(notice.getPeriodC())) {
            String[] dateArr = notice.getPeriodC().split(" 到 ");
            if (StringUtils.isNotEmpty(dateArr[0])) {
                criteria.andGreaterThanOrEqualTo("cTime", dateArr[0].trim());
            }
            if (StringUtils.isNotEmpty(dateArr[1])) {
                criteria.andLessThanOrEqualTo("cTime", dateArr[1].trim());
            }
        }
        if (StringUtils.isNotEmpty(notice.getPeriodP())) {
            String[] dateArr = notice.getPeriodP().split(" 到 ");
            if (StringUtils.isNotEmpty(dateArr[0])) {
                criteria.andGreaterThanOrEqualTo("pTime", dateArr[0].trim());
            }
            if (StringUtils.isNotEmpty(dateArr[1])) {
                criteria.andLessThanOrEqualTo("pTime", dateArr[1].trim());
            }
        }
        if (StringUtils.isNotEmpty(notice.getSortWithOutOrderBy()))
            example.setOrderByClause(notice.getSortWithOutOrderBy());
        return getMapper().selectByExample(example);
    }

}
