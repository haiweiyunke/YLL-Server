package yll.component.store.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.github.relucent.base.util.model.Result;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import yll.component.store.config.StoreConfigProperties;
import yll.component.store.convert.IConvert;
import yll.component.store.domain.StoreRequest;
import yll.component.store.service.IStoreService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

/**
 * 阿里oss存储服务
 * @author cc
 */
@Slf4j
@Service("storeService")
@ConditionalOnProperty(name = "store.platform", havingValue = "oss")
public class OssStoreServiceImpl implements IStoreService {

    @Autowired
    private StoreConfigProperties storeConfigProperties;

    @Autowired
    private IConvert<GenericRequest> ossConvertImpl;

    private static OSSClient ossClient;

    @PostConstruct
    @Override
    public void init() {

        //生成oss客户端
        ossClient = new OSSClient(storeConfigProperties.getEndpoint(), storeConfigProperties.getCredential().getSecrectId(), storeConfigProperties.getCredential().getSecrectKey());
    }

    @PreDestroy
    @Override
    public void destroy() {
        ossClient.shutdown();
    }

    @Override
    public Result<?> createBucket(String bucketName) throws Exception {
        log.info("create bucket {}", bucketName);
        if (StringUtils.isBlank(bucketName)) {
            return Result.error("桶位名称不能为空");
        }
        boolean result = ossClient.doesBucketExist(bucketName);
        if (result) {
            return Result.error("桶位已经存在");
        } else {
            Bucket bucket = ossClient.createBucket(bucketName);
            if (bucket != null) {
                log.info("create bucket done,bucket {}", JSONUtils.valueToString(bucket));
                return Result.error("桶位创建成功");
            } else {
                return Result.error("桶位创建失败");
            }
        }
    }

    @Override
    public Result<?> uploadObject(StoreRequest storeRequest) throws Exception {
        log.info("upload object {}", storeRequest);

        //参数校验
        String errorMsg = ossConvertImpl.validate(storeRequest);
        if (StringUtils.isNotEmpty(errorMsg)) {
            log.error(errorMsg);
            return Result.error(errorMsg);
        }

        //协议对象转换
        PutObjectRequest putObjectRequest = ossConvertImpl.convert(storeRequest);

        //oss存储
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(storeRequest.getStreamLength());
        PutObjectResult putObjectResult = ossClient.putObject(putObjectRequest);
        if (putObjectResult != null && StringUtils.isNotBlank(putObjectResult.getETag())) {
            log.info("upload object on oss done,result {}", JSONUtils.valueToString(putObjectResult));
            storeRequest.getInputStream().close();
            Map<String, Object> data = new HashMap<>();
            data.put(URL, ossConvertImpl.getObjectUrl(storeRequest.getBucketName(), storeRequest.getPathKey()));
            return Result.ok(data);
        } else {
            return Result.error();
        }
    }

}
