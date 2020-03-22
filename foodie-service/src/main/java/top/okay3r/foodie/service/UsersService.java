package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.UserBo;

public interface UsersService {

    /**
     * 校验用户名是否存在
     */
    boolean queryUsernameIsExists(String username);

    /**
     * 新建用户
     */
    Users createUser(UserBo userBO);

    /**
     * 用户登录，校验用户名、密码
     */
    Users queryUserForLogin(UserBo userBO);
}
