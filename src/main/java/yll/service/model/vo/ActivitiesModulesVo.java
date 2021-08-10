package yll.service.model.vo;

import lombok.Data;
import yll.entity.ActivitiesModules;

/**
 * 活动详情模块处理类
 */
@SuppressWarnings("serial")
@Data
public class ActivitiesModulesVo extends ActivitiesModules {

    //================返回参数======================
    /** 编码名称 */
    private String codename;

    /** 活动名称 */
    private String activityName;

    /** 图片 */
    private String image;
    //================查询条件======================
    /** 活动ID */
    private String activityId;
}
