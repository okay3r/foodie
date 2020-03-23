package top.okay3r.foodie.pojo.vo;

import lombok.Data;

@Data
public class OrderVo {
    private String orderId;
    private MerchantOrdersVo merchantOrdersVO;
}
