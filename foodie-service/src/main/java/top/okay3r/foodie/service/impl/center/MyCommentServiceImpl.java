package top.okay3r.foodie.service.impl.center;

import com.github.pagehelper.PageHelper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.mapper.ItemsCommentsMapperCustom;
import top.okay3r.foodie.mapper.OrderItemsMapper;
import top.okay3r.foodie.mapper.OrderStatusMapper;
import top.okay3r.foodie.mapper.OrdersMapper;
import top.okay3r.foodie.pojo.OrderItems;
import top.okay3r.foodie.pojo.OrderStatus;
import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.pojo.bo.center.OrderItemsCommentBO;
import top.okay3r.foodie.pojo.vo.MyCommentVo;
import top.okay3r.foodie.service.center.MyCommentService;
import top.okay3r.foodie.service.impl.BaseService;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyCommentServiceImpl extends BaseService implements MyCommentService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems record = new OrderItems();
        record.setOrderId(orderId);
        return this.orderItemsMapper.select(record);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String userId, String orderId, List<OrderItemsCommentBO> commentList) {
        for (OrderItemsCommentBO commentBO : commentList) {
            commentBO.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        this.itemsCommentsMapperCustom.saveComments(map);

        Orders orders = new Orders();
        orders.setId(orderId);
        orders.setIsComment(YesOrNo.YES.type);
        this.ordersMapper.updateByPrimaryKeySelective(orders);

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        this.orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        PageHelper.startPage(page, pageSize);
        List<MyCommentVo> myCommentVoList = this.itemsCommentsMapperCustom.queryMyComments(map);
        PagedGridResult pagedGridResult = setPageGrid(page, myCommentVoList);
        return pagedGridResult;
    }
}
