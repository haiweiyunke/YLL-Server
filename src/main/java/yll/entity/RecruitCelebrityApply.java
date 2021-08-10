package yll.entity;

import lombok.Data;

/** 招聘达人申请 */
@SuppressWarnings("serial")
@Data
public class RecruitCelebrityApply extends BaseEntity {

    /** 达人id */
    private String cid;
    /** 招聘信息id */
    private String redid;

    /** 类型 */
    private String type;
    /** 备注 */
    private String remark;
    /** 状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
