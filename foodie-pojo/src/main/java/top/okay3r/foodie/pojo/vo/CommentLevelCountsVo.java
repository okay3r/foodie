package top.okay3r.foodie.pojo.vo;

import lombok.Data;

/**
 * 评论数量VO
 */
@Data
public class CommentLevelCountsVo {
    private Integer totalCounts;
    private Integer goodCounts;
    private Integer normalCounts;
    private Integer badCounts;
}
