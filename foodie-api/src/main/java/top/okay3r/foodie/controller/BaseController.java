package top.okay3r.foodie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.service.center.MyOrdersService;
import top.okay3r.foodie.utils.ApiJsonResult;

public class BaseController {
    public static final Integer COMMON_PAGE_SIZE = 10;

    //购物车cookie
    public static final String FOODIE_SHOPCART = "shopcart";

    //支付成功回调地址
    // public static final String PAY_RETURN_URL = "http://api.foodie.com/foodie-api/orders/notifyMerchantOrderPaid";
    // public static final String PAY_RETURN_URL = "http://api.okay3r.top/foodie-api/orders/notifyMerchantOrderPaid";
    public static final String PAY_RETURN_URL = "http://127.0.0.1:8088/orders/notifyMerchantOrderPaid";

    //支付中心地址
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";

    @Autowired
    private MyOrdersService myOrdersService;

    /**
     * 用于验证用户和订单是否有关联关系，避免非法用户调用
     */
    public ApiJsonResult queryMyOrder(String userId, String orderId) {
        Orders orders = myOrdersService.queryMyOrder(userId, orderId);
        if (orderId == null) {
            return ApiJsonResult.errorMsg("订单不存在!");
        }
        return ApiJsonResult.ok(orders);
    }
}
