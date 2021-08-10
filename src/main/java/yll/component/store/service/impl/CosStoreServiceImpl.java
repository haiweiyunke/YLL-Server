package yll.component.store.service.impl;

import com.github.relucent.base.util.model.Result;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.internal.CosServiceRequest;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.tencent.cloud.CosStsClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import yll.component.store.config.StoreConfigProperties;
import yll.component.store.convert.IConvert;
import yll.component.store.domain.StoreRequest;
import yll.component.store.service.IStoreService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 腾讯cos存储服务
 * @author cc
 */
@Slf4j
@Service("storeService")
@ConditionalOnProperty(name = "store.platform", havingValue = "cos")
public class CosStoreServiceImpl implements IStoreService {

    /** cos客户端 */
    private static COSClient cosClient;
    /** cos配置 */
    private static TreeMap<String, Object> config;
    /** 临时密钥-有效时间（最大值7200） */
    protected static Integer durationSeconds = 7200;
    /** 临时密钥-资源的前缀 */
    @Value("${store.temp-path:yshd/prod/*}")
    protected String allowPrefix;
    /** 临时密钥-权限集合 */
    protected static String[] allowActions = new String[] {
            // 简单上传
            "name/cos:PutObject",
            // 表单上传
            "name/cos:PostObject",
            // 分片上传： 初始化分片
            "name/cos:InitiateMultipartUpload",
            // 分片上传： 查询 bucket 中未完成分片上传的UploadId
            "name/cos:ListMultipartUploads",
            // 分片上传： 查询已上传的分片
            "name/cos:ListParts",
            // 分片上传： 上传分片块
            "name/cos:UploadPart",
            // 分片上传： 完成分片上传
            "name/cos:CompleteMultipartUpload"
    };

    @Autowired
    private StoreConfigProperties storeConfigProperties;

    @Autowired
    private IConvert<CosServiceRequest> cosConvertImpl;


    @PostConstruct
    @Override
    public void init() {

        //初始化参数
        config = new TreeMap<>();
        config.put("secretId", storeConfigProperties.getCredential().getSecrectId());
        config.put("secretKey", storeConfigProperties.getCredential().getSecrectKey());
        config.put("durationSeconds", durationSeconds);
        config.put("bucket", storeConfigProperties.getDefaultBucket());
        config.put("region", storeConfigProperties.getRegion());
        config.put("allowPrefix", allowPrefix);
        config.put("allowActions", allowActions);
        //config.put("policy", storeConfigProperties.getCredential().getSecrectId());

        //=============================== 永久密钥 ===================================
        //设置认证信息()
        //COSCredentials cred = new BasicCOSCredentials(config.get("secretId").toString(), config.get("secretKey").toString());

        //设置bucket的区域
       //ClientConfig clientConfig = new ClientConfig(new Region(storeConfigProperties.getRegion()));

        //生成cos客户端
        //cosClient = new COSClient(cred, clientConfig);

    }

    @PreDestroy
    @Override
    public void destroy() {
        cosClient.shutdown();
    }

    //========================== 永久密钥 ================================
    /**
     *  永久密钥创建存储桶
     * @param bucketName
     * @return
     * @throws Exception
     */
    @Override
    public Result<?> createBucket(String bucketName) throws Exception {
        log.info("create bucket {}", bucketName);
        if (StringUtils.isBlank(bucketName)) {
            return Result.error("桶位名称不能为空");
        }
        boolean result = cosClient.doesBucketExist(bucketName);
        if (result) {
            return Result.error("桶位已经存在");
        } else {
            Bucket bucket = cosClient.createBucket(bucketName);
            if (bucket != null) {
                log.info("create bucket done,bucket {}", JSONUtils.valueToString(bucket));
                return Result.error("桶位创建成功");
            } else {
                return Result.error("桶位创建失败");
            }
        }
    }

    /**
     *  永久密钥上传
     * @param bucketName
     * @return
     * @throws Exception
     */
    @Override
    public Result<?> uploadObject(StoreRequest storeRequest) throws Exception {
        log.info("upload object {}", storeRequest);

        //参数校验
        String errorMsg = cosConvertImpl.validate(storeRequest);
        if (StringUtils.isNotEmpty(errorMsg)) {
            log.error(errorMsg);
            return Result.error(errorMsg);
        }

        //协议对象转换
        PutObjectRequest putObjectRequest = cosConvertImpl.convert(storeRequest);

        //cos存储
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        if (putObjectResult != null && StringUtils.isNotBlank(putObjectResult.getETag())) {
            log.info("upload object on cos done,result {}", JSONUtils.valueToString(putObjectResult));
            storeRequest.getInputStream().close();
            Map<String, Object> data = new HashMap<>();
            data.put(URL, cosConvertImpl.getObjectUrl(storeRequest.getBucketName(), storeRequest.getPathKey()));
            return Result.ok(data);
        } else {
            return Result.error();
        }
    }

