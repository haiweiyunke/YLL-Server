package yll.entity;

import lombok.Data;

import java.util.Date;

/** 招聘公司 */
@SuppressWarnings("serial")
@Data
public class RecruitEnterprise extends BaseEntity {

    /** 企业主id */
    private String cid;
    /** 营业执照 */
    private String businessLicense;
    /** 社会统一信用代码 */
    private String creditCode;
    /** 企业名称 */
    private String name;
    /** 成立时间 */
    private Date establishTime;
    /** 注册资本 */
    private String registeredCapital;
    /** 法律代表人 */
    private String superintendent;
    /** 所属行业(字典表获取) */
    private String industry;
    /** 公司简介 */
    private String description;
    /** 公司人数范围(字典表获取) */
    private String staff;

    /** 类型 */
    private String type;
    /** 备注 */
    private String remark;
    /** 状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
