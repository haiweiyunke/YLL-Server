package yll.component.store.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;

/**
 * 存储请求对象
 * @author cc
 */
@Data
@Accessors(chain = true)
public class StoreRequest {

    /**
     * 桶位名称
     */
    private String bucketName;

    /**
     * 桶位内部的文件路径key
     */
    private String pathKey;

    /**
     * 文件输入流
     */
    private InputStream inputStream;

    /**
     * 输入流的长度
     */
    private Long streamLength;

    /**
     * 文件名
     */
    private String fileName;

    //=================== COS ========================
    /**
     * 临时密钥 Id，可用于计算签名
     * COS
     */
    private String tmpSecretId;

    /**
     * 临时密钥 Key，可用于计算签名
     * COS
     */
    private String tmpSecretKey;

    /**
     * 请求时需要用的 token 字符串，最终请求 COS API 时，需要放在 Header 的 x-cos-security-token 字段
     * COS
     */
    private String sessionToken;

    /**
     * 密钥的起止时间，是 UNIX 时间戳
     * COS
     */
    private String startTime;

    /**
     * 密钥的失效时间，是 UNIX 时间戳
     * COS
     */
    private String expiredTime;

    //=================== 其它 ========================
    /**
     *  操作（PUT-上传，GET-下载）
     */
    private String requestType;

    /**
     * PUT-上传
     */
    String requestTypeGet = "GET";

    /**
     * GET-下载
     */
    String requestTypePut = "PUT";

}
