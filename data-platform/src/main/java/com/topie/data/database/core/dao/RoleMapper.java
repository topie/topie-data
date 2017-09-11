package com.topie.data.database.core.dao;

import com.topie.data.common.utils.TreeNode;
import com.topie.data.database.core.model.Role;
import com.topie.data.system.dto.HasRoleUserDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends Mapper<Role> {

    int insertRoleFunction(@Param("roleId") Integer roleId, @Param("functionId") Integer functionId);

    List<Map> findRoleMatchUpFunctions();

    List<TreeNode> selectRoleTreeNodes(Role role);

    List<Role> findRoleList(Role role);

    List<Integer> findFunctionByRoleId(@Param("roleId") Integer roleId);

    int deleteFunctionByRoleId(@Param("roleId") Integer roleId);

    List<HasRoleUserDTO> findHasRoleUserDtoList(HasRoleUserDTO hasRoleUserDTO);

    void deleteUserRoleRelateByRoleId(Integer id);

    List<Integer> findHasRoleUserIdsByRoleId(@Param("roleId") Integer roleId);

    int selectRoleIdByRoleName(@Param("roleName") String roleName);

    List<TreeNode> selectRoleFunctions(@Param("roleIds")List<Integer> roleIdsList);
}
