package yll.entity;

import lombok.Data;

/** 意见反馈 */
@SuppressWarnings("serial")
@Data
public class CustomerFeedback extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 活动id */
    private String content;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
