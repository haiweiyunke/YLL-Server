package yll.service.model.vo;

import lombok.Data;
import yll.entity.Enterprise;
import yll.entity.PlatformAttributeValue;

import java.util.Date;

/**
 * DH
 * 平台自定义属性-值表
 */
@SuppressWarnings("serial")
@Data
public class PlatformAttributeValueVo extends PlatformAttributeValue {

    //================返回参数======================
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date endTime;

    /** 键名 */
    private String kname;
    /** app使用日期 */
    private String appCreatedTime;

    /** 1-求和，2-平均值 */
    private String flag;

    /** 用户名 */
    private String username;
}
