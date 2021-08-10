package yll.entity;

import lombok.Data;

/** 活动竞赛题目 */
@SuppressWarnings("serial")
@Data
public class ActivitiesQuestions extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 题目*/
    private String content;
    /** 类型(字典表获取。选择、判断、填空) */
    private String type;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
