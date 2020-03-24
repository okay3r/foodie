package top.okay3r.foodie.service.center;

import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.pojo.vo.OrderStatusCountsVo;
import top.okay3r.foodie.utils.PagedGridResult;

public interface MyOrdersService {
    /**
     * 查询我的订单
     */
    PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 更改订单状态为发货
     * @return
     */
    boolean updateDeliverOrderStatus(String orderId);

    /**
     * 更新订单状态为已收货
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 根据userID、orderID查询订单
     */
    Orders queryMyOrder(String userId, String orderId);

    /**
     * 删除订单
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 查询订单状态数
     */
    OrderStatusCountsVo queryStatusCounts(String userId);

    /**
     * 获取订单动向
     */
    PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize);
}
