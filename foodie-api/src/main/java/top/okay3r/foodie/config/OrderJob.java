package top.okay3r.foodie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.okay3r.foodie.service.OrderService;
import top.okay3r.foodie.utils.DateUtil;

/**
 * 订单定时任务
 */
@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/3 * * * * ? ")
    public void autoCloseOrder() {
        System.out.println("执行定时任务" + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
        this.orderService.closeOrder();
    }
}
