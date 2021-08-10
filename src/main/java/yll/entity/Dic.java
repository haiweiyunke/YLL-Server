package yll.entity;

import lombok.Data;

/** 数据字典表 */
@SuppressWarnings("serial")
@Data
public class Dic extends BaseEntity {

    /** 类别表id */
    private String targetId;
    /** 编码 */
    private String code;
    /** 列表预览图片(","分割) */
    private String codename;
    /** 备注 */
    private String remark;
    /** 备用备注 */
    private String remarks;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 排序 */
    private Integer ordinal;

}
