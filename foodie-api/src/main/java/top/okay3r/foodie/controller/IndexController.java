package top.okay3r.foodie.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.mapper.CarouselMapper;
import top.okay3r.foodie.pojo.Carousel;
import top.okay3r.foodie.pojo.Category;
import top.okay3r.foodie.pojo.vo.CategoryVo;
import top.okay3r.foodie.pojo.vo.NewItemsVo;
import top.okay3r.foodie.service.CarouselService;
import top.okay3r.foodie.service.CategoryService;
import top.okay3r.foodie.utils.ApiJsonResult;

import java.util.List;

@Api(value = "首页", tags = "首页的相关接口")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CarouselService carouselService;

    /**
     * 获取首页轮播图
     */
    @ApiOperation(value = "获取首页轮播图", notes = "获取首页轮播图", httpMethod = "GET")
    @GetMapping("/carousel")
    public ApiJsonResult carousel() {
        List<Carousel> carouselList = this.carouselService.queryAll(YesOrNo.YES.type);
        return ApiJsonResult.ok(carouselList);
    }

    /**
     * 获取所有root分类
     */
    @ApiOperation(value = "获取root分类", notes = "获取root分类", httpMethod = "GET")
    @GetMapping("/cats")
    public ApiJsonResult cats() {
        List<Category> categoryList = this.categoryService.queryAllRootLevelCat();
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
        List<CategoryVo> subCatList = this.categoryService.getSubCatList(rootCatId);
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
