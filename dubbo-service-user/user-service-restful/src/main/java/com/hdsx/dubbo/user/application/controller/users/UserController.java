package com.hdsx.dubbo.user.application.controller.users;

import com.hdsx.dubbo.common.framework.vo.CommonResult;
import com.hdsx.dubbo.common.framework.vo.Result;
import com.hdsx.dubbo.common.framework.vo.ResultCode;
import com.hdsx.dubbo.common.framework.vo.ResultUtil;
import com.hdsx.dubbo.user.api.UserService;
import com.hdsx.dubbo.user.api.bean.user.UserBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import static com.hdsx.dubbo.common.framework.vo.CommonResult.success;
@Slf4j
@RestController
@RequestMapping("/users/user")
@Api("用户模块")
public class UserController {

    @Reference(validation = "true", version = "${dubbo.provider.UserService.version}")
    private UserService userService;

    @ApiOperation(value = "新增用户信息", httpMethod = "POST", produces = "application/json", notes = "新增用户信息")
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json")
    public Result addUser(@RequestBody UserBean userBean) {
        try {
            return userService.addUser(userBean);
        } catch (Exception e) {
            log.error("系统异常:{}", e.getMessage(), e);
            return ResultUtil.error(ResultCode.ADD_FAIL);
        }
    }

    @ApiOperation(value = "编辑用户信息", httpMethod = "POST", produces = "application/json", notes = "编辑用户信息")
    @RequestMapping(value = "/editUser", method = RequestMethod.POST, produces = "application/json")
    public Result editUser(@RequestBody UserBean userBean) {
        try {
            return userService.editUser(userBean);
        } catch (Exception e) {
            log.error("系统异常:{}", e.getMessage(), e);
            return ResultUtil.error(ResultCode.ADD_FAIL);
        }
    }

    @ApiImplicitParam(paramType = "query", name = "username", dataType = "String", required = true, value = "用户名")
    @ApiOperation(value = "根据用户名查询用户", httpMethod = "GET", produces = "application/json")
    @RequestMapping(value = "/getUserByName",method = RequestMethod.GET,produces = "application/json")
    public Result getUserByName(@RequestParam String username){
        try{
            return userService.getUserByName(username);
        } catch (Exception e){
            log.error("服务器异常{}",e.getMessage(),e);
            return ResultUtil.error(ResultCode.QUERY_FAIL);
        }
    }

    @ApiOperation(value = "用户登录", httpMethod = "POST", produces = "application/json", notes = "用户登录")
    @RequestMapping(value = "/userLogin", method = RequestMethod.POST, produces = "application/json")
    public Result userLogin(@RequestBody UserBean userBean) {
        try {
            return userService.userLogin(userBean);
        } catch (Exception e) {
            log.error("系统异常:{}", e.getMessage(), e);
            return ResultUtil.error(ResultCode.LOGIN_FAIL);
        }
    }

}
