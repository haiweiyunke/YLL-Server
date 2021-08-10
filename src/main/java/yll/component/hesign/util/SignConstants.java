package yll.component.hesign.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * 和签配置
 */
public class SignConstants {

    public static String UPLOADURL = "https://test-api.hesigning.com";

//    public static String PREFIX_NOTIFYURL = "http://a598012777.6655.la";
    public static String PREFIX_NOTIFYURL = "http://152.136.194.24:8090/";
    /** H5签署成功和签回调服务器地址 */
    public static String NOTIFYURL = PREFIX_NOTIFYURL + "/app/process/sign";
    /** H5签署成功和签回调后服务器重定向地址 */
    public static String REDIRECT_URL = PREFIX_NOTIFYURL + "/contract-back.html";

    public static String APPSECRET = "$2y$13$XYVFlqXpgQ8ZA9bRbKHu4OngDUMyhat13tD2fAZU5jdPxpgnvjVUK";

    public static String OPENID = "a69e92dc774756fb11dc20e634b64aed32300726";

    /**  5.1 上传合同  */
    public static String UPLOADURL_CONTRACT_UPLOAD_51 = UPLOADURL + "/contract/upload";
    /**  5.2 根据模板创建合同  */
    public static String UPLOADURL_CONTRACT_FROM_TPL_52 = UPLOADURL + "/contract/from/tpl";
    /**  5.4 页面签署  */
    public static String UPLOADURL_CONTRACT_SIGN_H5_53 = UPLOADURL + "/contract/sign/h5";
    /**  5.5. 页面批量签署  */
    public static String UPLOADURL_PROJECT_SIGN_H5_55 = UPLOADURL + "/project/sign/h5";
    /**  5.6 页面签署状态查询  */
    public static String UPLOADURL_CONTRACT_STATUS_56 = UPLOADURL + "/contract/status";
    /**  5.7 页面查看合同  */
    public static String UPLOADURL_CONTRACT_VIEW_57 = UPLOADURL + "/contract/view";
    /**  5.8 获取项目全部合同的查看页面  */
    public static String UPLOADURL_PROJECT_VIEW_58 = UPLOADURL + "/project/view";
    /**  5.9.下载合同  */
    public static String UPLOADURL_CONTRACT_DOWNLOAD_59 = UPLOADURL + "/contract/download";

    /**  52的上传合同pdf  */
//    public static String UPLOAD_PDF = "E:/upload/yanyu_daihuo/fenContract.pdf";
    public static String UPLOAD_PDF = "C:/project/daihuo/new/contract/fenContract.pdf";
  @Value("${hesign.upload.pdf}")
    private void setUploadPdf(String uploadPdf){
        this.UPLOAD_PDF = uploadPdf + "/fenContract.pdf";
    }

    //=========================实体常量===========================
    public static String VERSION = "1";
    /** 0-表示非自动签，1-表示自动签署 */
    public static String AUTO = "0";
    /** 证件类型-营业执照 */
    public static String ID_CARD_TYPE_ENTERPRISE = "1";
    /** 证件类型-身份证 */
    public static String ID_CARD_TYPE_CELEBRITY = "2";
    /** 签署位置1 */
    public static String POSITION_ONE = "1,0.2,0.2";
    /** 签署位置2 */
    public static String POSITION_TWO = "1,0.2,0.4";
    /** 签署方式1 -短信 */
    public static String METHOD_SMS = "sms";
    /** 签署方式2 -手写 */
    public static String METHOD_HANDWRITE = "handwrite";
    /** 签署方式3 -短信+手写 */
    public static String METHOD_ALL = "sms,handwrite";

    /** 合同编号前缀 */
    public static String PREFIX_CONSTANTS = "XJX";


}
