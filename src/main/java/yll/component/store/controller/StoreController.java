package yll.component.store.controller;

import com.github.relucent.base.util.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yll.component.store.domain.StoreRequest;
import yll.component.store.service.IStoreService;

import java.util.Date;
import java.util.UUID;


/**
 * 存储服务API
 * @author cc
 */
@Slf4j
@Controller
public class StoreController {

    @Autowired
    private IStoreService storeService;

    /**
     * 创建桶位
     *
     * @param bucketName
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/create-bucket")
    public Result<?> createBucket(String bucketName) {
        try {
            return storeService.createBucket(bucketName);
        } catch (Exception e) {
            log.error("create bucket error ", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 上传文件对象
     *
     * @param storeRequest
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/upload-object")
    public Result<?> uploadObject(StoreRequest storeRequest, @RequestParam(value = "file") MultipartFile file) {
        try {
            if (file != null) {
                storeRequest.setInputStream(file.getInputStream());
                storeRequest.setStreamLength(file.getSize());
                storeRequest.setFileName(getUniqueFileName(file.getOriginalFilename()));
            }
            return storeService.uploadObject(storeRequest);
        } catch (Exception e) {
            log.error("upload object error", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 返回唯一文件名
     *
     * @param fileName
     * @return
     */
    private String getUniqueFileName(String fileName) {
        String uuid = UUID.randomUUID().toString();
        long time = new Date().getTime();
        uuid = StringUtils.replace(uuid, "-", "");
        return uuid + time + StringUtils.substring(fileName, fileName.lastIndexOf("."));
    }
}
