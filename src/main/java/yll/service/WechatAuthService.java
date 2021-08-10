package yll.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.component.app.sms.HttpClientUtil;

import java.io.IOException;

/**
 * 第三方登录-微信  TODO 搞一个开放平台OAuth2.0的登录
 */
@Transactional
@Service
public class WechatAuthService {

    // ==============================Fields===========================================
    private String appid;

    private String appSecret;

    private String tokenUrl;

    // ==============================Constructor==========================================
    public WechatAuthService(@Value("${wechat.appid}") String appid, @Value("${wechat.appSecret}") String appSecret, @Value("${wechat.tokenUrl}") String tokenUrl){
        this.appid = appid;
        this.appSecret = appSecret;
        this.tokenUrl = tokenUrl.replace("APPID", appid).replace("SECRET", appSecret);
    }

    // ==============================Methods==========================================
    /**
     * 获取Token（需要用code获取，oauth2方式，App登录使用）
     * @return
     * @throws IOException
     */
    public Object getTokenAppByOauth2() throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String id = "wx5087d28cdae9e623";
        String secret = "1ea9a271148387a90e859450c23f8215";
        url = url.replace("APPID", id).replace("SECRET", secret).replace("code","");
        HttpClientUtil httpClient = HttpClientUtil.getInstance("UTF-8");
        String result = httpClient.getResponseBodyAsString(url, "");
        System.out.println(result);
        /*JSONObject jsonResult = new JSONObject(result);
        JSONObject data = jsonResult.optJSONObject("Response");
        if (data == null) {
            data = jsonResult;
        }*/


        return  null;
    }

    /**
     * 获取微信code地址
     * @return
     */
    protected String codeUrl() {

        return  null;
    }

    public static void main(String[] args) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String id = "wx5087d28cdae9e623";
        String secret = "1ea9a271148387a90e859450c23f8215";
        url.replace("APPID", id).replace("SECRET", secret).replace("code","");
        HttpClientUtil httpClient = HttpClientUtil.getInstance("UTF-8");
        String result = httpClient.getResponseBodyAsString(url, "");
        JSONObject jsonResult = new JSONObject(result);
//        JSONObject data = jsonResult.getJSONObject("errcode");
//        if (data == null) {
//            System.out.println(data);
//        }

        System.out.println(jsonResult);
    }


}
