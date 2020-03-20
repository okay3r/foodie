package top.okay3r.foodie.pojo.vo;

import lombok.Data;

@Data
public class SubCategoryVo {
    private Integer subId;
    private String subName;
    private Integer subType;
    private Integer subFatherId;
}
