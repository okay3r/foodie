package top.okay3r.foodie.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.pojo.Carousel;
import top.okay3r.foodie.pojo.Category;
import top.okay3r.foodie.pojo.vo.CategoryVo;
import top.okay3r.foodie.pojo.vo.NewItemsVo;
import top.okay3r.foodie.service.CarouselService;
import top.okay3r.foodie.service.CategoryService;
import top.okay3r.foodie.utils.ApiJsonResult;
import top.okay3r.foodie.utils.RedisOperator;

import java.util.List;

@Api(value = "首页", tags = "首页的相关接口")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 获取首页轮播图
     */
    @ApiOperation(value = "获取首页轮播图", notes = "获取首页轮播图", httpMethod = "GET")
    @GetMapping("/carousel")
    public ApiJsonResult carousel() {
        String carouselStr = (String) this.redisOperator.get("carousel");
        List<Carousel> carouselList = null;
        if (StringUtils.isBlank(carouselStr)) {
            carouselList = this.carouselService.queryAll(YesOrNo.YES.type);
            this.redisOperator.set("carousel", JSON.toJSONString(carouselList));
        } else {
            carouselList = JSON.parseArray(carouselStr, Carousel.class);
        }
        return ApiJsonResult.ok(carouselList);
    }

    /**
     * 获取所有root分类
     */
    @ApiOperation(value = "获取root分类", notes = "获取root分类", httpMethod = "GET")
    @GetMapping("/cats")
    public ApiJsonResult cats() {
        String rootCatsStr = (String) this.redisOperator.get("rootCats");
        List<Category> categoryList = null;
        if (StringUtils.isBlank(rootCatsStr)) {
            categoryList = this.categoryService.queryAllRootLevelCat();
            this.redisOperator.set("rootCats", JSON.toJSONString(categoryList));
        } else {
            categoryList = JSON.parseArray(rootCatsStr, Category.class);
        }
        return ApiJsonResult.ok(categoryList);
    }

    /**
     * 获取商品子分类
     */
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public ApiJsonResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable(value = "rootCatId") Integer rootCatId
    ) {
        if (rootCatId == null) {
            return ApiJsonResult.errorMsg("分类id不存在");
        }

        String subCatsStr = (String) this.redisOperator.get("subCats:" + rootCatId);
        List<CategoryVo> subCatList = null;
        if (StringUtils.isBlank(subCatsStr)) {
            subCatList = this.categoryService.getSubCatList(rootCatId);
            this.redisOperator.set("subCats:" + rootCatId, JSON.toJSONString(subCatList));
        } else {
            subCatList = JSON.parseArray(subCatsStr, CategoryVo.class);
        }

        return ApiJsonResult.ok(subCatList);
    }

    /**
     * 获取推荐新商品
     */
    @ApiOperation(value = "获取推荐新商品", notes = "获取推荐新商品", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public ApiJsonResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true)
            @PathVariable(value = "rootCatId") Integer rootCatId
    ) {
        if (rootCatId == null) {
            return ApiJsonResult.errorMsg("分类id不存在");
        }
        List<NewItemsVo> newItemsVoList = this.categoryService.getSixNewItemsLazy(rootCatId);
        return ApiJsonResult.ok(newItemsVoList);
    }

}
