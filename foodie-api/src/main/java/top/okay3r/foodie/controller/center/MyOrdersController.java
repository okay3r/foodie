package top.okay3r.foodie.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.controller.BaseController;
import top.okay3r.foodie.pojo.vo.OrderStatusCountsVo;
import top.okay3r.foodie.service.center.MyOrdersService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.PagedGridResult;

@Api(value = "用户中心我的订单", tags = "用户中心我的订单")
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    /**
     * 查询用户订单
     */
    @PostMapping("/query")
    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    public ApiJsonResult query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg("");
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = this.myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return ApiJsonResult.ok(pagedGridResult);
    }

    /**
     * 商家发货
     */
    @GetMapping("/deliver")
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    public ApiJsonResult deliver(@RequestParam String orderId) {
        if (StringUtils.isBlank(orderId)) {
            return ApiJsonResult.errorMsg("订单id不能为空");
        }
        this.myOrdersService.updateDeliverOrderStatus(orderId);
        return ApiJsonResult.ok();
    }

    /**
     * 确认收货
     */
    @PostMapping("/confirmReceive")
    @ApiOperation(value = "确认收货", notes = "确认收货", httpMethod = "POST")
    public ApiJsonResult confirmReceive(
            @RequestParam String userId,
            @RequestParam String orderId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
            return ApiJsonResult.errorMsg("");
        }
        ApiJsonResult checkResult = queryMyOrder(userId, orderId);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        boolean res = this.myOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return ApiJsonResult.errorMsg("确认收货失败！");
        }
        return ApiJsonResult.ok();
    }

    /**
     * 删除订单
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
    public ApiJsonResult delete(
            @RequestParam String userId,
            @RequestParam String orderId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(orderId)) {
            return ApiJsonResult.errorMsg("");
        }
        ApiJsonResult checkResult = queryMyOrder(userId, orderId);
        if (checkResult.getStatus() != 200) {
            return checkResult;
        }
        boolean res = this.myOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return ApiJsonResult.errorMsg("删除订单失败！");
        }
        return ApiJsonResult.ok();
    }

    /**
     * 查询订单状态数状况
     */
    @PostMapping("/statusCounts")
    @ApiOperation(value = "查询订单状态数状况", notes = "查询订单状态数状况", httpMethod = "POST")
    public ApiJsonResult statusCounts(
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg("");
        }
        OrderStatusCountsVo orderStatusCountsVo = this.myOrdersService.queryStatusCounts(userId);
        return ApiJsonResult.ok(orderStatusCountsVo);
    }

    /**
     * 查询订单动向
     */
    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public ApiJsonResult trend(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return ApiJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGridResult grid = myOrdersService.getOrdersTrend(userId, page, pageSize);

        return ApiJsonResult.ok(grid);
    }

}
