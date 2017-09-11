package com.topie.data.database.core.dao;

import com.topie.data.database.core.model.Function;
import com.topie.data.common.utils.TreeNode;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface FunctionMapper extends Mapper<Function> {

    public List<TreeNode> selectFunctionTreeNodes(Function function);

    List<Function> findFunctionList(Function function);

    int deleteRoleFunctionByFunctionId(int id);

    List<Integer> findRoleIdsByFunctionId(@Param("functionId") int id);
}
