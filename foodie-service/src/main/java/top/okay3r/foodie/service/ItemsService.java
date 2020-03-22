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

    Items queryItemById(String itemId);

    List<ItemsImg> queryItemImgList(String itemId);

    List<ItemsSpec> queryItemSpecList(String itemId);

    ItemsParam queryItemParam(String itemId);

    CommentLevelCountsVo queryCommentLevelCounts(String itemId);

    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize);

    PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize);

    List<ShopCartVo> queryItemsBySpecIds(String itemSpecIds);
}
