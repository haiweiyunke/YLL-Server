package yll.entity;

import lombok.Data;

/** 用户钱包 */
@SuppressWarnings("serial")
@Data
public class CustomerWallet extends BaseEntity {

    /** 用户id */
    private String targetId;
    /** 余额 */
    private Integer price;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
