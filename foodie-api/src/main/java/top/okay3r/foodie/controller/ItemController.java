package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.okay3r.foodie.pojo.Items;
import top.okay3r.foodie.pojo.ItemsImg;
import top.okay3r.foodie.pojo.ItemsParam;
import top.okay3r.foodie.pojo.ItemsSpec;
import top.okay3r.foodie.pojo.vo.ItemInfoVo;
import top.okay3r.foodie.service.ItemsService;
import top.okay3r.foodie.utils.ApiJsonResult;

import java.util.List;

@Api(value = "商品信息", tags = "商品信息相关接口")
@RestController
@RequestMapping("/items")
public class ItemController {

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
}
