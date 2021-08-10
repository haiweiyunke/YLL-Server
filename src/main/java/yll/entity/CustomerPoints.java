package yll.entity;

import lombok.Data;

/** 积分 */
@SuppressWarnings("serial")
@Data
public class CustomerPoints extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 总积分 */
    private Integer point;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
