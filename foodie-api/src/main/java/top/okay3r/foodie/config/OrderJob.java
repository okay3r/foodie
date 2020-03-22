package top.okay3r.foodie.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 订单定时任务
 */
@Component
public class OrderJob {

    @Scheduled(cron = "0/3 * * * * ? ")
    public void autoCloseOrder() {
    }
}
