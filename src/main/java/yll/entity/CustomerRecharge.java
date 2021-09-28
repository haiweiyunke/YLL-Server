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
    /** 订单名称 */
    private String orderName;
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
