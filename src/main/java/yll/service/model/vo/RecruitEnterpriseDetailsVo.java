package yll.service.model.vo;

import lombok.Data;
import yll.entity.RecruitEnterpriseDetails;


/**
 * 招聘信息处理类
 */
@SuppressWarnings("serial")
@Data
public class RecruitEnterpriseDetailsVo extends RecruitEnterpriseDetails {

    //================查询参数======================
    /** 排序条件 */
    private String orderBy;
    /** 薪酬，1-正序，2-倒叙。 */
    private Integer salaryOrder;
    /** 发布时间，1-正序，2-倒叙。 */
    private Integer createdTimeOrder;
    /** 要显示的state数据，1-其他看(只展示state=1的)，2-企业主看(展示state=1,2,3的)。 */
    private Integer stateFlag;



    //================返回参数======================
    /** 截止时间 (格式化) */
    private String endTimeStr;
    /** 省 */
    private String provinceStr;
    /** 市 */
    private String cityStr;
    /** 公司人数范围 */
    private String staffStr;
    /** 企业名称 */
    private String enterpriseName;
    /** mcn名称 */
    private String mcnName;
    /** 最低学历 */
    private String educationStr;
    /** 工作经验 */
    private String workExperienceStr;
    /** 所属行业 */
    private String industryStr;
    /** 薪资范围 */
    private String salaryStr;
    /** 职位名称中文 */
    private String nameStr;
    /** 工作经验中文 */
    private String experienceStr;

    /** 招聘信息id */
    private String redid;

}
