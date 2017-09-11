package com.topie.data.system.api.common;

import com.topie.data.common.tools.cache.RedisCache;
import com.topie.data.common.tools.captcha.service.Captcha;
import com.topie.data.common.tools.captcha.utils.ImageUtils;
import com.topie.data.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by chenguojun on 8/31/16.
 */
@Controller
@RequestMapping("/api/noneAuth")
public class NoneAuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCache redisCache;

    @RequestMapping("/unique")
    @ResponseBody
    public Object unique(@RequestParam(value = "loginName", required = false) String loginName) {
        int count = userService.countByLoginName(loginName);
        return count > 0 ? false : true;
    }

    @RequestMapping("/captcha")
    public void captcha(@RequestParam("vkey") String vkey, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) throws IOException {
        Captcha captcha = ImageUtils.getCaptcha(httpServletResponse);
        redisCache.set(vkey, captcha.getChallenge(), 120);
        System.out.println(captcha.getChallenge());
    }
}
