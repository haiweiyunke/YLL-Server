package yll.component.hesign.entity;

import lombok.Data;

/**
 * 和签响应结果
 */
@Data
public class HeSignResult {

    /** 名称 */
    private String name;
    /** 英文信息 */
    private String message;
   /** 编码 */
    private String code;
    /** 错误类型 */
    private String type;
    /** 成功标识，0-不成功 或 1-成功*/
    private String success;
    /** 错误码1 */
    private String errno;
    /** 错误值1 */
    private String error;
    /** 错误码2 */
    private String errno2;
    /** 错误值2 */
    private String error2;
    /** 软件环境 */
    private String env;
    /** 是否调试模式 */
    private String isDebugging;
    /** 服务器 */
    private String server;

    //======================signH5===========================
    /** 签名地址 */
    private String  url;
    /** 签名openId */
    private String  signerOpenId;

}
