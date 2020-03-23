package top.okay3r.foodie.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.pojo.vo.MyOrdersVo;

import java.util.List;
import java.util.Map;

@Repository
public interface OrdersMapperCustom {
    //TODO 定义VO
    List<MyOrdersVo> queryMyOrders(@Param("paramsMap") Map<String, Object> paramsMap);
}
