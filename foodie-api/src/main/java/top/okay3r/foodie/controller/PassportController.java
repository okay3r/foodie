package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.UserBO;
import top.okay3r.foodie.service.UsersService;
import top.okay3r.foodie.utils.ApiJsonResult;

@Api(value = "注册登录", tags = "用于注册登录的相关接口")
@RestController
@RequestMapping("/passport")
public class PassportController {

    @Autowired
    private UsersService usersService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public ApiJsonResult usernameIsExist (@RequestParam String username) {
        if (StringUtils.isBlank(username)) {
            return ApiJsonResult.errorMsg("用户名不能为空");
        }
        boolean isExists = this.usersService.queryUsernameIsExists(username);
        if (isExists) {
            return ApiJsonResult.errorMsg("用户名已经存在");
        }
        return ApiJsonResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public ApiJsonResult regist(@RequestBody UserBO userBO) {

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

        Users user = this.usersService.createUser(userBO);

        return ApiJsonResult.ok();
    }
}
