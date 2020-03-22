package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.pojo.bo.ShopCartBo;
import top.okay3r.foodie.utils.ApiJsonResult;

@Api(value = "购物车", tags = "购物车相关接口")
@RestController
@RequestMapping("/shopcart")
public class ShopCartController {

    /**
     * 将商品添加至购物车
     */
    @PostMapping("/add")
    @ApiOperation(value = "将商品添加至购物车", notes = "将商品添加至购物车", httpMethod = "POST")
    public ApiJsonResult getItemInfo(
            @RequestParam("userId") String userId,
            @RequestBody ShopCartBo shopCartBo
    ) {
        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg("");
        }

        System.out.println(shopCartBo);
        //TODO 用户登录时，将购物车中的数据添加在redis中

        return ApiJsonResult.ok();
    }

    /**
     * 从购物车中删除商品
     */
    @PostMapping("/del")
    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    public ApiJsonResult getItemInfo(
            @RequestParam("userId") String userId,
            @RequestParam("itemSpecId") String itemSpecId
    ) {
        if (StringUtils.isBlank(userId)||StringUtils.isBlank(itemSpecId)) {
            return ApiJsonResult.errorMsg("");
        }

        //TODO 用户登录时，将redis中对应的购物车数据删除

        return ApiJsonResult.ok();
    }

}
