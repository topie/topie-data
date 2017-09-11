package com.topie.data.security.service.impl;

import com.topie.data.common.baseservice.impl.BaseService;
import com.topie.data.database.core.dao.UserMapper;
import com.topie.data.database.core.model.User;
import com.topie.data.security.dto.FunctionDTO;
import com.topie.data.security.security.UserCache;
import com.topie.data.security.service.UserService;
import com.topie.data.security.utils.SecurityUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 工程：os-app 创建人 : ChenGJ 创建时间： 2015/9/2 说明：
 */
@Service("userService")
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserCache userCache;

    @Override
    public int insertUser(User user) {
        user.setPassword(SecurityUtil.encodeString(user.getPassword()));
        int result = getMapper().insertSelective(user);
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            deleteUserAllRoles(user.getId());
            for (Integer roleId : user.getRoles()) {
                insertUserRole(user.getId(), roleId);
            }
        }
        return result;
    }

    @Override
    public int updateUser(User user) {
        if (StringUtils.isNotEmpty(user.getPassword())) {
            user.setPassword(SecurityUtil.encodeString(user.getPassword()));
        }
        int result = getMapper().updateByPrimaryKeySelective(user);
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            deleteUserAllRoles(user.getId());
            for (Integer roleId : user.getRoles()) {
                insertUserRole(user.getId(), roleId);
            }
        }
        if (result > 0) {
            userCache.removeUserFromCacheByUserId(user.getId());
        }
        return result;
    }

    @Override
    public User findUserById(Integer id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public User findUserByLoginName(String loginName) {
        return userMapper.findUserByLoginName(loginName);
    }

    @Override
    public int deleteUser(Integer id) {
        int result = getMapper().deleteByPrimaryKey(id);
        if (result > 0) {
            deleteUserAllRoles(id);
        }
        return result;
    }

    @Override
    public int insertUserRole(Integer userId, Integer roleId) {
        int result = userMapper.insertUserRole(userId, roleId);
        if (result > 0) {
            userCache.removeUserFromCacheByUserId(userId);
        }
        return result;
    }

    @Override
    public List<Integer> findUserRoleByUserId(int userId) {
        return userMapper.findUserRoleByUserId(userId);
    }

    @Override
    public PageInfo<User> findUserList(Integer pageNum, Integer pageSize, User user) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.findUserList(user);
        PageInfo<User> page = new PageInfo<User>(list);
        return page;
    }

    @Override
    public int findExistUser(User user) {
        return userMapper.findExistUser(user);
    }

    @Override
    public List<FunctionDTO> findUserFunctionByLoginName(String loginName) {
        return userMapper.findUserFunction(loginName);
    }

    @Override
    public int countByLoginName(String loginName) {
        return userMapper.countByLoginName(loginName);
    }

    @Override
    public int updateLockStatusByUserId(int userId, Boolean accountNonLocked) {
        int result = userMapper.updateAccountNonLocked(userId, accountNonLocked);
        if (result > 0) {
            userCache.removeUserFromCacheByUserId(userId);
        }
        return result;
    }

    @Override
    public String findLoginNameByUserId(Integer userId) {
        return userMapper.findLoginNameByUserId(userId);
    }

    @Override
    public int deleteUserAllRoles(Integer userId) {
        int result = userMapper.deleteUserAllRoles(userId);
        if (result > 0) {
            userCache.removeUserFromCacheByUserId(userId);
        }
        return result;
    }

    @Override
    public int deleteUserRole(Integer userId, Integer roleId) {
        int result = userMapper.deleteUserRole(userId, roleId);
        if (result > 0) {
            userCache.removeUserFromCacheByUserId(userId);
        }
        return result;
    }

    @Override
    public void updateLastLoginInfoByUserName(String username, Date lastLoginDate, String remoteAddr) {
        userMapper.updateLastLoginInfoByUserName(username, lastLoginDate, remoteAddr);
    }

    @Override
    public void deleteUserCache(int userId) {
        userCache.removeUserFromCacheByUserId(userId);
    }
}
