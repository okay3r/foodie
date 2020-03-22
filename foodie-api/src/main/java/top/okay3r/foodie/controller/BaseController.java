package top.okay3r.foodie.controller;

public class BaseController {
    public static final Integer COMMON_PAGE_SIZE = 10;

    //购物车cookie
    public static final String FOODIE_SHOPCART = "shopcart";

    //支付成功回调地址
    public static final String PAY_RETURN_URL = "http://zbu892.natappfree.cc/orders/notifyMerchantOrderPaid";

    //支付中心地址
    public static final String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
}
