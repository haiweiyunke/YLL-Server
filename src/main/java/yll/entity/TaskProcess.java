package yll.entity;

import lombok.Data;

/** 任务达人订单流程 */
@SuppressWarnings("serial")
@Data
public class TaskProcess extends BaseEntity {

    /** 任务表id */
    private String tid;
    /** 任务达人表id */
    private String tcId;
    /** 任务流程（字典表获取） */
    private String process;
     /** 类型 */
    private String type;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
