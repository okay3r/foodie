package top.okay3r.foodie.service.center;

import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.center.CenterUserBO;

public interface CenterUserService {
    /**
     * 根据id查找用户
     */
    Users queryUserInfoById(String userId);

    /**
     * 更新用户信息
     */
    Users updateUserInfo(String userId, CenterUserBO centerUserBO);

    /**
     * 更新用户头像
     */
    Users updateUserFace(String userId, String finalUserFaceUrl);
}
