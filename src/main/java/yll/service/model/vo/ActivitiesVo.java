package yll.service.model.vo;

import lombok.Data;
import yll.entity.Activities;

/**
 * 活动处理类
 */
@SuppressWarnings("serial")
@Data
public class ActivitiesVo extends Activities {

    //================返回参数======================
    /** 编码名称 */
    private String codename;

    /** 编码名称(父类编码) */
    private String code;

    /** 活动是否结束，0-未结束，1-已结束 */
    private Integer ends;

    /** 截止时间显示类型， -1-活动已结束，0-小时，1-天 */
    private Integer endsType;

    /** 剩余截止时间数， -1-活动已结束，其余按 endsType 显示小时或天 */
    private Integer endsNum;

    /** 起始时间 */
    private String appStartTime;

    /** 结束时间 */
    private String appEndTime;

    /** "我的活动"列表id */
    private String cusActivityId;

    //================查询条件======================
    /** 用户id */
    private String customerId;

}
