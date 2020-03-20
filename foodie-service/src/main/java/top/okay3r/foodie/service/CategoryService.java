package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Carousel;
import top.okay3r.foodie.pojo.Category;
import top.okay3r.foodie.pojo.vo.CategoryVo;
import top.okay3r.foodie.pojo.vo.NewItemsVo;

import java.util.List;

public interface CategoryService {

    List<Category> queryAllRootLevelCat();

    List<CategoryVo> getSubCatList(Integer rootCatId);

    List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId);

}
