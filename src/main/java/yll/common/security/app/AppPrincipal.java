package yll.common.security.app;

import java.io.Serializable;

/**
 *  APP使用,登录对象实体
 */
public class AppPrincipal implements Serializable {
    private String customerId;
    private String username;
    private String nickname;
    private String token;
    private String aliId;
    private String wechatId;
    private Integer code;               //短信验证码
    private String appFlag;        //app传来的验证票据，用于后续支付等校验

    public static final AppPrincipal NONE = new AppPrincipal();

    public AppPrincipal() {
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickame() { return this.nickname; }

    public void setNickname(String nickname) { this.nickname = nickname;  }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAliId() {
        return this.aliId;
    }

    public void setAliId(String aliId) {
        this.aliId = aliId;
    }

    public String getWechatId() {
        return this.wechatId;
    }

    public void setWechatId(String wechatId) {
        this.wechatId = wechatId;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAppFlag() {
        return this.appFlag;
    }

    public void setAppFlag(String appFlag) {
        this.appFlag = appFlag;
    }

    static {
        NONE.setCustomerId("");
        NONE.setUsername("");
        NONE.setNickname("");
        NONE.setToken("");
        NONE.setAliId("");
        NONE.setWechatId("");
        NONE.setCode(null);
        NONE.setAppFlag("");
    }
}
