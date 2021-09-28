package yll.component.pay.wechat;



import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import yll.component.pay.parameter.StaticParameter;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;


/**
 *  微信支付工厂类--（微信请求使用此类）
 *  微信整个支付流程统一由微信管理
 *  我们只需要配置好httpClient，每次获取Client发起对应的请求就好了
 */
@Slf4j
@Component
public class WxPayHttpClientFactory {


    //商户id
    private String merchantId;
    //商户证书序列号
    private String merchantSerialNo;
    //V3秘钥
    private String apiV3Key;

    public WxPayHttpClientFactory() {
        initFactory(StaticParameter.wechatMchId, StaticParameter.serialNo, StaticParameter.wechatV3Key);
    }

    /**
     * 初始化工厂类参数
     *
     * @param merchantId       商户id
     * @param merchantSerialNo 商户证书序列号
     * @param apiV3Key         V3秘钥
     */
    private void initFactory(String merchantId, String merchantSerialNo, String apiV3Key) {
        this.merchantId = StaticParameter.wechatMchId;
        this.merchantSerialNo = StaticParameter.serialNo;
        this.apiV3Key = StaticParameter.wechatV3Key;
    }


    /**
     * 获取微信支付httpClient
     *
     * @return httpClient
     */
    public CloseableHttpClient getHttpClient() {
        //若工厂属性为空，则重新加载属性
        if(null == merchantId || null == merchantSerialNo || null == apiV3Key){
            initFactory(StaticParameter.wechatMchId, StaticParameter.serialNo, StaticParameter.wechatV3Key);
        }
        return initHttpClient();
    }

    /**
     * 初始化客户端
     *
     * @return httpClient
     */
    public CloseableHttpClient initHttpClient() {
        CloseableHttpClient httpClient = null;
        try {
            // 加载商户私钥（privateKey：私钥字符串）
//            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new FileInputStream(StaticParameter.privateKeyPath));   //
            PrivateKey merchantPrivateKey = StaticParameter.privateKey;     //自定义版--封装PrivateKey返回
            // 加载平台证书（merchantId：商户号,merchantSerialNo：商户证书序列号,apiV3Key：V3秘钥）
            AutoUpdateCertificatesVerifier verifier = new AutoUpdateCertificatesVerifier(
                    new WechatPay2Credentials(merchantId, new PrivateKeySigner(merchantSerialNo, merchantPrivateKey)), apiV3Key.getBytes("utf-8"));
            //httpClient构造器，可以继续通过builder构造其他参数
            WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                    .withMerchant(merchantId, merchantSerialNo, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier));
//                    .withValidator(response -> true);
            // 初始化httpClient
            httpClient = builder.build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("初始化微信支付httpClient失败！");
        }
        /*  采用“原版--文件所在地址” 模式获取“PrivateKey”时，才会抛出此错误
        catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("加载秘钥文件失败！请检查秘钥文件是否存在！");
        }*/
        return httpClient;
    }

}
