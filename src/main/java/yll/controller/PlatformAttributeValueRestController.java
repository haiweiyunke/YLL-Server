package yll.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.PlatformAttributeKey;
import yll.entity.PlatformAttributeValue;
import yll.service.PlatformAttributeKeyService;
import yll.service.PlatformAttributeValueService;
import yll.service.model.vo.PlatformAttributeValueVo;
import yll.service.model.vo.PlatformVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 平台自定义属性-值 管理   TODO 后台页表页面、编辑页面
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/platform-value")
public class PlatformAttributeValueRestController {

    // ==============================Fields===========================================
    @Autowired
    private PlatformAttributeValueService platformAttributeValueService;
    @Autowired
    private PlatformAttributeKeyService platformAttributeKeyService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/platform-value/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody PlatformAttributeValue entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            platformAttributeValueService.insert(entity);
        } else {
            platformAttributeValueService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/platform-value/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        platformAttributeValueService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/platform-value/{id} <br>
     * 根据id查询详情
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        PlatformAttributeValue entity = platformAttributeValueService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/platform-value/gid/{gid} <br>
     * 根据gid查询详情
     */
     @GetMapping(value = "/gid/{gid}")
    public Result<?> getByGid(@PathVariable("gid") String gid) {
         PlatformAttributeValue condition = new PlatformAttributeValue();
         condition.setGid(gid);
         List<PlatformAttributeValue> list = platformAttributeValueService.getBySelf(condition);
        return Result.ok(list);
    }

    /**
     * [GET] /rest/platform-value/creator/{id} <br>
     * 根据用户查询详情
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        PlatformAttributeValue condition = new PlatformAttributeValue();
        condition.setCreator(cid);
        PlatformAttributeValue entity = platformAttributeValueService.getByCondition(condition);
        return Result.ok(entity);
    }

    /**
     * [POST] /rest/platform-value/keys <br>
     * 获取键
     */
    @PostMapping(value = "/keys")
    public Result<?> keys(@RequestBody PlatformAttributeValue condition) {
        String type = condition.getType();
        if(StringUtils.isBlank(type)){
            return Result.error("平台类型为空");
        }
        String cid = condition.getCid();
        String pid = condition.getPid();
        PlatformAttributeKey pkCondition = new PlatformAttributeKey();
        pkCondition.setType(type);
        List<PlatformAttributeKey> list = platformAttributeKeyService.all(pkCondition);
        List<PlatformAttributeValueVo> vlist = new ArrayList<>();
        for (PlatformAttributeKey kvo:
             list) {
            PlatformAttributeValueVo pv = new PlatformAttributeValueVo();
            pv.setKname(kvo.getName());
            pv.setName("");
            pv.setKid(kvo.getId());
            pv.setCid(cid);
            pv.setPid(pid);
            vlist.add(pv);
        }
        return Result.ok(vlist);
    }

    /**
     * [POST] /rest/platform-value/insert <br>
     * 保存数据(新增)
     */
    @PostMapping(value = "/insert")
    public Result<?> insert(@RequestBody PlatformVo condition) {
        String type = condition.getPlatformType();
        if(StringUtils.isBlank(type)){
            return Result.error("缺少平台类型");
        }
        /*String image = condition.getImage();
        if(StringUtils.isBlank(image)){
            return Result.error("缺少平台识别图片");
        }*/
        String attributesStr = condition.getAttributesStr();
        if(StringUtils.isBlank(attributesStr)){
            return Result.error("缺少平台识别结果");
        }
        String customerId = condition.getCreator();
        if(StringUtils.isBlank(customerId)){
            return Result.error("缺少所属人");
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
        platformAttributeValueService.insert(condition, customerId);
        return Result.ok();
    }

    /**
     * [POST] /rest/platform-value/update <br>
     * 保存数据(更新)
     */
    @PostMapping(value = "/update")
    public Result<?> update(@RequestBody PlatformVo condition) {
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
        platformAttributeValueService.update(condition);
        return Result.ok();
    }

}
