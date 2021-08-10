package yll.component.store.convert;

import com.qcloud.cos.internal.CosServiceRequest;
import yll.component.store.domain.StoreRequest;

/**
 * 存储转换校验接口
 * @author cc
 */
public interface IConvert<T> {

    /**
     * 对象转换
     *
     * @param storeRequest
     * @return
     */
    <T> T convert(StoreRequest storeRequest) throws Exception;

    /**
     * 参数校验
     *
     * @param storeRequest
     * @return
     */
    String validate(StoreRequest storeRequest);

    /**
     * 获得可访问的url
     * @param bucketName
     * @param pathKey
     * @return
     */
    String getObjectUrl(String bucketName, String pathKey);

    /**
     * 替换bucket占位符
     */
    String REPLACE_BUCKET = "replace-bucket";
}
