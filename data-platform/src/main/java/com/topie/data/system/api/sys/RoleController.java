package com.topie.data.system.api.sys;

import com.topie.data.common.utils.PageConvertUtil;
import com.topie.data.common.utils.ResponseUtil;
import com.topie.data.common.utils.Result;
import com.topie.data.system.dto.HasRoleUserDTO;
import com.topie.data.database.core.model.Role;
import com.topie.data.security.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.topie.data.common.utils.TreeNode;
import com.topie.data.security.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cgj on 2016/4/9.
 */
@Controller
@RequestMapping("/api/sys/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    @ResponseBody
    public Result roles(Role role, @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        PageInfo<Role> pageInfo = roleService.findRoleList(pageNum, pageSize, role);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public Result insertRole(Role role) {
        int result = roleService.insertRole(role);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Result updateRole(Role role) {
        int result = roleService.updateRole(role);
        if (result > 0) {
            roleService.refreshAuthAndResource(role.getId());
        }
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/load/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public Result loadRole(@PathVariable(value = "roleId") Integer roleId) {
        Role role = roleService.findRoleById(roleId);
        List functions = roleService.findFunctionByRoleId(roleId);
        if (functions != null) role.setFunctions(functions);
        return ResponseUtil.success(role);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestParam(value = "roleId") Integer roleId) {
        roleService.deleteRole(roleId);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/treeNodes", method = RequestMethod.POST)
    @ResponseBody
    public Object treeNodes(Role role) {
        List<TreeNode> list = roleService.getRoleTreeNodes(role);
        return list;
    }

    @RequestMapping(value = "/functions", method = RequestMethod.POST)
    @ResponseBody
    public Object functions(@RequestParam(value = "roleIds", required = false) String roleIds) {
        List<TreeNode> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(roleIds)) {
            List<Integer> roleIdsList = new ArrayList<>();
            String[] roleIdsStr = roleIds.split(",");
            for (String roleId : roleIdsStr) {
                if(StringUtils.isNotEmpty(roleId)){
                    roleIdsList.add(Integer.valueOf(roleId));
                }
            }
            list = roleService.selectRoleFunctions(roleIdsList);
        }
        return list;
    }

    @RequestMapping(value = "/hasRoleUserList", method = RequestMethod.GET)
    @ResponseBody
    public Result hasRoleUserList(HasRoleUserDTO hasRoleUserDTO,
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "15") int pageSize) {
        if (hasRoleUserDTO.getRoleId() == null) {
            return ResponseUtil.error("角色id不能为空");
        }
        PageInfo<HasRoleUserDTO> pageInfo = roleService
                .findHasRoleUserDtoListByRoleId(pageNum, pageSize, hasRoleUserDTO);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping(value = "/selectUser", method = RequestMethod.GET)
    @ResponseBody
    public Result selectUser(@RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "userId") Integer userId) {
        userService.insertUserRole(userId, roleId);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/cancelUser", method = RequestMethod.GET)
    @ResponseBody
    public Result cancelUser(@RequestParam(value = "roleId") Integer roleId,
            @RequestParam(value = "userId") Integer userId) {
        userService.deleteUserRole(userId,roleId);
        return ResponseUtil.success();
    }

}
