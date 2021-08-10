package yll.entity;

import lombok.Data;

/** 平台自定义属性-键表 */
@SuppressWarnings("serial")
@Data
public class PlatformAttributeKey extends BaseEntity {

    /** 平台类型 */
    private String type;

    /** 名称 */
    private String name;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
