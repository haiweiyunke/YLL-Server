package yll.entity;

import lombok.Data;

/** 用户充值 */
@SuppressWarnings("serial")
@Data
public class CustomerRecharge extends BaseEntity {

    /** 用户id */
    private String targetId;
    /** 粉条充值表订单号 */
    private String orderNumber;
    /** 微信返回的预支付id */
    private String prepayId;
    /** 订单名称 */
    private String orderName;
    /** 加减符号(0-减，1-加) */
    private Integer signs;
    /** 交易金额 */
    private Integer price;
    /** 支付方式（字典表获取） */
    private String payType;
    /** 状态（0-删除，1-正常，21-待支付，22-已支付，23-取消支付） */
    private Integer state;
    /** 备注 */
    private String remark;

}
