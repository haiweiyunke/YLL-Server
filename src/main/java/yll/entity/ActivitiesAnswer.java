package yll.entity;

import lombok.Data;

/** 活动竞赛答案 */
@SuppressWarnings("serial")
@Data
public class ActivitiesAnswer extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 内容*/
    private String content;
    /** 正确答案（0-否，1-是；填空题默认为1） */
    private Integer answer;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
