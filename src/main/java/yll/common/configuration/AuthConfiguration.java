package yll.common.configuration;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yll.common.security.AuthRealm;

import java.io.Serializable;

/**
 * 第三方平台（配置）
 */
@SuppressWarnings("serial")

@Configuration
@Data
public class AuthConfiguration implements Serializable {

    //===================微信=========================

    //===================支付宝=========================
    /** 应用id-支付宝 */
    @Value("${alipay.appid}")
    private String appidAlipay;
    /** 秘钥--支付宝 */
    @Value("${alipay.privateKey}")
    private String privateKeyAlipay;
    /** 公钥--支付宝 */
    @Value("${alipay.publicKey}")
    private String publicKeyAlipay;
    /** 请求地址--支付宝 */
    @Value("${alipay.serverUrl}")
    private String serverUrlAlipay;
    /** 回调地址--支付宝 */
    @Value("${alipay.redirectUri}")
    private String redirectUrlAlipay;

    @Bean
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(serverUrlAlipay, appidAlipay, privateKeyAlipay, "json", "UTF-8", publicKeyAlipay, "RSA2");
    }

}
