package top.okay3r.foodie.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.okay3r.foodie.mapper.OrdersMapperCustom;
import top.okay3r.foodie.pojo.vo.MyOrdersVo;
import top.okay3r.foodie.service.center.MyOrdersService;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Override
    public PagedGridResult queryMyOrder(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }
        PageHelper.startPage(page, pageSize);

        List<MyOrdersVo> myOrdersVoList = this.ordersMapperCustom.queryMyOrders(map);

        PagedGridResult pagedGridResult = setPageGrid(page, myOrdersVoList);

        return pagedGridResult;
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
}
