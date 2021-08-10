package yll.common.security.app;

import lombok.Data;

import java.io.Serializable;

/**
 *  APP请求使用（登录信息）
 */
@Data
public class AppToken implements Serializable {

    private String username;

    private String password;

    private String token;

    private String aliId;
            ;
    private String wechatId;

    private Integer code;

    private String nickname;

    private String headImg;

    /** 电话 */
    private String phone;

    /** 登录用户端：1-APP，2-H5（OAuth2.0登录方式） */
    private Integer flag;

    /** 登录方式或操作标识： 1-验证码，2-密码，3-微信，4-支付宝，5-手机绑定*/
    private Integer type;

    /** 支付宝Code */
    private String aliCode;

}
