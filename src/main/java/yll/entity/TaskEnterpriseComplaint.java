package yll.entity;

import lombok.Data;

import java.util.Date;

/** 任务企业主申诉 */
@SuppressWarnings("serial")
@Data
public class TaskEnterpriseComplaint extends BaseEntity {

    /** 任务表id */
    private String tid;
    /** 任务达人表id */
    private String tcId;
    /** 申诉原因 */
    private String reason;
    /** 上传凭证(","分割) */
    private String image;

    /** 备注 */
    private String remark;
    /** 状态（1-正常，2-备用）*/
    private Integer state;

}
