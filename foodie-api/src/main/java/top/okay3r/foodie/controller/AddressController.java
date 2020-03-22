package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.pojo.UserAddress;
import top.okay3r.foodie.pojo.bo.UserAddressBo;
import top.okay3r.foodie.service.AddressService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.MobileEmailUtils;

import java.io.StringBufferInputStream;
import java.util.List;

@Api(value = "收货地址", tags = "收货地址接口")
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 根据用户id查询收货地址
     */
    @PostMapping("/list")
    @ApiOperation(value = "根据用户id查询收货地址", notes = "根据用户id查询收货地址", httpMethod = "POST")
    public ApiJsonResult list(
            @RequestParam("userId") String userId
    ) {
        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg("");
        }

        List<UserAddress> list = this.addressService.queryAll(userId);

        return ApiJsonResult.ok(list);
    }

    /**
     * 新增收货地址
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增收货地址", notes = "新增收货地址", httpMethod = "POST")
    public ApiJsonResult add(@RequestBody UserAddressBo userAddressBo) {

        ApiJsonResult checkStatus = checkAddress(userAddressBo);
        if (checkStatus.getStatus() != 200) {
            return checkStatus;
        }

        this.addressService.addNewUserAddress(userAddressBo);

        return ApiJsonResult.ok();
    }

    /**
     * 修改收货地址
     */
    @PostMapping("/update")
    @ApiOperation(value = "修改收货地址", notes = "修改收货地址", httpMethod = "POST")
    public ApiJsonResult update(@RequestBody UserAddressBo userAddressBo) {

        if (StringUtils.isBlank(userAddressBo.getAddressId())) {
            return ApiJsonResult.errorMsg("addressId 不能为空");
        }

        ApiJsonResult checkStatus = this.checkAddress(userAddressBo);
        if (checkStatus.getStatus() != 200) {
            return checkStatus;
        }

        this.addressService.updateUserAddress(userAddressBo);

        return ApiJsonResult.ok();
    }

    /**
     * 删除收货地址
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址", httpMethod = "POST")
    public ApiJsonResult delete(
            @RequestParam String userId,
            @RequestParam String addressId
    ) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiJsonResult.errorMsg("");
        }

        this.addressService.deleteUserAddress(userId, addressId);

        return ApiJsonResult.ok();
    }

    /**
     * 设置默认收货地址
     */
    @PostMapping("/setDefault")
    @ApiOperation(value = "设置默认收货地址", notes = "设置默认收货地址", httpMethod = "POST")
    public ApiJsonResult setDefault(
            @RequestParam String userId,
            @RequestParam String addressId
    ) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiJsonResult.errorMsg("");
        }

        this.addressService.updateUserAddressToBeDefault(userId, addressId);

        return ApiJsonResult.ok();
    }

    private ApiJsonResult checkAddress(UserAddressBo addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return ApiJsonResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return ApiJsonResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return ApiJsonResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return ApiJsonResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return ApiJsonResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return ApiJsonResult.errorMsg("收货地址信息不能为空");
        }

        return ApiJsonResult.ok();
    }

}
