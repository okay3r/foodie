package top.okay3r.foodie.pojo.vo;

import lombok.Data;

@Data
public class ShopCartVo {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer priceDiscount;
    private Integer priceNormal;
}
