package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Carousel;
import top.okay3r.foodie.pojo.Category;
import top.okay3r.foodie.pojo.vo.CategoryVo;
import top.okay3r.foodie.pojo.vo.NewItemsVo;

import java.util.List;

public interface CategoryService {

    /**
     * 查询全部一级分类
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 查询一级分类下的子分类
     */
    List<CategoryVo> getSubCatList(Integer rootCatId);

    /**
     * 根据分类查询首页推荐商品
     */
    List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId);

}
