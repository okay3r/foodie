package top.okay3r.foodie.pojo.vo;

import lombok.Data;
import top.okay3r.foodie.pojo.bo.ShopCartBo;

import java.util.List;

@Data
public class OrderVo {
    private String orderId;
    private MerchantOrdersVo merchantOrdersVO;
    private List<ShopCartBo> toRemoveShopCartBoList;
}
