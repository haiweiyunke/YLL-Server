package yll.entity;

import lombok.Data;

/** 任务达人 */
@SuppressWarnings("serial")
@Data
public class TaskProducts extends BaseEntity {

    /** 任务id */
    private String taskId;
    /** 商品id */
    private String pid;
     /** 类型 */
    private String type;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
