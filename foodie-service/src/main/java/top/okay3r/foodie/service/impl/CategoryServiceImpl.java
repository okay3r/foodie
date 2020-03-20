package top.okay3r.foodie.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import top.okay3r.foodie.mapper.CarouselMapper;
import top.okay3r.foodie.mapper.CategoryMapper;
import top.okay3r.foodie.mapper.CategoryMapperCustom;
import top.okay3r.foodie.pojo.Carousel;
import top.okay3r.foodie.pojo.Category;
import top.okay3r.foodie.pojo.vo.CategoryVo;
import top.okay3r.foodie.pojo.vo.NewItemsVo;
import top.okay3r.foodie.service.CategoryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryAllRootLevelCat() {

        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> categoryList = this.categoryMapper.selectByExample(example);

        return categoryList;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<CategoryVo> getSubCatList(Integer rootCatId) {
        return this.categoryMapperCustom.getSubCatList(rootCatId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<NewItemsVo> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return this.categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
