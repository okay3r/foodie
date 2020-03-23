package top.okay3r.foodie.service.center;

import top.okay3r.foodie.utils.PagedGridResult;

public interface MyOrdersService {
    PagedGridResult queryMyOrder(String userId, Integer orderStatus, Integer page, Integer pageSize);
}
