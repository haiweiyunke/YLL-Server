package yll.component.pay.tools;

import org.springframework.util.Base64Utils;
import yll.component.pay.exception.RequestWechatException;
import yll.component.pay.parameter.StaticParameter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 微信支付回调工具类
 */
public class PayNotifyUtils {

    /**
     * 验证微信签名
     * @param request
     * @param body
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ParseException
     */
    public static boolean verifiedSign(HttpServletRequest request, String body) throws GeneralSecurityException, ParseException, RequestWechatException {
        //微信返回的证书序列号
        String serialNo = request.getHeader("Wechatpay-Serial");
        //微信返回的随机字符串
        String nonceStr = request.getHeader("Wechatpay-Nonce");
        //微信返回的时间戳
        String timestamp = request.getHeader("Wechatpay-Timestamp");
        //微信返回的签名
        String wechatSign = request.getHeader("Wechatpay-Signature");
        //组装签名字符串
        String signStr = Stream.of(timestamp, nonceStr, body)
                .collect(Collectors.joining("\n", "", "\n"));
        //当证书容器为空 或者 响应提供的证书序列号不在容器中时  就应该刷新了
        if (StaticParameter.certificateMap.isEmpty() || !StaticParameter.certificateMap.containsKey(serialNo)) {
            StaticParameter.certificateMap=PayResponseUtils.refreshCertificate();
        }
        //根据序列号获取平台证书
        X509Certificate certificate = StaticParameter.certificateMap.get(serialNo);
        //获取失败 验证失败
        if (certificate == null){
            return false;
        }
        //SHA256withRSA签名
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(certificate);
        signature.update(signStr.getBytes());
        //返回验签结果
        return signature.verify(Base64Utils.decodeFromString(wechatSign));
    }


    /**
     * 获取请求文体
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        ServletInputStream stream = null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            stream = request.getInputStream();
            // 获取响应
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new IOException("读取返回支付接口数据流出现异常！");
        } finally {
            reader.close();
        }
        return sb.toString();
    }

}
