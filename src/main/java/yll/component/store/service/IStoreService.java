package yll.component.store.service;


import com.github.relucent.base.util.model.Result;
import yll.component.store.domain.StoreRequest;

/**
 * 存储服务接口
 * @author cc
 */
public interface IStoreService {

    /**
     * 初始化
     */
    void init();

    /**
     * 资源销毁
     */
    void destroy();

    /**
     * 创建桶位
     *
     * @param bucketName
     * @return
     */
    Result<?> createBucket(String bucketName) throws Exception;

    /**
     * 上传文件对象
     *
     * @param storeRequest
     * @return
     */
    Result<?> uploadObject(StoreRequest storeRequest) throws Exception;

    String URL = "url";

}
