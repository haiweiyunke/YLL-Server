package yll.entity;

import lombok.Data;

/** 钱包订单流水 */
@SuppressWarnings("serial")
@Data
public class CustomerWalletDetails extends BaseEntity {

    /** 用户id */
    private String targetId;
    /** 订单号(充值单号前缀为CZ） */
    private String orderNumber;
    /** 订单名称（课件充值后的标识，可存于此字段，值为“课件充值”） */
    private String orderName;
    /** 订单类型（字典表获取。订单来源） */
    private String orderType;
    /** 加减符号(0-减，1-加) */
    private Integer signs;
    /** 交易金额 */
    private Integer price;
    /** 支付方式（字典表获取） */
    private String payType;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
