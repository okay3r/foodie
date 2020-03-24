package top.okay3r.foodie.service.center;

import top.okay3r.foodie.pojo.OrderItems;
import top.okay3r.foodie.pojo.bo.center.OrderItemsCommentBO;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.List;

public interface MyCommentService {

    /**
     * 查询订单中未被评论的商品
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存评价
     */
    void saveComments(String userId, String orderId, List<OrderItemsCommentBO> commentList);

    /**
     * 查询我的评价
     */
    PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
