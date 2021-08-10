package yll.entity;

import lombok.Data;

/** 达人平台信息-自定义Json字段 */
@SuppressWarnings("serial")
@Data
public class PlatformJson extends BaseEntity {

    /**  键 */
    private String key;
    /** 值 */
    private String value;
     /** 类型 */
    private String type;
    /** 排序 */
    private Integer ordinal;
    /** 备注 */
    private String remark;

}
