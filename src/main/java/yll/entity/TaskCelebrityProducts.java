package yll.entity;

import lombok.Data;

/** 任务达人商品 */
@SuppressWarnings("serial")
@Data
public class TaskCelebrityProducts extends BaseEntity {

    /** 任务id */
    private String taskId;
    /** 达人id */
    private String cid;
     /** 任务达人表id */
    private String tcId;
     /** 商品id */
    private String pid;
     /** 类型 */
    private String type;
    /** 签收状态（0-未发货，1-运送中，2-已签收） */
    private Integer receive;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
