package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Items;
import top.okay3r.foodie.pojo.ItemsImg;
import top.okay3r.foodie.pojo.ItemsParam;
import top.okay3r.foodie.pojo.ItemsSpec;
import top.okay3r.foodie.pojo.vo.CommentLevelCountsVo;
import top.okay3r.foodie.pojo.vo.ShopCartVo;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.List;

public interface ItemsService {

    /**
     * 根据商品id查询商品
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品图片
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询总的评论数量
     */
    CommentLevelCountsVo queryCommentLevelCounts(String itemId);

    /**
     * 根据评论等级查询评论
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 根据keyWords模糊查询商品
     */
    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据三级分类查询商品
     */
    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    /**
     * 根据商品规格id列表查询商品
     */
    List<ShopCartVo> queryItemsBySpecIds(String itemSpecIds);

    /**
     * 根据id查询商品规格
     */
    ItemsSpec queryItemSpecBySpecId(String itemSpecId);

    /**
     * 根据商品id查询主图
     */
    String queryItemMainImgById(String itemId);

    /**
     * 更新库存数量
     */
    void decreaseItemSpecStock(String specId, Integer pendingCounts);
}
