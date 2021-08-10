package yll.app.controller;

import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yll.common.configuration.AuthConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 第三方登录(调试demo)
 * @author cc
 * @date Created in 2019-08-13 21:57
 */
@RestController
@RequestMapping("/oauth")
public class RestAuthController {

    // ==============================Fields===========================================
    @Autowired
    private AuthConfiguration auth;

    // ==============================Methods==========================================
    /**
     * 提供授权
     * @param source
     * @param response
     * @throws IOException
     */
    @RequestMapping("/render/{source}")
    public void renderAuth(@PathVariable("source") String source, HttpServletResponse response) throws IOException {
    }

    /**
     * oauth平台中配置的授权回调地址，（在对应的方法体中处理返回信息）
     * 以Demo为例，在创建github授权应用时的回调地址应为：http://127.0.0.1:8443/oauth/callback/github
     */
    @RequestMapping("/callback/{source}")
    public Object login(@PathVariable("source") String source) {
        //System.out.println("进入callback：" + source + " callback params：" + JSONObject.toJSONString(callback));
        return null;
    }

    /**
     * 根据具体的授权来源，获取授权请求工具类
     *
     * @param source
     * @return
     */
    private Object getAuthRequest(String source) {
        Object authRequest = null;
        switch (source) {
            case "alipay":
                // 支付宝在创建回调地址时，不允许使用localhost或者127.0.0.1，所以这儿的回调地址使用的局域网内的ip
               /* authRequest = new AuthAlipayRequest(AuthConfig.builder()
                        .clientId(auth.getClientIdAlipay())
                        .clientSecret(auth.getClientSecretAlipay())
                        .alipayPublicKey(auth.getPublicKeyAlipay())
                        .redirectUri(auth.getRedirectUriAlipay())
                        .build());*/
                break;
            case "wechat":
               /* authRequest = new AuthWeChatRequest(AuthConfig.builder()
                        .clientId(auth.getClientIdWechat())
                        .clientSecret(auth.getClientSecretWechat())
                        .redirectUri(auth.getRedirectUriWechat())
                        .build());*/
                break;
            default:
                break;
        }
        if (null == authRequest) {
            throw ExceptionHelper.prompt("未获取到有效的Auth配置");
        }
        return authRequest;
    }


    private void token() {
        System.out.println("进入测试");
    }


    //TODO 测试配置
    @RequestMapping("/test")
    public Result<?> test() {
        System.out.println("进入测试");
        return Result.ok(auth);
    }

}