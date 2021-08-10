package yll.entity;

import lombok.Data;

import java.util.Date;

/** 机构（MCN） */
@SuppressWarnings("serial")
@Data
public class Mcn extends BaseEntity {

    /** mcn名称*/
    private String mcnName;
    /** logo */
    private String logo;
    /** 负责人 */
    private String superintendent;
    /** 联系方式 */
    private String phone;
    /** 网红人数 */
    private String celebrityNumber;
    /** 公司简介 */
    private String description;
    /** 认证证书链接 */
    private String authenticateLink;
    /** 机构查询链接 */
    private String queryLink;
    /** 公司形象，英文逗号分隔 */
    private String corporateImage;
    /** 营业执照 */
    private String businessLicense;

    /** 成立时间 */
    private Date establishTime;
    /** 注册资本 */
    private String registeredCapital;
    /** 法人 */
    private String legalPerson;
    /** 所在地（省市县英文逗号分隔） */
    private String location;
    /** 所属行业(字典表获取) */
    private String industry;
    /** 社会统一信用代码 */
    private String creditCode;


    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-待审核，2-已审核）*/
    private Integer state;


}
