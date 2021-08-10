package yll.entity;

import lombok.Data;

/** 数据字典类别表 */
@SuppressWarnings("serial")
@Data
public class DicType extends BaseEntity {

    /** 标题 */
    private String code;
    /** 列表预览图片(","分割) */
    private String codename;
    /** 备注 */
    private String remark;
    /** 状态（0-删除，1-正常） */
    private Integer state;

}
