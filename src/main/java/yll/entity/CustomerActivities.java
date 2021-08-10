package yll.entity;

import lombok.Data;

/** 我的活动 */
@SuppressWarnings("serial")
@Data
public class CustomerActivities extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 活动id */
    private String activitiesId;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
