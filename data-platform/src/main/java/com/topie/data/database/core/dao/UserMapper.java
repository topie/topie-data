package com.topie.data.database.core.dao;

import com.topie.data.database.core.model.User;
import com.topie.data.security.dto.FunctionDTO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface UserMapper extends Mapper<User> {

    User findUserByLoginName(@Param("loginName") String loginName);

    int insertUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    int deleteUserAllRoles(@Param("userId") Integer userId);

    int deleteUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    List<Integer> findUserRoleByUserId(int userId);

    List<User> findUserList(User user);

    int findExistUser(User user);

    List<FunctionDTO> findUserFunction(@Param("loginName") String loginName);

    int countByLoginName(@Param("loginName") String loginName);

    int updateAccountNonLocked(@Param("userId") Integer UserId, @Param("accountNonLocked") Boolean accountNonLocked);

    String findLoginNameByUserId(@Param("userId") Integer userId);

    int updateLastLoginInfoByUserName(@Param("userName") String username, @Param("lastLoginDate") Date lastLoginDate,
            @Param("remoteAddr") String remoteAddr);
}
