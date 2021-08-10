package yll.service.model.vo;

import lombok.Data;
import yll.entity.InternetCelebrity;
import yll.entity.Mcn;

/**
 * DH
 * 机构（MCN）
 */
@SuppressWarnings("serial")
@Data
public class McnVo extends Mcn {

    //================返回参数======================
    /** 网红(公司)人数 */
    private String celebrityNumStr;
    /** 成立时间 */
    private String establishTimeStr;
    /** 所属行业 */
    private String industryStr;
    /** 公司人数范围 */
    private String staffStr;

    /** 名称。（为了接口中方便app端统一取值显示） */
    private String enterpriseName;


}
