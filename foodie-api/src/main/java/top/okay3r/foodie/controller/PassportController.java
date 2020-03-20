package top.okay3r.foodie.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.UserBO;
import top.okay3r.foodie.service.UsersService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.CookieUtils;
import top.okay3r.foodie.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = "用于注册登录的相关接口")
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UsersService usersService;

    /**
     * 检验用户名是否存在
     */
    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ApiJsonResult usernameIsExist(@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return ApiJsonResult.errorMsg("用户名不能为空");
        }
        boolean isExists = this.usersService.queryUsernameIsExists(username);
        if (isExists) {
            return ApiJsonResult.errorMsg("用户名已经存在");
        }
        return ApiJsonResult.ok();
    }

    /**
     * 用户注册
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public ApiJsonResult regist(@RequestBody UserBO userBO,
                                HttpServletRequest request,
                                HttpServletResponse response) {

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(confirmPassword)
        ) {
            return ApiJsonResult.errorMsg("用户名或密码不能为空");
        }

        boolean isExists = this.usersService.queryUsernameIsExists(username);
        if (isExists) {
            return ApiJsonResult.errorMsg("用户名已经存在");
        }

        if (password.length() < 6) {
            return ApiJsonResult.errorMsg("密码长度不能小于6");
        }

        if (!StringUtils.equals(password, confirmPassword)) {
            return ApiJsonResult.errorMsg("密码与确认密码不一致");
        }

        Users userRes = this.usersService.createUser(userBO);

        setNullProperty(userRes);

        //存入cookie
        String userJson = JSON.toJSONString(userRes);
        CookieUtils.setCookie(request, response, "user", userJson, true);

        return ApiJsonResult.ok();
    }

    /**
     * 用户登录
     */
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public ApiJsonResult login(@RequestBody UserBO userBO,
                               HttpServletRequest request,
                               HttpServletResponse response) {

        if (StringUtils.isBlank(userBO.getUsername()) || StringUtils.isBlank(userBO.getPassword())) {
            return ApiJsonResult.errorMsg("用户名或密码不能为空");
        }

        if (userBO.getPassword().length() < 6) {
            return ApiJsonResult.errorMsg("密码长度不能小于6");
        }

        Users userRes = this.usersService.queryUserForLogin(userBO);
        if (userRes == null) {
            return ApiJsonResult.errorMsg("用户名或密码错误");
        }

        //将不需要返回到前端的属性设置为null
        setNullProperty(userRes);

        //存入cookie
        String userJson = JSON.toJSONString(userRes);
        CookieUtils.setCookie(request, response, "user", userJson, true);

        return ApiJsonResult.ok(userRes);
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    public ApiJsonResult logout(@RequestParam String userId,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        //删除用户cookie
        CookieUtils.deleteCookie(request, response, "user");
        return ApiJsonResult.ok();
    }

    private void setNullProperty(Users userRes) {
        userRes.setPassword(null);
        userRes.setMobile(null);
        userRes.setEmail(null);
        userRes.setCreatedTime(null);
        userRes.setUpdatedTime(null);
        userRes.setBirthday(null);
    }
}
