package yll.service.model.vo;

import lombok.Data;
import yll.entity.RecruitCelebrity;


/**
 * 招聘达人简历处理类
 */
@SuppressWarnings("serial")
@Data
public class RecruitCelebrityVo extends RecruitCelebrity {

    //================返回参数======================
    /** 民族 */
    private String nationStr;
    /** 省 */
    private String provinceStr;
    /** 市 */
    private String cityStr;
    /** 最高学历 */
    private String educationStr;

}
