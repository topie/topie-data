package com.topie.data.system.api.sys;

import com.topie.data.common.tools.excel.ExcelLogs;
import com.topie.data.common.tools.excel.ExcelUtil;
import com.topie.data.common.utils.PageConvertUtil;
import com.topie.data.common.utils.ResponseUtil;
import com.topie.data.common.utils.Result;
import com.topie.data.database.core.model.User;
import com.topie.data.security.exception.AuBzConstant;
import com.topie.data.security.exception.AuthBusinessException;
import com.topie.data.security.service.RoleService;
import com.topie.data.security.service.UserService;
import com.topie.data.security.utils.SecurityUtil;
import com.topie.data.system.dto.ImportUserDto;
import com.github.pagehelper.PageInfo;
import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by cgj on 2016/4/9.
 */
@Controller
@RequestMapping("/api/sys/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ResponseBody
    public Result users(User user, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<User> pageInfo = userService.findUserList(pageNum, pageSize, user);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseUtil.error(result);
        }
        if (userService.findExistUser(user) > 0) {
            throw new AuthBusinessException(AuBzConstant.LOGIN_NAME_EXIST);
        }
        userService.insertUser(user);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public Result importUser(@RequestParam(value = "file", required = false) MultipartFile file)
            throws IOException, PinyinException {
        if (file == null || file.isEmpty()) {
            return ResponseUtil.error("附件为空");
        }
        ExcelLogs logs = new ExcelLogs();
        Collection<ImportUserDto> userDtos;
        if (file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) {
            userDtos = ExcelUtil.importExcelX(ImportUserDto.class, file.getInputStream(), 0, "yyyy-MM-dd", logs);
        } else {
            userDtos = ExcelUtil.importExcel(ImportUserDto.class, file.getInputStream(), 0, "yyyy-MM-dd", logs);
        }
        for (ImportUserDto userDto : userDtos) {
            User user = new User();
            user.setLoginName(
                    PinyinHelper.convertToPinyinString(userDto.getDisplayName(), "", PinyinFormat.WITHOUT_TONE));
            user.setDisplayName(userDto.getDisplayName());
            user.setContactPhone(userDto.getContactPhone());
            user.setEmail(user.getLoginName() + "@topie.com");
            user.setPassword(user.getLoginName() + "123");
            if (userService.findExistUser(user) > 0) {
                continue;
            }
            userService.insertUser(user);
            Integer userId = user.getId();
            Integer roleId = roleService.selectRoleIdByRoleName(userDto.getRoleName());
            if (userId > 0 && roleId > 0) userService.insertUserRole(userId, roleId);
        }

        return ResponseUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateUser(User user) {
        if (userService.findExistUser(user) > 0) {
            throw new AuthBusinessException(AuBzConstant.LOGIN_NAME_EXIST);
        }
        userService.updateUser(user);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Result loadUser(@PathVariable(value = "userId") int userId) {
        User user = userService.findUserById(userId);
        List roles = userService.findUserRoleByUserId(userId);
        if (roles != null) user.setRoles(roles);
        return ResponseUtil.success(user);
    }

    @RequestMapping(value = "/lock/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Result lock(@PathVariable(value = "userId") int userId) {
        if (SecurityUtil.getCurrentUserId() == userId) {
            throw new AuthBusinessException(AuBzConstant.CANNOT_CHANGE_CURRENT_USER);
        }
        int result = userService.updateLockStatusByUserId(userId, false);
        if (result > 0) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error("操作未成功。");
        }
    }

    @RequestMapping(value = "/unLock/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Result unLock(@PathVariable(value = "userId") int userId) {
        if (SecurityUtil.getCurrentUserId() == userId) {
            throw new AuthBusinessException(AuBzConstant.CANNOT_CHANGE_CURRENT_USER);
        }
        int result = userService.updateLockStatusByUserId(userId, true);
        if (result > 0) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error("操作未成功。");
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam(value = "userId") int userId) {
        if (SecurityUtil.getCurrentUserId() == userId) {
            throw new AuthBusinessException(AuBzConstant.CANNOT_CHANGE_CURRENT_USER);
        }
        userService.deleteUser(userId);
        return ResponseUtil.success();
    }


    @RequestMapping(value = "/deleteCache", method = RequestMethod.GET)
    @ResponseBody
    public Result deleteCache(@RequestParam(value = "userId") int userId) {
        userService.deleteUserCache(userId);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/roles/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public Result userRoles(@PathVariable(value = "userId") int userId) {
        List roles = userService.findUserRoleByUserId(userId);
        return ResponseUtil.success(roles);
    }
}
