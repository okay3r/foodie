package top.okay3r.foodie.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.stereotype.Repository;
import top.okay3r.foodie.pojo.vo.ItemCommentsVo;
import top.okay3r.foodie.pojo.vo.SearchItemVo;
import top.okay3r.foodie.pojo.vo.ShopCartVo;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemsMapperCustom {
    List<ItemCommentsVo> queryItemsComments(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemVo> searchItems(@Param("paramsMap") Map<String, Object> paramsMap);

    List<SearchItemVo> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> paramsMap);

    List<ShopCartVo> searchItemsBySpecId(@Param("paramsList") List<String> paramsList);
}
