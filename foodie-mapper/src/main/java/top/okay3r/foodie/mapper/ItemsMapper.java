package top.okay3r.foodie.mapper;

import org.springframework.stereotype.Repository;
import top.okay3r.foodie.my.mapper.MyMapper;
import top.okay3r.foodie.pojo.Items;

@Repository
public interface ItemsMapper extends MyMapper<Items> {
}