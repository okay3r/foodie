package top.okay3r.foodie.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.mapper.UserAddressMapper;
import top.okay3r.foodie.pojo.UserAddress;
import top.okay3r.foodie.pojo.bo.UserAddressBo;
import top.okay3r.foodie.service.AddressService;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return this.userAddressMapper.select(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(UserAddressBo userAddressBo) {
        String userId = userAddressBo.getUserId();

        List<UserAddress> userAddressList = this.queryAll(userId);

        Integer isDefault = 0;
        if (userAddressList == null && userAddressList.isEmpty() || userAddressList.size() == 0) {
            isDefault = 1;
        }

        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressBo, userAddress);
        String addressId = sid.nextShort();
        userAddress.setIsDefault(isDefault);
        userAddress.setId(addressId);
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        this.userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(UserAddressBo userAddressBo) {
        String addressId = userAddressBo.getAddressId();
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressBo, userAddress);
        userAddress.setId(addressId);
        userAddress.setUpdatedTime(new Date());

        this.userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        userAddress.setId(addressId);
        this.userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {

        //将原来的地址设置为不默认
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> queryAddressList = this.userAddressMapper.select(queryAddress);
        for (UserAddress address : queryAddressList) {
            address.setIsDefault(YesOrNo.NO.type);
            this.userAddressMapper.updateByPrimaryKeySelective(address);
        }

        //根据地址id修改为默认地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        this.userAddressMapper.updateByPrimaryKeySelective(defaultAddress);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressID) {
        UserAddress record = new UserAddress();
        record.setId(addressID);
        record.setUserId(userId);
        return this.userAddressMapper.selectOne(record);
    }
}
