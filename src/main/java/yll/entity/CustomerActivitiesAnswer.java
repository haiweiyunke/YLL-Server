package yll.entity;

import lombok.Data;

/** 我的活动-知识竞赛结果表 */
@SuppressWarnings("serial")
@Data
public class CustomerActivitiesAnswer extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 共答题 */
    private Integer total;
    /** 正确 */
    private Integer correct;
    /** 正确率 */
    private String rate;
    /** 获得积分 */
    private Integer point;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注(答题进度，存储的为前端答题序号) */
    private String remark;

}
