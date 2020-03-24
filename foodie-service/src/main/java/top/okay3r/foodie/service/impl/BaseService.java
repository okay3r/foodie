package top.okay3r.foodie.service.impl;

import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.okay3r.foodie.utils.PagedGridResult;

import java.util.List;

public class BaseService {

    /**
     * 设置分页
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedGridResult setPageGrid(Integer page, List<?> list) {
        PagedGridResult pagedGridResult = new PagedGridResult();
        PageInfo pageInfo = new PageInfo(list);
        pagedGridResult.setPage(page);
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageInfo.getPages());
        pagedGridResult.setRecords(pageInfo.getTotal());
        return pagedGridResult;
    }

}
