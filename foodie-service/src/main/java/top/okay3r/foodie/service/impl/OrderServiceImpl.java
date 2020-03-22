package top.okay3r.foodie.service.impl;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.okay3r.foodie.enums.OrderStatusEnum;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.mapper.OrderItemsMapper;
import top.okay3r.foodie.mapper.OrderStatusMapper;
import top.okay3r.foodie.mapper.OrdersMapper;
import top.okay3r.foodie.pojo.*;
import top.okay3r.foodie.pojo.bo.SubmitOrderBo;
import top.okay3r.foodie.pojo.vo.MerchantOrdersVO;
import top.okay3r.foodie.pojo.vo.OrderVo;
import top.okay3r.foodie.service.AddressService;
import top.okay3r.foodie.service.ItemsService;
import top.okay3r.foodie.service.OrderService;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;


    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVo createOrder(SubmitOrderBo submitOrderBo) {
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

        //收件人信息
        String receiverAddress = address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail();
        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(receiverAddress);

        //用于用户订单中所有的商品价格累加
        Integer totalAmount = 0;
        Integer realPayAmount = 0;

        String[] itemSpecIdArr = itemSpecIds.split(",");

        //循环设置每个商品订单
        for (String itemSpecId : itemSpecIdArr) {
            int buyCounts = 1;
            ItemsSpec itemsSpec = this.itemsService.queryItemSpecBySpecId(itemSpecId);
            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;

            String itemId = itemsSpec.getItemId();
            Items items = this.itemsService.queryItemById(itemId);
            String imgUrl = this.itemsService.queryItemMainImgById(itemId);

            OrderItems subOrderItems = new OrderItems();
            String orderItemsId = this.sid.nextShort();
            subOrderItems.setId(orderItemsId);
            subOrderItems.setOrderId(orderId);
            subOrderItems.setItemId(itemId);
            subOrderItems.setItemImg(imgUrl);
            subOrderItems.setItemName(items.getItemName());
            subOrderItems.setItemSpecId(itemSpecId);
            subOrderItems.setItemSpecName(itemsSpec.getName());
            subOrderItems.setPrice(itemsSpec.getPriceDiscount());
            subOrderItems.setBuyCounts(buyCounts);

            //插入新的商品订单
            this.orderItemsMapper.insert(subOrderItems);

            //从商品规格表中扣除库存
            this.itemsService.decreaseItemSpecStock(itemSpecId, buyCounts);
        }

        //设置订单
        newOrder.setPostAmount(postAmount);
        newOrder.setTotalAmount(totalAmount);
        newOrder.setRealPayAmount(realPayAmount);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setIsDelete(YesOrNo.NO.type);
        Date now = new Date();
        newOrder.setCreatedTime(now);
        newOrder.setUpdatedTime(now);

        //插入新的用户订单
        this.ordersMapper.insert(newOrder);

        //设置订单状态
        top.okay3r.foodie.pojo.OrderStatus orderStatus = new top.okay3r.foodie.pojo.OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(now);
        this.orderStatusMapper.insert(orderStatus);

        //构建支付信息
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setAmount(realPayAmount + postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        OrderVo orderVo = new OrderVo();
        orderVo.setOrderId(orderId);
        orderVo.setMerchantOrdersVO(merchantOrdersVO);

        return orderVo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(String orderId, Integer orderStatus) {
        OrderStatus status = new OrderStatus();
        status.setOrderId(orderId);
        status.setOrderStatus(orderStatus);
        status.setPayTime(new Date());

        this.orderStatusMapper.updateByPrimaryKeySelective(status);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatus queryOrderStatusInfo(String orderId) {
        return this.orderStatusMapper.selectByPrimaryKey(orderId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void closeOrder() {

    }
}
