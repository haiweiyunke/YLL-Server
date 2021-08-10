package yll.entity;

import lombok.Data;

/** 任务达人合同 */
@SuppressWarnings("serial")
@Data
public class TaskCelebrityContract extends BaseEntity {

    /** 任务id */
    private String taskId;
    /** 达人id */
    private String cid;
     /** 任务达人表id */
    private String tcId;
     /** 企业主|MCN id */
    private String eid;

     /** 类型 */
    private String type;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;


    /**  项目编号/合同编号  */
    private String projectSn;
    /** 项目名称 */
    private String projectName;
    /** 表单名即为 files[0][sn]  */
    private String fileSn;
    /** 企业主签约状态（1-未签约，2-已签约）*/
    private Integer enterpriseContract;
     /** 达人签约状态（1-未签约，2-已签约）*/
    private Integer celebrityContract;


}
