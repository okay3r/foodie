package top.okay3r.foodie.pojo.vo;

import lombok.Data;

@Data
public class SearchItemVo {

    private String itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price;

}
