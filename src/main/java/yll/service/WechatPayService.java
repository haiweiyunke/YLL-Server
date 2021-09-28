package yll.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.component.pay.parameter.StaticParameter;
import yll.component.pay.tools.PayRequestUtils;
import yll.component.pay.wechat.WxPayHttpClientFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付工具类
 */
@Slf4j
@Transactional
@Service
public class WechatPayService {

    @Autowired
    private WxPayHttpClientFactory wxPayHttpClientFactory;


    // ============================== 微信支付业务--APP ==========================================

    /**
     * 新建订单--APP下单
     * 重要参数：
     * • out_trade_no：商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     * • description：商品描述
     * • notify_url：支付回调通知URL，该地址必须为直接可访问的URL，不允许携带查询串
     * • total：订单总金额，单位为分
     */
    public String createOrderApp(String outTradeNo, Integer total, String description) throws Exception{
        //请求URL（）
        HttpPost httpPost = new HttpPost(StaticParameter.wechatUnifiedOrderUrl);

        //请求body参数
        String reqdata = "{"
        + "\"mchid\": \"" + StaticParameter.wechatMchId + "\","
        + "\"out_trade_no\": \"" + outTradeNo +"\","
        + "\"appid\": \"" + StaticParameter.wechatAppId + "\","
        + "\"description\": \"" + description + "\","
        +  "\"notify_url\": \"" + StaticParameter.wechatNotifyUrl + "\","
        + "\"amount\": "
        + "{"
        + "\"total\": " + total + ","
        + "\"currency\": \"CNY\""
        + "}"
        + "}";

        //请求头设置
        PayRequestUtils.setHeaders(httpPost);
        StringEntity entity = new StringEntity(reqdata,"utf-8");
        httpPost.setEntity(entity);
        System.out.println("============微信支付-App下单-请求参数：" +reqdata + "============");
        //从工厂获取微信请求Client
        CloseableHttpClient httpClient = wxPayHttpClientFactory.getHttpClient();
        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        String prepayId = "";   //预付款id
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) { //处理成功
                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
                //获取微信返回内容
                String result= EntityUtils.toString(response.getEntity(),"UTF-8");
                JSONObject jsonObject = JSON.parseObject(result);
                //获取预付款id
                prepayId = jsonObject.getString("prepay_id");
            } else if (statusCode == 204) { //处理成功，无返回Body
                System.out.println("success");
            } else {
                log.error("============微信支付-下单-失败！订单号为：" + outTradeNo + "；============" + "总金额：" + total + "============");
                log.error("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
        } finally {
            response.close();
            return prepayId;
        }
    }


