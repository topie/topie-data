package com.topie.data.security.security;

import com.topie.data.common.tools.cache.IBasicCache;
import com.topie.data.security.constants.SecurityConstant;
import com.topie.data.security.service.UserService;
import com.topie.data.security.dto.FunctionDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by cgj on 2016/4/13.
 */
public class UserCache implements org.springframework.security.core.userdetails.UserCache, InitializingBean {

    private IBasicCache<String, Object> cache = null;

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setCache(IBasicCache<String, Object> cache) {
        this.cache = cache;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.cache == null) this.cache = new NullCache<String, Object>();
        Assert.notNull(this.cache);
    }

    @Override
    public UserDetails getUserFromCache(String username) {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) cache.get(SecurityConstant.USER_CACHE_PREFIX + username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return userDetails == null ? null : userDetails;
        }

    }

    @Override
    public void putUserInCache(UserDetails user) {
        cache.set(SecurityConstant.USER_CACHE_PREFIX + user.getUsername(), user);
        List<FunctionDTO> functionList = userService.findUserFunctionByLoginName(user.getUsername());
        cache.set(SecurityConstant.FUNCTION_CACHE_PREFIX + user.getUsername(), functionList);
    }

    @Override
    public void removeUserFromCache(String username) {
        cache.del(SecurityConstant.USER_CACHE_PREFIX + username);
        cache.del(SecurityConstant.FUNCTION_CACHE_PREFIX + username);
    }

    public void removeUserFromCacheByUserId(Integer userId) {
        String loginName = userService.findLoginNameByUserId(userId);
        if (loginName != null) {
            removeUserFromCache(loginName);
        }
    }
}
