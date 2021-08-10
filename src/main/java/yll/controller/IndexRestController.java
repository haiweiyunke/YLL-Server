package yll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.github.relucent.base.plug.security.AuthToken;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.model.Result;
import org.springframework.web.multipart.MultipartFile;
import yll.common.identifier.IdHelper;
import yll.component.store.domain.StoreRequest;
import yll.component.store.service.FileService;
import yll.service.model.YllConstants;

import java.util.HashMap;
import java.util.Map;


/**
 * 主页/登录/注销 视图类
 */
@RestController
@RequestMapping(value = "/rest")
public class IndexRestController {      //TODO 补上 产品、课件 精品轮播图后台列表、编辑模块，参照资讯轮播。   搞积分接口（结合当天操作记录，集成在原先阅读、收藏里边）

    // ==============================Fields===========================================
    /** COS上传路径 */
    @Value("${store.path:/yshd/prod/}")
    protected String path;
    /** COS存储桶 */
    @Value("${store.bucket:yshd-1256225403}")
    protected String bucket;
    /** COS区域 */
    @Value("${store.region:ap-beijing}")
    protected String region;
    /** 后台管理系统COS回显路径 */
    @Value("${store.management-system-path:/prod/}")
    protected String managementSystemPath;

    @Autowired
    private Securitys securitys;
    @Autowired
    private FileService fileService;

    // ==============================Methods==========================================
    /**
     * [POST] | /rest/login <br>
     * 用户登录
     */
    // @OpLogAx("用户登录")
    @PostMapping(value = "/login")
    public Result<?> doLogin(@RequestBody AuthToken token) {
        securitys.login(token);
        return Result.ok();
    }

    /**
     * [POST] | /rest/logout <br>
     * 用户登出
     */
    // @OpLogAx("用户登出")
    @RequestMapping(value = "/logout")
    public Result<?> logout() {
        securitys.logout();
        return Result.ok();
    }

    /**
     * 获取密钥给第三方
     * [GET] /rest/cos-keys <br>
     *
     */
    @GetMapping(value = "/cos-keys")
    public Result<?> keys(StoreRequest storeRequest, MultipartFile file) {
        fileService.getCredentialKeys(storeRequest);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("tmpSecretId", storeRequest.getTmpSecretId());
        credentials.put("tmpSecretKey", storeRequest.getTmpSecretKey());
        credentials.put("sessionToken", storeRequest.getSessionToken());
        credentials.put("startTime", storeRequest.getStartTime());
        credentials.put("expiredTime", storeRequest.getExpiredTime());

        result.put("credentials", credentials);
        return Result.ok(result);
    }

    /**
     * 获取COS配置
     * [GET] /rest/config <br>
     *
     */
    @GetMapping(value = "/config")
    public Result<?> config() {

        Map<String, Object> result = new HashMap<>();
        result.put("bucket", bucket);
        result.put("region", region);
        result.put("path", path);
        return Result.ok(result);
    }

    /**
     * 获取COS后台上传文件夹路径
     * [GET] /rest/msPath <br>
     *
     */
    @GetMapping(value = "/msPath")
    public Result<?> msPath() {
        Map<String, Object> result = new HashMap<>();
        result.put("msPath", managementSystemPath);
        return Result.ok(result);
    }

    /**
     * 获取随机数
     * [GET] /rest/random <br>
     *
     */
    @GetMapping(value = "/random")
    public Result<?> random() {
        return Result.ok(IdHelper.nextId());
    }

}
