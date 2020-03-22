package top.okay3r.foodie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import top.okay3r.foodie.enums.CommentLevel;
import top.okay3r.foodie.enums.YesOrNo;
import top.okay3r.foodie.mapper.*;
import top.okay3r.foodie.pojo.*;
import top.okay3r.foodie.pojo.vo.CommentLevelCountsVo;
import top.okay3r.foodie.pojo.vo.ItemCommentsVo;
import top.okay3r.foodie.pojo.vo.SearchItemVo;
import top.okay3r.foodie.pojo.vo.ShopCartVo;
import top.okay3r.foodie.service.ItemsService;
import top.okay3r.foodie.utils.DesensitizationUtil;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.*;

@Service
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {
        return this.itemsMapper.selectByPrimaryKey(itemId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return this.itemsImgMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return this.itemsSpecMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return this.itemsParamMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVo queryCommentLevelCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.good.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.normal.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.bad.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVo commentLevelCountsVo = new CommentLevelCountsVo();
        commentLevelCountsVo.setTotalCounts(totalCounts);
        commentLevelCountsVo.setGoodCounts(goodCounts);
        commentLevelCountsVo.setNormalCounts(normalCounts);
        commentLevelCountsVo.setBadCounts(badCounts);

        return commentLevelCountsVo;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        PageHelper.startPage(page, pageSize);

        List<ItemCommentsVo> itemCommentsVoList = this.itemsMapperCustom.queryItemsComments(map);

        for (ItemCommentsVo vo : itemCommentsVoList) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }

        PagedGridResult pagedGridResult = setPageGrid(page, itemCommentsVoList);
        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);

        List<SearchItemVo> searchItemVoList = this.itemsMapperCustom.searchItems(map);

        PagedGridResult pagedGridResult = setPageGrid(page, searchItemVoList);
        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItemsByThirdCat(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        PageHelper.startPage(page, pageSize);

        List<SearchItemVo> searchItemVoList = this.itemsMapperCustom.searchItemsByThirdCat(map);

        PagedGridResult pagedGridResult = setPageGrid(page, searchItemVoList);
        return pagedGridResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopCartVo> queryItemsBySpecIds(String itemSpecIds) {

        String[] split = itemSpecIds.split(",");
        List<String> paramsList = new ArrayList<>();
        Collections.addAll(paramsList, split);
        List<ShopCartVo> shopCartVoList = this.itemsMapperCustom.searchItemsBySpecId(paramsList);
        return shopCartVoList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpec queryItemSpecBySpecId(String itemSpecId) {
        return this.itemsSpecMapper.selectByPrimaryKey(itemSpecId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg img = new ItemsImg();
        img.setItemId(itemId);
        img.setIsMain(YesOrNo.YES.type);

        return this.itemsImgMapper.selectOne(img).getUrl();
    }

    @Override
    public void decreaseItemSpecStock(String specId, Integer pendingCounts) {
        //TODO 分布式锁

        int res = this.itemsMapperCustom.decreaseItemSpecStock(specId, pendingCounts);
        if (res != 1) {
            throw new RuntimeException("订单创建失败，原因：库存不足!");
        }
    }

    /**
     * 设置分页
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    PagedGridResult setPageGrid(Integer page, List<?> list) {
        PagedGridResult pagedGridResult = new PagedGridResult();
        PageInfo pageInfo = new PageInfo(list);
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageInfo.getPages());
        pagedGridResult.setRecords(pageInfo.getTotal());
        return pagedGridResult;
    }

    /**
     * 查询评论数量
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return this.itemsCommentsMapper.selectCount(condition);
    }


}