    //========================== 临时密钥 ================================
    /**
     * 获取腾讯云临时密钥
     * @return
     * @throws Exception
     */
    public StoreRequest getCredential(StoreRequest storeRequest) throws IOException, JSONException {
        // 请求临时密钥信息
        JSONObject credential = CosStsClient.getCredential(config);
        JSONObject credentials = (JSONObject) credential.get("credentials");
        // 请求成功：打印对应的临时密钥信息
        System.out.println(credential.toString(4));
        storeRequest.setTmpSecretId(credentials.get("tmpSecretId").toString());
        storeRequest.setTmpSecretKey(credentials.get("tmpSecretKey").toString());
        storeRequest.setSessionToken(credentials.get("sessionToken").toString());
        storeRequest.setStartTime(credential.get("startTime").toString());
        storeRequest.setExpiredTime(credential.get("expiredTime").toString());
        return storeRequest;
    }

    /**
     *  临时密钥初始化cos客户端
     * @param credential    临时密钥
     */
    public static void init(StoreRequest storeRequest) throws JSONException {
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        String tmpSecretId = storeRequest.getTmpSecretId();
        String tmpSecretKey = storeRequest.getTmpSecretKey();
        String sessionToken = storeRequest.getSessionToken();
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // 2 设置 bucket 的区域, COS 地域的简称请参阅 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参阅源码或者常见问题 Java SDK 部分
        Region region = new Region(config.get("region").toString());
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        cosClient = new COSClient(cred, clientConfig);
    }

    /**
     * 临时密钥上传
     * @param storeRequest
     * @return
     */
    public Result<?> credentialUpload(StoreRequest storeRequest) throws Exception {
        log.info("upload object {}", storeRequest);
        //参数校验
        String errorMsg = cosConvertImpl.validate(storeRequest);
        if (StringUtils.isNotEmpty(errorMsg)) {
            log.error(errorMsg);
            return Result.error(errorMsg);
        }

        //协议对象转换
        PutObjectRequest putObjectRequest = cosConvertImpl.convert(storeRequest);

        // 设置 x-cos-security-token header 字段
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setSecurityToken(storeRequest.getSessionToken());
        putObjectRequest.setMetadata(objectMetadata);

        //cos存储
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        if (putObjectResult != null && StringUtils.isNotBlank(putObjectResult.getETag())) {
            log.info("upload object on cos done,result {}", JSONUtils.valueToString(putObjectResult));
            storeRequest.getInputStream().close();
//            Map<String, Object> data = new HashMap<>();
//            data.put(URL, cosConvertImpl.getObjectUrl(storeRequest.getBucketName(), storeRequest.getPathKey()));
            String data = cosConvertImpl.getObjectUrl(storeRequest.getBucketName(), storeRequest.getPathKey());
            return Result.ok(data);
        } else {
            return Result.error();
        }

    }

    /**
     * 临时密钥下载-未完成
     * @param storeRequest
     * @param bucketName
     * @param localFile
     * @throws Exception
     */
     public void credentialDownload(StoreRequest storeRequest, String bucketName,File localFile) throws Exception {
         log.info("upload object {}", storeRequest);
         //参数校验
         String errorMsg = cosConvertImpl.validate(storeRequest);
         if (StringUtils.isNotEmpty(errorMsg)) {
             log.error(errorMsg);
             //return Result.error(errorMsg);
         }

         //协议对象转换
         GetObjectRequest getObjectRequest = cosConvertImpl.convert(storeRequest);

         // 获取文件流
         COSObject cosObject =cosClient.getObject(getObjectRequest);
         log.info("upload object on cos done,result {}", JSONUtils.valueToString(cosObject));
         COSObjectInputStream cosObjectInput = cosObject.getObjectContent();

         // 下载
         /*String key = config.get("allowPrefix").toString();
         File downFile = new File("src/test/resources/len5M_down.txt");
          GetObjectRequest getObjectRequest =new GetObjectRequest(bucketName, key);
         ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);*/
    }

}
