package top.okay3r.foodie.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import top.okay3r.foodie.pojo.OrderStatus;
import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.pojo.vo.MyOrdersVo;
import top.okay3r.foodie.pojo.vo.MySubOrderItemVo;

import java.util.List;
import java.util.Map;

@Repository
public interface OrdersMapperCustom {
    List<MyOrdersVo> queryMyOrders(@Param("paramsMap") Map<String, Object> paramsMap);

    MySubOrderItemVo getSubItems(String orderId);

    /**
     * 废弃方法，原因：PageHelper无法分页
     */
    List<MyOrdersVo> queryMyOrdersDoNotUse(@Param("paramsMap") Map<String, Object> paramsMap);

    Integer getMyOrderStatusCounts(@Param("paramsMap") Map paramsMap);

    List<OrderStatus> getMyOrderTrend(@Param("paramsMap") Map paramsMap);

}
