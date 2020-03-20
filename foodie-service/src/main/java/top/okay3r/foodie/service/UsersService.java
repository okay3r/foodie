package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.UserBO;

public interface UsersService {
    boolean queryUsernameIsExists(String username);

    Users createUser(UserBO userBO);

    Users queryUserForLogin(UserBO userBO);
}
