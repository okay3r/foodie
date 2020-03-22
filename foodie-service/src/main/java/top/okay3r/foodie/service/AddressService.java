package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.UserAddress;
import top.okay3r.foodie.pojo.bo.UserAddressBo;

import java.util.List;

public interface AddressService {

    /**
     * 根据userId查询全部地址
     */
    List<UserAddress> queryAll(String userId);

    /**
     * 添加用户收货地址
     */
    void addNewUserAddress(UserAddressBo userAddressBo);

    /**
     * 更新用户收货地址
     */
    void updateUserAddress(UserAddressBo userAddressBo);

    /**
     * 删除用户收货地址
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 设置收货地址为默认
     */
    void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id、地址id查询收货地址
     */
    UserAddress queryUserAddress(String userId, String addressID);
}
