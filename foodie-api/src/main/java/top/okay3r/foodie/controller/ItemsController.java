package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.okay3r.foodie.pojo.Items;
import top.okay3r.foodie.pojo.ItemsImg;
import top.okay3r.foodie.pojo.ItemsParam;
import top.okay3r.foodie.pojo.ItemsSpec;
import top.okay3r.foodie.pojo.vo.CommentLevelCountsVo;
import top.okay3r.foodie.pojo.vo.ItemInfoVo;
import top.okay3r.foodie.service.ItemsService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.List;

@Api(value = "商品信息", tags = "商品信息相关接口")
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController{

    @Autowired
    private ItemsService itemsService;

    /**
     * 获取商品详情
     */
    @GetMapping("/info/{itemId}")
    @ApiOperation(value = "获取商品详情", notes = "获取商品详情", httpMethod = "GET")
    public ApiJsonResult getItemInfo(@PathVariable("itemId") String itemId) {
        Items item = this.itemsService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = this.itemsService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = this.itemsService.queryItemSpecList(itemId);
        ItemsParam itemsParams = this.itemsService.queryItemParam(itemId);
        ItemInfoVo itemInfoVo = new ItemInfoVo();
        itemInfoVo.setItem(item);
        itemInfoVo.setItemImgList(itemsImgList);
        itemInfoVo.setItemSpecList(itemsSpecList);
        itemInfoVo.setItemParams(itemsParams);
        return ApiJsonResult.ok(itemInfoVo);
    }

    /**
     * 获取商品评价等级
     */
    @GetMapping("/commentLevel")
    @ApiOperation(value = "获取商品评价等级", notes = "获取商品评价等级", httpMethod = "GET")
    public ApiJsonResult commentLevel(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam("itemId") String itemId
    ) {
        CommentLevelCountsVo commentLevelCountsVo = this.itemsService.queryCommentLevelCounts(itemId);
        return ApiJsonResult.ok(commentLevelCountsVo);
    }

    /**
     * 获取商品评价
     */
    @GetMapping("/comments")
    @ApiOperation(value = "获取商品评价", notes = "获取商品评价", httpMethod = "GET")
    public ApiJsonResult comments(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ) {
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = this.itemsService.queryPagedComments(itemId, level, page, pageSize);
        return ApiJsonResult.ok(pagedGridResult);
    }

    /**
     * 搜索商品
     */
    @GetMapping("/search")
    @ApiOperation(value = "搜索商品", notes = "搜索商品", httpMethod = "GET")
    public ApiJsonResult search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ) {
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = this.itemsService.searchItems(keywords, sort, page, pageSize);
        return ApiJsonResult.ok(pagedGridResult);
    }

    /**
     * 根据类别搜索商品
     */
    @GetMapping("/catItems")
    @ApiOperation(value = "根据类别搜索商品", notes = "根据类别搜索商品", httpMethod = "GET")
    public ApiJsonResult catItems(
            @ApiParam(name = "catId", value = "类别id", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize
    ) {
        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = this.itemsService.searchItemsByThirdCat(catId, sort, page, pageSize);
        return ApiJsonResult.ok(pagedGridResult);
    }

}
