package top.okay3r.foodie.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.controller.BaseController;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.pojo.OrderItems;
import top.okay3r.foodie.pojo.Orders;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.pojo.bo.center.OrderItemsCommentBO;
import top.okay3r.foodie.service.center.CenterUserService;
import top.okay3r.foodie.service.center.MyCommentService;
import top.okay3r.foodie.service.center.MyOrdersService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.List;

@Api(value = "订单评价", tags = "订单评价相关接口")
@RestController
@RequestMapping("/mycomments")
public class MyCommentsController extends BaseController {

    @Autowired
    private MyCommentService myCommentService;

    /**
     * 查询用户信息
     */
    @PostMapping("/pending")
    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    public ApiJsonResult pending(
            @RequestParam String userId,
            @RequestParam String orderId
    ) {
        ApiJsonResult checkResult = queryMyOrder(userId, orderId);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        Orders orders = (Orders) checkResult.getData();
        if (orders.getIsComment() == YesOrNo.YES.type) {
            return ApiJsonResult.errorMsg("该订单已被评价");
        }
        List<OrderItems> orderItems = this.myCommentService.queryPendingComment(orderId);
        return ApiJsonResult.ok(orderItems);
    }

    /**
     * 保存评价
     */
    @PostMapping("/saveList")
    @ApiOperation(value = "保存评价", notes = "保存评价", httpMethod = "POST")
    public ApiJsonResult pending(
            @RequestParam String userId,
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList
    ) {
        ApiJsonResult checkResult = queryMyOrder(userId, orderId);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return ApiJsonResult.errorMsg("评价不能为空");
        }
        this.myCommentService.saveComments(userId, orderId, commentList);
        return ApiJsonResult.ok();
    }

    /**
     * 查询我的评价
     */
    @PostMapping("/query")
    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    public ApiJsonResult query(
            @RequestParam String userId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer pageSize
    ) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = this.myCommentService.queryMyComments(userId, page, pageSize);
        return ApiJsonResult.ok(pagedGridResult);
    }
}
