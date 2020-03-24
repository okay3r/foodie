package top.okay3r.foodie.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import top.okay3r.foodie.enums.OrderStatusEnum;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.mapper.OrderStatusMapper;
import top.okay3r.foodie.mapper.OrdersMapper;
import top.okay3r.foodie.mapper.OrdersMapperCustom;
import top.okay3r.foodie.pojo.OrderStatus;
import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.pojo.vo.MyOrdersVo;
import top.okay3r.foodie.pojo.vo.OrderStatusCountsVo;
import top.okay3r.foodie.service.center.MyOrdersService;
import top.okay3r.foodie.service.impl.BaseService;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);

        List<MyOrdersVo> myOrdersVoList = this.ordersMapperCustom.queryMyOrders(map);

        PagedGridResult pagedGridResult = setPageGrid(page, myOrdersVoList);

        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateDeliverOrderStatus(String orderId) {
        OrderStatus updateOrder = new OrderStatus();
        updateOrder.setDeliverTime(new Date());
        updateOrder.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        int res = this.orderStatusMapper.updateByExampleSelective(updateOrder, example);
        return res == 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean updateReceiveOrderStatus(String orderId) {
        OrderStatus updateOrderStatus = new OrderStatus();
        updateOrderStatus.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        updateOrderStatus.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        int res = this.orderStatusMapper.updateByExampleSelective(updateOrderStatus, example);

        return res == 1;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setId(orderId);
        orders.setIsDelete(YesOrNo.NO.type);
        return this.ordersMapper.selectOne(orders);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteOrder(String userId, String orderId) {
        Orders orders = new Orders();
        orders.setIsDelete(YesOrNo.YES.type);
        orders.setUpdatedTime(new Date());

        Example example = new Example(Orders.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("id", orderId);

        int res = this.ordersMapper.updateByExampleSelective(orders, example);
        return res == 1;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public OrderStatusCountsVo queryStatusCounts(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        map.put("orderStatus", OrderStatusEnum.WAIT_PAY.type);
        int waitPayCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);
        int waitDeliverCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);
        int waitReceiveCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        map.put("orderStatus", OrderStatusEnum.SUCCESS.type);
        map.put("isComment", YesOrNo.NO.type);
        int waitCommentCounts = ordersMapperCustom.getMyOrderStatusCounts(map);

        OrderStatusCountsVo countsVo = new OrderStatusCountsVo(waitPayCounts,
                waitDeliverCounts,
                waitReceiveCounts,
                waitCommentCounts);

        return countsVo;
    }

    @Override
    public PagedGridResult getOrdersTrend(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<OrderStatus> myOrderTrendList = this.ordersMapperCustom.getMyOrderTrend(map);
        return setPageGrid(page, myOrderTrendList);
    }


}
