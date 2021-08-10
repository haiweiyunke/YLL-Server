package yll.service.model.vo;

import lombok.Data;
import yll.entity.RecruitCelebrityApply;


/**
 * 招聘达人申请处理类
 */
@SuppressWarnings("serial")
@Data
public class RecruitCelebrityApplyVo extends RecruitCelebrityApply {

    //================返回参数======================
    /** 申请人姓名 */
    private String name;
    /** 性别（1-女，2-男） */
    private String gender;
    /** 年龄 */
    private String age;
    /** 照片 */
    private String image;
    /** 简历id */
    private String rcid;

}
