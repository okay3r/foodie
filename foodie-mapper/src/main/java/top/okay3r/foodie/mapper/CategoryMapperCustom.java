package top.okay3r.foodie.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.okay3r.foodie.pojo.vo.CategoryVo;
import top.okay3r.foodie.pojo.vo.NewItemsVo;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryMapperCustom {
    List<CategoryVo> getSubCatList(Integer rootCatId);

    List<NewItemsVo> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}
