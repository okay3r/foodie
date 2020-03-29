package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.OrderStatus;
import top.okay3r.foodie.pojo.bo.ShopCartBo;
import top.okay3r.foodie.pojo.bo.SubmitOrderBo;
import top.okay3r.foodie.pojo.vo.OrderVo;

import java.util.List;

public interface OrderService {

    /**
     * 创建新的订单
     */
    OrderVo createOrder(SubmitOrderBo submitOrderBo, List<ShopCartBo> shopCartList);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 查询订单状态
     */
    OrderStatus queryOrderStatusInfo(String orderId);

    /**
     * 关闭超时未支付订单
     */
    void closeOrder();
}
