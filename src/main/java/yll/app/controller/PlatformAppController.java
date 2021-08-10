package yll.app.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.Platform;
import yll.entity.PlatformAttributeKey;
import yll.entity.PlatformAttributeValue;
import yll.service.InternetCelebrityService;
import yll.service.PlatformAttributeKeyService;
import yll.service.PlatformAttributeValueService;
import yll.service.PlatformService;
import yll.service.model.vo.PlatformVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  平台
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/platform")
public class PlatformAppController {

    // ==============================Fields===========================================
    @Autowired
    private PlatformService platformService;
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private PlatformAttributeValueService platformAttributeValueService;
    @Autowired
    private PlatformAttributeKeyService platformAttributeKeyService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/platform/detail <br>
     * 查询数据详情
     */
    @PostMapping(value = "/detail")
    public Result<?> detail(PlatformVo condition) {
        String id = condition.getId();
        String platformType = condition.getPlatformType();
        if(StringUtils.isBlank(id)){
            return Result.error("缺少达人标识");
        } else if(StringUtils.isBlank(platformType)){
            return Result.error("缺少平台标识");
        }
        condition.setCreator(id);    //用customerId查询
        condition.setId(null);
        condition = ObjectUtils.defaultIfNull(condition, new PlatformVo());
        PlatformVo result = platformService.getAppDetail(condition);
        return Result.ok(result);
    }


    /**
     * [POST] /app/platform/recognition <br>
     * 图片识别平台详情
     */
    @PostMapping(value = "/recognition")
    public Result<?> recognition(String platformJson) {
        if(StringUtils.isBlank(platformJson)){
            return Result.error("缺少平台数据");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        internetCelebrityService.updatePlatform(platformJson, customerId);
        return Result.ok();
    }

    /**
     * [POST] /app/platform/recognition-key <br>
     * 图片识别平台详情-表结构版
     */
    @PostMapping(value = "/recognition-key")
    public Result<?> recognitioKey(PlatformAttributeKey condition) {
        String type = condition.getType();
        if(StringUtils.isBlank(type)){
            return Result.error("平台类型为空");
        }
        List<PlatformAttributeKey> list = platformAttributeKeyService.all(condition);
        return Result.ok(list);
    }

    /**
     * [POST] /app/platform/recognition-tab <br>
     * 图片识别平台详情-表结构版
     */
    @PostMapping(value = "/recognition-tab")
    public Result<?> recognitionTab(PlatformVo condition) {
        String type = condition.getPlatformType();
        if(StringUtils.isBlank(type)){
            return Result.error("缺少平台类型");
        }
        String image = condition.getImage();
        if(StringUtils.isBlank(image)){
            return Result.error("缺少平台识别图片");
        }
        String attributesStr = condition.getAttributesStr();
        if(StringUtils.isBlank(attributesStr)){
            return Result.error("缺少平台识别结果");
        }
        ObjectMapper om = new ObjectMapper();
        JavaType javaType = om.getTypeFactory().constructParametricType(List.class, PlatformAttributeValue.class);
        List<PlatformAttributeValue> list = null;
        try {
            list = (List<PlatformAttributeValue>)om.readValue(attributesStr, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        condition.setAttributes(list);
        String customerId = AppSecuritysUtil.getCustomerId();
        platformAttributeValueService.insert(condition, customerId);
        return Result.ok();
    }

}
