package yll.component.hesign.entity;

import lombok.Data;

/**
 * 和签授权实体
 */
@Data
public class HeSignBase {

    /** 签名 */
    private String sign;

    /**  openId  */
    private String openId;

    /**  加密参数 */
    private String nonce;

    /** 填1 */
    private String version;

    /**  项目编号/合同编号  */
    private String projectSn;

    /** 项目名称 */
    private String projectName;

    /** 后台回调 URL  */
    private String notifyUrl;

}
