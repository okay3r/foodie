package top.okay3r.foodie.pojo.vo;

import lombok.Data;


/**
 * 用于封装首页推荐商品的 单个商品
 */
@Data
public class SimpleItemVo {

    private String itemId;
    private String itemName;
    private String itemUrl;

}
