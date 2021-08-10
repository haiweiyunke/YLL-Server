package yll.entity;

import lombok.Data;

import java.util.Date;

/** 招聘信息 */
@SuppressWarnings("serial")
@Data
public class RecruitEnterpriseDetails extends BaseEntity {

    /** 企业主id */
    private String cid;
    /** 职位名称 */
    private String name;
    /** 省 */
    private String province;
    /** 市 */
    private String city;
    /** 最低学历（字典表获取） */
    private String education;
    /** 薪资范围 (字典表获取)*/
    private String salary;
    /** 岗位要求 */
    private String jobDescription;
    /** 任职要求 */
    private String recruitDescription;
    /** 招聘人数 */
    private String quantity;
    /** 工作经验(字典表获取) */
    private String workExperience;
    /** 截止时间 */
    private Date endTime;
    /** 工作性质（1-全职，2-兼职，3-实习） */
    private String workNature;
    /** 所属行业(字典表获取) */
    private String industry;

    /** 类型 */
    private String type;
    /** 备注 */
    private String remark;
    /** 状态（0-删除，1-正常，2-下架，3-已过期）*/
    private Integer state;

}
