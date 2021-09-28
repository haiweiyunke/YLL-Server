package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerWalletDetails;

/**
 * 钱包订单流水处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerWalletDetailsVo extends CustomerWalletDetails {

    //================入参======================
    /** 微信用户openid，用于小程序支付的下单 */
    private String openid;
    /** 微信下单类型，（小程序--wechatApp，小程序--wechatMini） */
    private String wechatPayType;


    //================返回参数======================
    /** 用户名 */
    private String username;

    /** 金额和 */
    private String total;

    /** 完成次数 */
    private Integer num;

}
