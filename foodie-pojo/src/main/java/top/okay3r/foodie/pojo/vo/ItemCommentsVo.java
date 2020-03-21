package top.okay3r.foodie.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ItemCommentsVo {

    private Integer commentLevel;
    private String content;
    private String specName;
    private Date createTime;
    private String nickname;
    private String userFace;

}
