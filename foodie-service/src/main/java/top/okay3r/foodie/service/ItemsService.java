package top.okay3r.foodie.service;

import top.okay3r.foodie.pojo.Items;
import top.okay3r.foodie.pojo.ItemsImg;
import top.okay3r.foodie.pojo.ItemsParam;
import top.okay3r.foodie.pojo.ItemsSpec;

import java.util.List;

public interface ItemsService {

    Items queryItemById(String itemId);

    List<ItemsImg> queryItemImgList(String itemId);

    public List<ItemsSpec> queryItemSpecList(String itemId);

    public ItemsParam queryItemParam(String itemId);


}
