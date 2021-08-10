package yll.controller;

import com.github.relucent.base.util.collect.Mapx;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.Enterprise;
import yll.entity.PlatformAttributeKey;
import yll.service.PlatformAttributeKeyService;

import java.util.List;


/**
 * 平台自定义属性-键 管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/platform-key")
public class PlatformAttributeKeyRestController {

    // ==============================Fields===========================================
    @Autowired
    private PlatformAttributeKeyService platformAttributekeyService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/platform-key/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody PlatformAttributeKey entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            platformAttributekeyService.insert(entity);
        } else {
            platformAttributekeyService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [GET] /rest/platform-key/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, PlatformAttributeKey condition) {
        condition = ObjectUtils.defaultIfNull(condition, new PlatformAttributeKey());
        Page<PlatformAttributeKey> page = platformAttributekeyService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [DELETE] /rest/platform-key/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        platformAttributekeyService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/platform-key/{id} <br>
     * 根据id查询详情
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        PlatformAttributeKey entity = platformAttributekeyService.getById(id);
        return Result.ok(entity);
    }


    /**
     * [GET] /rest/platform-key/creator/{id} <br>
     * 根据用户查询详情
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        PlatformAttributeKey condition = new PlatformAttributeKey();
        condition.setCreator(cid);
        PlatformAttributeKey entity = platformAttributekeyService.getByCondition(condition);
        return Result.ok(entity);
    }

    /**
     * [POST] /rest/platform-key/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        platformAttributekeyService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [POST] /rest/platform-key/state <br>
     * [BODY] {id:?,state:?}<br>
     * 图表启用禁用
     */
    @PostMapping(value = "/state")
    public Result<?> state(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer state = params.getInteger("state");
        platformAttributekeyService.state(id, state);
        return Result.ok();
    }

    /**
     * [POST] /rest/platform-key/all <br>
     * 查询所所有数据列表
     */
    @PostMapping(value = "/all")
    public Result<?> pagedQuery(PlatformAttributeKey condition) {
        condition.setEnabled(1);
        condition.setState(2);
        List<PlatformAttributeKey> list = platformAttributekeyService.all(condition);
        return Result.ok(list);
    }


}
