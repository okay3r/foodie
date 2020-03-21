package top.okay3r.foodie.pojo.bo;

import lombok.Data;

@Data
public class ShopCartBo {
    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private Integer specId;
    private String specName;
    private Integer buyCounts;
    private Integer priceDiscount;
    private Integer priceNormal;
}
