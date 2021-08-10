package yll.component.store.service.impl;

import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yll.common.security.app.AppPrincipal;
import yll.common.security.app.AppSecuritys;
import yll.component.store.config.StoreConfigProperties;
import yll.component.store.domain.StoreRequest;
import yll.component.store.service.FileService;
import yll.component.util.JedisUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 存储服务外部调用service
 * @author cc
 */
@Service("fileService")
public class FileServiceImpl implements FileService {

    public static final String REDIS_PREFIX = "YLL:COSTOKEN";
    public static final String STORE_TYPE_COS = "COS";
    public static final String STORE_TYPE_OOS = "OOS";
    public static final String COS_UPLOAD_URL = "/yshd/upload/";

    @Autowired
    private AppSecuritys appSecuritys;

    @Autowired
    private Securitys adminSecuritys;

    @Autowired
    private JedisUtils jedisUtils;

    @Autowired
    private CosStoreServiceImpl cosStoreService;

    @Autowired
    private StoreConfigProperties storeConfigProperties;

    /**
     * 上传
     * @param storeRequest
     * @return
     */
    @Override
    public Result<?> upload(StoreRequest storeRequest) {
        try {
            //转换file
            if(STORE_TYPE_COS.equalsIgnoreCase(storeConfigProperties.getPlatform())){
                return  uploadCOS(storeRequest);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error();
    }

    /**
     * 腾讯COS上传
     * @param storeRequest
     * @return
     */
    private Result<?> uploadCOS(StoreRequest storeRequest) throws Exception {
        //获取临时密钥
        storeRequest = initCOS(storeRequest);
        return  cosStoreService.credentialUpload(storeRequest);
    }

    /**
     * COS临时密钥、客户端初始化
     * @return
     */
    private StoreRequest initCOS(StoreRequest storeRequest) throws IOException, JSONException {
        String key = getRedisKey();
        boolean check = checkRedisCredential(key);
        if(check){
            //缓存中获取临时密钥
            Map<String, String> credential = getRedisCredential(key);
            storeRequest.setTmpSecretId(credential.get("tmpSecretId"));
            storeRequest.setTmpSecretKey(credential.get("tmpSecretKey"));
            storeRequest.setSessionToken(credential.get("sessionToken"));
            storeRequest.setStartTime(credential.get("startTime"));
            storeRequest.setExpiredTime(credential.get("expiredTime"));
            //初始化cos客户端
            cosStoreService.init(storeRequest);
            return storeRequest;
        }   else{
            //重新获取临时密钥并初始化客户端
            //1、获取临时密钥
             cosStoreService.getCredential(storeRequest);
            //2、初始化cos客户端 - 调用方直接上传，不经过后台服务器 2019.10.22
//           cosStoreService.init(storeRequest);
            //3、存储获取的临时密钥
            setRedisCredential(storeRequest);
            return storeRequest;
        }
    }

    /**
     * 存储获取的临时密钥
     *  可根据项目业务进行存储逻辑更改
     * @param storeRequest
     */
    public void setRedisCredential(StoreRequest storeRequest) {
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        Map<String, String> credentials = new HashMap<>();
        credentials.put("tmpSecretId", storeRequest.getTmpSecretId());
        credentials.put("tmpSecretKey", storeRequest.getTmpSecretKey());
        credentials.put("sessionToken", storeRequest.getSessionToken());
        credentials.put("startTime", storeRequest.getStartTime());
        credentials.put("expiredTime", storeRequest.getExpiredTime());
        String key = getRedisKey();
        jedisUtils.hmset(key,credentials);
        jedisUtils.expire(key, CosStoreServiceImpl.durationSeconds);
    }

    /**
     * 获取存储的临时密钥
     *  可根据项目业务进行存储逻辑更改
     */
    public Map<String, String> getRedisCredential(String key) {
        return jedisUtils.hgetall(key);
    }

    /**
     * 临时密钥是否存在
     *  可根据项目业务进行存储逻辑更改
     */
    public boolean checkRedisCredential(String key) {
        return jedisUtils.exists(key);
    }

    /**
     * 获取Rediskey，不同用户key不同 - (弃用)
     * @return
     */
    @Deprecated
    private String getRedisKey2() {
        Principal adminPrincipal = adminSecuritys.getPrincipal();
        if(StringUtils.isBlank(adminPrincipal.getUserId())){
            //app请求
            AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();
            return REDIS_PREFIX.concat(appPrincipal.getCustomerId());
        }
        return REDIS_PREFIX.concat(adminPrincipal.getUserId());
    }

    /**
     * 获取key
     * @return
     */
    private String getRedisKey() {
        return REDIS_PREFIX;
    }

    /**
     * 获取密钥给第三方
     * @return
     */
    public void getCredentialKeys(StoreRequest storeRequest) {
        try {
            initCOS(storeRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw ExceptionHelper.prompt(e.getMessage());
        }
    }

}
