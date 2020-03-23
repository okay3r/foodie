package top.okay3r.foodie.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.service.center.CenterUserService;
import top.okay3r.foodie.utils.ApiJsonResult;

@Api(value = "用户中心", tags = "用户中心相关接口")
@RestController
@RequestMapping("/center")
public class CenterController {

    @Autowired
    private CenterUserService centerUserService;

    /**
     * 查询用户信息
     */
    @GetMapping("/userInfo")
    @ApiOperation(value = "查询用户信息", notes = "查询用户信息", httpMethod = "GET")
    public ApiJsonResult getItemInfo(@RequestParam("userId") String userId) {
        Users users = this.centerUserService.queryUserInfoById(userId);
        setNullProperty(users);
        return ApiJsonResult.ok(users);
    }

    private void setNullProperty(Users userRes) {
        userRes.setPassword(null);
        userRes.setCreatedTime(null);
        userRes.setUpdatedTime(null);
    }
}