    /**
     * 关闭订单
     * @param orderId 订单id
     * @return 成功[true]，失败[false]
     */
    public static boolean appCloseOrder(String orderId) {
       /** CloseableHttpClient httpClient = factory.getHttpClient();
        HttpPost post = new HttpPost(WechatConstants.getWxAppCloseOrderUrl(orderId));
        boolean result = false;
        //封装传递参数
        JSONObject requestJson = new JSONObject();
        requestJson.put("mchid", WechatConstants.WX_MERCHANT_ID);       //商户号
        StringEntity entity = new StringEntity(requestJson.toString(), "UTF-8");
        //设置请求参数和请求头
        entity.setContentType("application/json");
        entity.setContentEncoding("UTF-8");
        setHeaders(post);
        post.setEntity(entity);
        try {
            CloseableHttpResponse response = httpClient.execute(post);
            if (isSuccess(response)) {
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("微信支付-关闭下单-失败！");
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return false;
    }


    // ============================== 微信支付业务--小程序 ==========================================
    /**
     * 新建订单--小程序下单
     * 重要参数：
     * • out_trade_no：商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
     * • description：商品描述
     * • notify_url：支付回调通知URL，该地址必须为直接可访问的URL，不允许携带查询串
     * • total：订单总金额，单位为分
     * • openid：openid是微信用户在appid下的唯一用户标识（appid不同，则获取到的openid就不同），可用于永久标记一个用户。
     */
    public String createOrderMini(String outTradeNo, Integer total, String description, String openid) throws Exception{
        //请求URL
        HttpPost httpPost = new HttpPost(StaticParameter.wechatUnifiedMiniOrderUrl);

     // 请求body参数
        String reqdata = "{"
                + "\"amount\": {"
                + "\"total\": " + total + ","
                + "\"currency\": \"CNY\""
                + "},"
                + "\"mchid\": \"" + StaticParameter.wechatMchId + "\","
                + "\"description\": \"" + description + "\","
                +  "\"notify_url\": \"" + StaticParameter.wechatNotifyUrl + "\","
                + "\"payer\": {"
                + "\"openid\": \"" + openid + "\""
                + "},"
                + "\"out_trade_no\": \"" + outTradeNo +"\","
                + "\"appid\": \"" + StaticParameter.appIdMini + "\""
                + "}";
        //请求头设置
        PayRequestUtils.setHeaders(httpPost);
        StringEntity entity = new StringEntity(reqdata,"utf-8");
        httpPost.setEntity(entity);
        System.out.println("============微信支付-小程序下单-请求参数：" +reqdata + "============");

        //从工厂获取微信请求Client
        CloseableHttpClient httpClient = wxPayHttpClientFactory.getHttpClient();
        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpPost);

        String prepayId = "";   //预付款id
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                System.out.println("success,return body = " + EntityUtils.toString(response.getEntity()));
                //获取微信返回内容
                String result= EntityUtils.toString(response.getEntity(),"UTF-8");
                JSONObject jsonObject = JSON.parseObject(result);
                //获取预付款id
                prepayId = jsonObject.getString("prepay_id");

            } else if (statusCode == 204) {
                System.out.println("success");
                log.error("============微信支付-下单-失败！订单号为：" + outTradeNo + "；============" + "总金额：" + total + "============");
                log.error("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
            } else {
                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
        } finally {
            response.close();
            return prepayId;
        }
    }



    // ============================== 微信支付业务--辅助 ==========================================
    /**
     * 获取用户openid--小程序使用
     * 重要参数：
     * • appid：小程序 appId
     * • secret：小程序 appSecret
     * • js_code：登录时获取的 code
     * • grant_type：授权类型，此处只需填写 authorization_code  MINI_JSCODE
     */
    public Map<String, String> getWechatOpenid(String jsCode) throws Exception{
        //请求URL
        String code2SessionUrl = StaticParameter.code2SessionUrl;
        code2SessionUrl = code2SessionUrl.replace("MINI_JSCODE", jsCode);
        HttpGet httpGet = new HttpGet(code2SessionUrl);

        //请求头设置
        PayRequestUtils.setHeaders(httpGet);

        //从工厂获取微信请求Client
        CloseableHttpClient httpClient = wxPayHttpClientFactory.getHttpClient();
        //完成签名并执行请求
        CloseableHttpResponse response = httpClient.execute(httpGet);

        Map<String, String> resultMap = new HashMap();   //返回前端结果集
        try {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                //获取微信返回内容
                String result= EntityUtils.toString(response.getEntity(),"UTF-8");
//                System.out.println("获取openid，返回信息打印 = " + EntityUtils.toString(response.getEntity()));
                System.out.println("获取openid，返回信息打印 = " + result);
                JSONObject jsonObject = JSON.parseObject(result);
                //获取openid
                String openid = jsonObject.getString("openid");
                String errcode = jsonObject.getString("errcode");
                if(StringUtils.isBlank(openid) && !"0".equals(errcode)){
                    String errmsg = jsonObject.getString("errmsg");
                    //获取openid失败
                    resultMap.put("code", errcode);
                    resultMap.put("msg", errmsg);
                    resultMap.put("openid", "");
//                    resultMap.put("session_key", "");
                } else if(StringUtils.isNotBlank(openid)){
                    String session_key = jsonObject.getString("session_key");
                    resultMap.put("code", "0");
                    resultMap.put("msg", "success");
                    resultMap.put("openid", openid);
//                    resultMap.put("session_key", session_key);
                }
            } else {
                log.error("============微信小程序-获取openid-失败！返回信息为" + EntityUtils.toString(response.getEntity()));
                System.out.println("failed,resp code = " + statusCode+ ",return body = " + EntityUtils.toString(response.getEntity()));
                throw new IOException("request failed");
            }
        } finally {
            response.close();
            return resultMap;
        }
    }


}
