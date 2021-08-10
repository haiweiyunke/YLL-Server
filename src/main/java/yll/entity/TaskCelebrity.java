package yll.entity;

import lombok.Data;

/** 任务达人 */
@SuppressWarnings("serial")
@Data
public class TaskCelebrity extends BaseEntity {

    /** 任务id */
    private String taskId;
    /** 达人id */
    private String cid;
     /** 类型 */
    private String type;
    /** 当前任务状态 */
    private String currentState;
    /** 商品集合中文 */
    private String productList;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
