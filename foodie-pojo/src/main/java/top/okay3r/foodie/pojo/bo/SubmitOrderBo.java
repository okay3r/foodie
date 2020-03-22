package top.okay3r.foodie.pojo.bo;

import lombok.Data;

@Data
public class SubmitOrderBo {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
