package yll.component.store.service;

import com.github.relucent.base.util.model.Result;
import yll.component.store.domain.StoreRequest;

/**
 * 存储服务外部调用接口
 * @author cc
 */
public interface FileService {

    /** 上传 */
    Result<?> upload(StoreRequest storeRequest);

    /** 获取密钥给第三方 */
    void getCredentialKeys(StoreRequest storeRequest);

}
