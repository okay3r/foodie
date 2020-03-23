package top.okay3r.foodie.controller.center;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.controller.BaseController;
import top.okay3r.foodie.pojo.Users;
import top.okay3r.foodie.service.center.MyOrdersService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.PagedGridResult;

@Api(value = "用户中心我的订单", tags = "用户中心我的订单")
@RestController
@RequestMapping("/myorders")
public class MyOrdersController extends BaseController {

    @Autowired
    private MyOrdersService centerUserService;

    /**
     * 查询用户信息
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

        PagedGridResult pagedGridResult = this.centerUserService.queryMyOrder(userId, orderStatus, page, pageSize);
        return ApiJsonResult.ok(pagedGridResult);
    }

    private void setNullProperty(Users userRes) {
        userRes.setPassword(null);
        userRes.setCreatedTime(null);
        userRes.setUpdatedTime(null);
    }
}
