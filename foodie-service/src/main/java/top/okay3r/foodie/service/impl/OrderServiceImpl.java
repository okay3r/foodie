package top.okay3r.foodie.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.jvm.hotspot.debugger.Address;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.pojo.UserAddress;
import top.okay3r.foodie.pojo.bo.SubmitOrderBo;
import top.okay3r.foodie.service.AddressService;
import top.okay3r.foodie.service.OrderService;

import java.util.Date;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void createOrder(SubmitOrderBo submitOrderBo) {
        String userId = submitOrderBo.getUserId();
        String addressId = submitOrderBo.getAddressId();
        String itemSpecIds = submitOrderBo.getItemSpecIds();
        String leftMsg = submitOrderBo.getLeftMsg();
        Integer payMethod = submitOrderBo.getPayMethod();

        //包邮 邮费设置为0
        Integer postAmount = 0;

        String orderId = sid.nextShort();
        UserAddress address = this.addressService.queryUserAddress(userId, addressId);

        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        String receiverAddress = address.getProvince() + " "
                                + address.getCity() + " "
                                + address.getDistrict() + " "
                                + address.getDetail();
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(receiverAddress);

        //TODO
        // newOrder.setTotalAmount();
        // newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount);

        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);

        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);

        Date now = new Date();
        newOrder.setCreatedTime(now);
        newOrder.setUpdatedTime(now);
    }
}
