package top.okay3r.foodie.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 用于封装首页推荐商品的一个大分类
 */
@Data
public class NewItemsVo {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    private List<SimpleItemVo> simpleItemList;

}
