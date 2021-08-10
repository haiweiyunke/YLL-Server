package yll.component.hesign.entity;

import lombok.Data;

/**
 * 和签授权实体
 */
@Data
public class HeSignAuth {

    /** 当前签章位置 */
    private String position;

    /** 证件类型:1 营业执照 2 身份证 */
    private String idCardType;

    /** 证件号码，如果是企业，此处使用社会统一信用代码 */
    private String idCardNo;

    /** 手机号，可加国家区号 */
    private String mobile;

    /** 姓名或公司全称，若未实名认证，将不会颁发有效的 CA 证书 */
    private String name;

    /** 0 表示非自动签，1 表示自动签署。demo里为1 */
    private String auto;

    /** 自动签证据， */
    private String reason;

    /** 签字方式。sms-短信，handwrite-手写，sms,handwrite-短信+手写 */
    private String method;

    /** 回调自用判断操作人角色 */
    private String type;

}
