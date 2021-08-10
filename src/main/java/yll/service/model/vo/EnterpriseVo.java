package yll.service.model.vo;

import lombok.Data;
import yll.entity.Enterprise;

/**
 * DH
 * 企业主处理类
 */
@SuppressWarnings("serial")
@Data
public class EnterpriseVo extends Enterprise {

    //================返回参数======================
    /** 成立时间 */
    private String establishTimeStr;
    /** 所属行业 */
    private String industryStr;
    /** 公司人数范围 */
    private String staffStr;

    /** 后台shop编辑页下拉菜单使用 */
    private String name;

}
