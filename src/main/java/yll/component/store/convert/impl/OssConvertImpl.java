package yll.component.store.convert.impl;

import com.aliyun.oss.model.GenericRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import yll.component.store.config.StoreConfigProperties;
import yll.component.store.convert.IConvert;
import yll.component.store.domain.StoreRequest;

/**
 * 阿里对象转换校验
 * @author cc
 */
@Component
@ConditionalOnProperty(name = "store.platform",havingValue = "oss")
public class OssConvertImpl implements IConvert<GenericRequest> {

    @Autowired
    private StoreConfigProperties storeConfigProperties;

    @Override
    public <T> T convert(StoreRequest storeRequest) throws Exception {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(storeRequest.getStreamLength());
        return (T) new PutObjectRequest(storeRequest.getBucketName(), storeRequest.getPathKey(), storeRequest.getInputStream(), objectMetadata);
    }

    @Override
    public String validate(StoreRequest storeRequest) {
        if (StringUtils.isBlank(storeRequest.getPathKey())) {
            return "文件路径key为空";
        }
        if (storeRequest.getStreamLength() == null) {
            return "输入流长度为空";
        }
        if (storeRequest.getInputStream() == null) {
            return "输入流为空";
        }
        if (StringUtils.isBlank(storeRequest.getFileName())) {
            return "文件名为空";
        }
        //oss不能以“/”或者“\”字符开头
        if (StringUtils.startsWith(storeRequest.getPathKey(), "/") || StringUtils.startsWith(storeRequest.getPathKey(), "\\")) {
            storeRequest.setPathKey(StringUtils.substring(storeRequest.getPathKey(), 1));
        }
        if (!StringUtils.endsWith(storeRequest.getPathKey(),"/")) {
            storeRequest.setPathKey(storeRequest.getPathKey() + "/" + storeRequest.getFileName());
        } else {
            storeRequest.setPathKey(storeRequest.getPathKey() + storeRequest.getFileName());
        }
        if (StringUtils.isBlank(storeRequest.getBucketName())) {
            storeRequest.setBucketName(storeConfigProperties.getDefaultBucket());
        }
        return null;
    }

    @Override
    public String getObjectUrl(String bucketName, String pathKey) {
        String url = storeConfigProperties.getUrl();
        url = StringUtils.replace(url, REPLACE_BUCKET, bucketName);
        return url + pathKey;
    }
}
