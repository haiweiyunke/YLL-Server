package yll.service.model.vo;

import lombok.Data;
import yll.entity.Conferences;

/**
 * DH
 * 发布会
 */
@SuppressWarnings("serial")
@Data
public class ConferencesVo extends Conferences {

    //================返回参数======================
    /** 编码名称 */
    private String codename;

    /** 编码名称*/
    private String code;

    /** 发布会日期 字符串格式化*/
    private String issuedTimeStr;

    /** 活动是否结束，0-未结束，1-已结束 */
    private Integer ends;

    /** 截止时间显示类型， -1-活动已结束，0-小时，1-天 */
    private Integer endsType;

    /** 剩余截止时间数， -1-活动已结束，其余按 endsType 显示小时或天 */
    private Integer endsNum;

    //================返回参数======================
    /** 旧轮播id */
    private String oldSlideId;
    /** 旧推广id */
    private String oldExtensionId;

}
