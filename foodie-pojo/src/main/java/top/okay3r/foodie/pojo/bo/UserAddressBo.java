package top.okay3r.foodie.pojo.bo;

import lombok.Data;

@Data
public class UserAddressBo {

    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;

}
