package yll.entity;

import lombok.Data;

/** 招聘达人简历 */
@SuppressWarnings("serial")
@Data
public class RecruitCelebrity extends BaseEntity {

    /** 达人id */
    private String cid;
    /** 姓名 */
    private String name;
    /** 照片(","分割) */
    private String image;
    /** 年龄 */
    private String age;
    /** 性别（1-女，2-男） */
    private String gender;
    /** 民族（字典表获取） */
    private String nation;
    /** 省 */
    private String province;
    /** 市 */
    private String city;
    /** 最高学历（字典表获取） */
    private String education;
    /** 邮箱 */
    private String email;
    /** 个人简介 */
    private String description;
    /** 工作经验 */
    private String experience;
    /** 兴趣特长 */
    private String interest;
    /** 工作性质（1-全职，2-兼职，3-实习） */
    private String workNature;
    /** 手机号 */
    private String phone;


    /** 类型 */
    private String type;
    /** 备注 */
    private String remark;
    /** 状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
