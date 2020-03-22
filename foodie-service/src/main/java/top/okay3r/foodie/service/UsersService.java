package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.UserBo;

public interface UsersService {
    boolean queryUsernameIsExists(String username);

    Users createUser(UserBo userBO);

    Users queryUserForLogin(UserBo userBO);
}
