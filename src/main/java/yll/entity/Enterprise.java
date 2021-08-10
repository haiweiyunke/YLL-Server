package yll.entity;

import lombok.Data;

import java.util.Date;

/** 企业主 */
@SuppressWarnings("serial")
@Data
public class Enterprise extends BaseEntity {

    /** 企业主名称*/
    private String enterpriseName;
    /** 负责人 */
    private String superintendent;
    /** 联系方式 */
    private String phone;
    /** 社会统一信用代码 */
    private String creditCode;
    /** 企业简介 */
    private String description;
    /** 营业执照 */
    private String businessLicense;
    /** 成立时间 */
    private Date establishTime;
    /** 注册资本 */
    private String registeredCapital;
    /** 法人 */
    private String legalPerson;
    /** 所属行业(字典表获取) */
    private String industry;
    /** 公司人数范围(字典表获取) */
    private String staff;
    /** 公司logo */
    private String logo;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-待审核，2-已审核）*/
    private Integer state;

}
