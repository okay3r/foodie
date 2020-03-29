package top.okay3r.foodie.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.pojo.bo.ShopCartBo;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.RedisOperator;

import java.util.ArrayList;
import java.util.List;

@Api(value = "购物车", tags = "购物车相关接口")
@RestController
@RequestMapping("/shopcart")
public class ShopCartController extends BaseController {

    @Autowired
    private RedisOperator redisOperator;

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
        String userShopCartStr = (String) this.redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        List<ShopCartBo> shopCartBoList = null;
        if (StringUtils.isNotBlank(userShopCartStr)) {
            shopCartBoList = JSON.parseArray(userShopCartStr, ShopCartBo.class);
            boolean isHaving = false;
            for (ShopCartBo bo : shopCartBoList) {
                if (shopCartBo.getSpecId().equals(bo.getSpecId())) {
                    isHaving = true;
                    bo.setBuyCounts(bo.getBuyCounts() + shopCartBo.getBuyCounts());
                    break;
                }
            }
            if (!isHaving) {
                shopCartBoList.add(shopCartBo);
            }
        } else {
            shopCartBoList = new ArrayList<>();
            shopCartBoList.add(shopCartBo);
        }
        this.redisOperator.set(FOODIE_SHOPCART + ":" + userId, JSON.toJSONString(shopCartBoList));
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
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return ApiJsonResult.errorMsg("");
        }

        String userShopCartStr = (String) this.redisOperator.get(FOODIE_SHOPCART + ":" + userId);
        if (StringUtils.isNotBlank(userShopCartStr)) {
            List<ShopCartBo> shopCartBoList = JSON.parseArray(userShopCartStr, ShopCartBo.class);
            for (ShopCartBo shopCartBo : shopCartBoList) {
                if (itemSpecId.equals(shopCartBo.getSpecId())) {
                    shopCartBoList.remove(shopCartBo);
                    break;
                }
            }
            this.redisOperator.set(FOODIE_SHOPCART + ":" + userId, JSON.toJSONString(shopCartBoList));
        }


        return ApiJsonResult.ok();
    }

}
