package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import top.okay3r.foodie.enums.OrderStatusEnum;
import top.okay3r.foodie.enums.PayMethod;
import top.okay3r.foodie.pojo.OrderStatus;
import top.okay3r.foodie.pojo.bo.SubmitOrderBo;
import top.okay3r.foodie.pojo.vo.MerchantOrdersVo;
import top.okay3r.foodie.pojo.vo.OrderVo;
import top.okay3r.foodie.service.OrderService;
import top.okay3r.foodie.utils.ApiJsonResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单", tags = "订单接口")
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 创建订单
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    public ApiJsonResult list(
            @RequestBody SubmitOrderBo submitOrderBo,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        if (submitOrderBo.getPayMethod() != PayMethod.WEIXIN.type &&
                submitOrderBo.getPayMethod() != PayMethod.ALIPAY.type
        ) {
            return ApiJsonResult.errorMsg("支付方式不支持！");
        }

        System.out.println(submitOrderBo);
        OrderVo orderVo = this.orderService.createOrder(submitOrderBo);
        MerchantOrdersVo merchantOrdersVO = orderVo.getMerchantOrdersVO();
        merchantOrdersVO.setReturnUrl(PAY_RETURN_URL);
        //设置为一分钱
        merchantOrdersVO.setAmount(1);

        //向支付中心发送当前订单，用于保存支付中心的订单数据
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId", "imooc");
        httpHeaders.add("password", "imooc");
        HttpEntity<MerchantOrdersVo> entity = new HttpEntity<>(merchantOrdersVO, httpHeaders);
        ResponseEntity<ApiJsonResult> responseEntity = restTemplate.postForEntity(PAYMENT_URL, entity, ApiJsonResult.class);

        ApiJsonResult paymentResult = responseEntity.getBody();
        if (paymentResult.getStatus() != 200) {
            return ApiJsonResult.errorMsg("支付中心订单创建失败，请联系管理员");
        }

        //TODO redis清空购物车
        // CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        return ApiJsonResult.ok(orderVo.getOrderId());
    }

    /**
     * 更新订单状态
     */
    @PostMapping("/notifyMerchantOrderPaid")
    @ApiOperation(value = "更新订单状态", notes = "更新订单状态", httpMethod = "POST")
    public ApiJsonResult notifyMerchantOrderPaid(@RequestParam String merchantOrderId) {
        this.orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return ApiJsonResult.ok();
    }

    /**
     * 查询支付状态
     */
    @PostMapping("/getPaidOrderInfo")
    @ApiOperation(value = "查询支付状态", notes = "查询支付状态", httpMethod = "POST")
    public ApiJsonResult getPaidOrderInfo(@RequestParam String orderId) {
        OrderStatus orderStatus = this.orderService.queryOrderStatusInfo(orderId);

        return ApiJsonResult.ok(orderStatus);
    }


}
