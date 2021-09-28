package yll.component.pay.vo;

import lombok.Data;
import yll.component.pay.parameter.StaticParameter;

/**
 * JSAPI/小程序下单APIVO
 * @author yll
 */
@Data
public class TransactionsVO {
    /**
     * 公众号ID
     */
    private String appid;

    /**
     * 直连商户号
     */
    private String mchid;

    /**
     * 商品描述
     */
    private String description;

    /**
     * 商户订单号
     */
    private String out_trade_no;

    /**
     * 通知地址
     */
    private String notify_url;

    /**
     * 订单金额
     */
    private Amount amount;

    /**
     * 支付者
     */
    private Payer payer;

    /**
     * 订单金额
     */
    @Data
    class Amount {
        /**
         * 总金额
         */
        // @ApiModelProperty("总金额")
        private Integer total;

    }

    /**
     * 支付者
     */
    @Data
    class Payer{
        /**
         * 用户标识
         */
        // @ApiModelProperty("用户标识")
        private String openid;

    }


    public TransactionsVO(String description, String out_trade_no, String notify_url,
                          Integer total, String openid){
        this.appid = StaticParameter.wechatAppId;
        this.mchid = StaticParameter.wechatMchId;
        this.description = description;
        this.out_trade_no = out_trade_no;
        this.notify_url = notify_url;
        Amount amount = new Amount();
        amount.setTotal(total);
        this.amount = amount;
        Payer payer = new Payer();
        payer.setOpenid(openid);
        this.payer = payer;
    }
}
