package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.enums.PayMethod;
import top.okay3r.foodie.pojo.UserAddress;
import top.okay3r.foodie.pojo.bo.SubmitOrderBo;
import top.okay3r.foodie.utils.ApiJsonResult;

import java.util.List;

@Api(value = "订单", tags = "订单接口")
@RestController
@RequestMapping("/orders")
public class OrderController {

    /**
     * 创建订单
     */
    @PostMapping("/create")
    @ApiOperation(value = "创建订单", notes = "创建订单", httpMethod = "POST")
    public ApiJsonResult list(
            @RequestBody SubmitOrderBo submitOrderBo
    ) {

        if (submitOrderBo.getPayMethod() != PayMethod.WEIXIN.type ||
                submitOrderBo.getPayMethod() != PayMethod.ALIPAY.type
        ) {
            return ApiJsonResult.errorMsg("支付方式不支持！");
        }

        System.out.println(submitOrderBo);

        return ApiJsonResult.ok();
    }

}
