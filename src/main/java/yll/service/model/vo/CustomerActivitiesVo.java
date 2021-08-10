package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerActivities;

/**
 * 我的活动处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerActivitiesVo extends CustomerActivities {

    //================返回参数======================
    /** 活动名称 */
    private String name;

    /** 活动封面 */
    private String cover;

    /** 活动图片 */
    private String image;

    /** 活动类型编码 */
    private String type;

    /** 活动开始时间 */
    private String appStartTime;

    /** 活动结束时间 */
    private String appEndTime;

    /** 活动类型名称 */
    private String codename;

}
