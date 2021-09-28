package yll.component.pay.vo;

import lombok.Data;

/**
 * 微信通知数据解密后对象
 * @author yll
 */
@Data
public class NotifyResourceVO {

    /**
     * 公众号ID
     */
    private String appid;

    /**
     * 直连商户号
     */
    private String mchid;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 微信支付订单号
     */
    private String transaction_id;

    /**
     * 交易类型
     */
    private String trade_type;

    /**
     * 交易状态
     */
    private String trade_state;

    /**
     * 交易状态描述
     */
    private String trade_state_desc;

    /**
     * 付款银行
     */
    private String bank_type;

    /**
     * 支付完成时间
     */
    private String success_time;

    /**
     * 支付者
     */
    private Payer payer;

    /**
     * 订单金额
     */
    private Amount amount;



    /**
     * 支付者
     */
    @Data
    public class Payer{
        /**
         * 用户标识
         */
        private String openid;

    }


    /**
     * 订单金额
     */
    @Data
    public class Amount{
        /**
         * 总金额
         */
        private Integer total;

        /**
         * 用户支付金额
         */
        private Integer payer_total;

        /**
         * 货币类型
         */
        private String currency;

        /**
         * 用户支付币种
         */
        private String payer_currency;

    }

}
