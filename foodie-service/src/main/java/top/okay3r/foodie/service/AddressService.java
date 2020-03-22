package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.UserAddress;
import top.okay3r.foodie.pojo.bo.UserAddressBo;

import java.util.List;

public interface AddressService {
    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(UserAddressBo userAddressBo);

    void updateUserAddress(UserAddressBo userAddressBo);

    void deleteUserAddress(String userId, String addressId);

    void updateUserAddressToBeDefault(String userId, String addressId);

    UserAddress queryUserAddress(String userId, String addressID);
}
