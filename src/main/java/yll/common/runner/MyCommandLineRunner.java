package yll.common.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import yll.common.factory.YamlConfigFactory;
import yll.component.pay.parameter.StaticParameter;
import yll.component.pay.tools.PayRequestUtils;
import yll.component.pay.tools.PayResponseUtils;

import java.io.FileInputStream;
import java.security.cert.X509Certificate;

/**
 * 启动项目时加载自定义配置
 * @author yll
 */
@Slf4j
@Order(0)
@Component
@PropertySource(value = {"classpath:application-tencent.yml"},factory = YamlConfigFactory.class)
public class MyCommandLineRunner implements CommandLineRunner {


    // ============================== 微信支付 ==========================================
    /**
     * 微信商户号
     */
    @Value("${wechat.pay.mchId}")
    private String wechatMchId;

    /**
     * 商户在微信公众平台申请服务号对应的APPID
     */
    @Value("${wechat.pay.appId}")
    private String wechatAppId;

    /**
     * 商户平台设置的密钥key
     */
    @Value("${wechat.pay.key}")
    private String wechatKey;

    /**
     * 回调报文解密V3密钥key
     */
    @Value("${wechat.pay.v3Key}")
    private String wechatV3Key;

    /**
     * 微信证书路径
     */
    @Value("${wechat.pay.certificatePath}")
    private String wechatCertificatePath;

    /**
     * 微信密钥路径
     */
    @Value("${wechat.pay.keyPath}")
    private String wechatKeyPath;

    /**
     * 微信获取平台证书列表地址
     */
    @Value("${wechat.pay.certificatesUrl}")
    private String wechatCertificatesUrl;

    /**
     * 微信APP下单URL
     */
    @Value("${wechat.pay.unifiedOrderUrl}")
    private String wechatUnifiedOrderUrl;

    /**
     * 微信APP查询订单URL
     */
    @Value("${wechat.pay.queryOrderUrl}")
    private String wechatQueryOrderUrl;

    /**
     * 异步接收微信支付结果通知的回调地址
     */
    @Value("${wechat.pay.notifyUrl}")
    private String wechatNotifyUrl;


    // ==============微信小程序===============
    /**
     * 小程序对应的APPID（用于获取openid）
     */
    @Value("${wechat.pay.appIdMini}")
    private String appIdMini;

    /**
     * 小程序 appSecret
     */
    @Value("${wechat.pay.appSecretMini}")
    private String appSecretMini;

    /**
     * 小程序用于获取openid的URL
     */
    @Value("${wechat.pay.code2SessionUrl}")
    private String code2SessionUrl;

    /**
     * 微信小程序下单URL
     */
    @Value("${wechat.pay.wechatUnifiedMiniOrderUrl}")
    private String wechatUnifiedMiniOrderUrl;



    /**
     * 初始化加载
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        //微信支付
        StaticParameter.wechatMchId = wechatMchId;
        StaticParameter.wechatAppId = wechatAppId;
        StaticParameter.wechatKey = wechatKey;
        StaticParameter.wechatV3Key = wechatV3Key;
        StaticParameter.wechatCertificatesUrl = wechatCertificatesUrl;
        StaticParameter.wechatUnifiedOrderUrl = wechatUnifiedOrderUrl;
        StaticParameter.wechatQueryOrderUrl = wechatQueryOrderUrl;
        StaticParameter.wechatNotifyUrl = wechatNotifyUrl;

        StaticParameter.appIdMini = appIdMini;
        StaticParameter.appSecretMini = appSecretMini;
        StaticParameter.code2SessionUrl = code2SessionUrl;
        StaticParameter.wechatUnifiedMiniOrderUrl = wechatUnifiedMiniOrderUrl;

        X509Certificate certificate = PayRequestUtils.getCertificate(new FileInputStream(wechatCertificatePath));
        StaticParameter.privateKey = PayRequestUtils.getPrivateKey(wechatKeyPath);
        StaticParameter.serialNo = certificate.getSerialNumber().toString(16).toUpperCase();
        StaticParameter.certificateMap = PayResponseUtils.refreshCertificate();
    }



}
