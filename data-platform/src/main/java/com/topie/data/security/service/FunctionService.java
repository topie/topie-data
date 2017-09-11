package com.topie.data.security.service;

import com.topie.data.database.core.model.Function;
import com.github.pagehelper.PageInfo;
import com.topie.data.common.baseservice.IService;
import com.topie.data.common.utils.TreeNode;

import java.util.List;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/4 说明：
 */
public interface FunctionService extends IService<Function> {

    int insertFunction(Function function);

    int updateFunction(Function function);

    Function findFunctionById(int id);

    int deleteFunctionById(int id);

    List<TreeNode> getFunctionTreeNodes(Function function);

    PageInfo<Function> findFunctionList(int pageNum, int pageSize, Function function);
}
