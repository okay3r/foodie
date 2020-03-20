package top.okay3r.foodie.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 封装商品分类
 */
@Data
public class CategoryVo {
    private Integer id;
    private String name;
    private Integer type;
    private Integer fatherId;

    private List<SubCategoryVo> subCatList;

}
