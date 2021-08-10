package yll.entity;

import lombok.Data;

/** 积分明细 */
@SuppressWarnings("serial")
@Data
public class CustomerPointsDetails extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 类型(字典表获取。积分获取来源) */
    private String type;
    /** 加减符号(0-减，1-加) */
    private Integer signs;
    /** 积分 */
    private Integer point;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
