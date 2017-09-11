package com.topie.data.security.service;

import com.topie.data.database.core.model.User;
import com.topie.data.security.dto.FunctionDTO;
import com.github.pagehelper.PageInfo;
import com.topie.data.common.baseservice.IService;

import java.util.Date;
import java.util.List;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/2 说明：
 */
public interface UserService extends IService<User> {

    int insertUser(User user);

    int updateUser(User user);

    User findUserById(Integer id);

    User findUserByLoginName(String loginName);

    int deleteUser(Integer id);

    int insertUserRole(Integer userId, Integer roleId);

    int deleteUserAllRoles(Integer userId);

    List<Integer> findUserRoleByUserId(int userId);

    PageInfo<User> findUserList(Integer pageNum, Integer pageSize, User user);

    int findExistUser(User user);

    List<FunctionDTO> findUserFunctionByLoginName(String loginName);

    int countByLoginName(String loginName);

    int updateLockStatusByUserId(int userId, Boolean accountNonLocked);

    String findLoginNameByUserId(Integer userId);

    int deleteUserRole(Integer userId, Integer roleId);

    void updateLastLoginInfoByUserName(String username, Date date, String remoteAddr);

    void deleteUserCache(int userId);
}
