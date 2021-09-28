package yll.component.pay.vo;

import lombok.Data;

/**
 * 接收微信支付通知VO
 * @author yll
 */
@Data
public class PayNotifyVO {
    /**
     * 通知的唯一ID
     */
    private String id;

    /**
     * 通知创建时间
     */
    private String create_time;

    /**
     * 通知类型 支付成功通知的类型为TRANSACTION.SUCCESS
     */
    private String event_type;

    /**
     * 通知数据类型 支付成功通知为encrypt-resource
     */
    private String resource_type;

    /**
     * 通知资源数据
     */
    private Resource resource;

    /**
     * 回调摘要
     */
    private String summary;

    /**
     * 通知资源数据
     */
    @Data
    public class Resource{
        /**
         * 加密算法类型
         */
        private String algorithm;

        /**
         * 数据密文
         */
        private String ciphertext;

        /**
         * 附加数据
         */
        private String associated_data;

        /**
         * 随机串
         */
        private String nonce;

    }

}
