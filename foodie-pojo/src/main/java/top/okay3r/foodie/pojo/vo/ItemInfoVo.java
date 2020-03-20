package top.okay3r.foodie.pojo.vo;

import lombok.Data;
import sun.tools.jstat.Literal;
import top.okay3r.foodie.pojo.Items;
import top.okay3r.foodie.pojo.ItemsImg;
import top.okay3r.foodie.pojo.ItemsParam;
import top.okay3r.foodie.pojo.ItemsSpec;

import java.util.List;

@Data
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;
}
