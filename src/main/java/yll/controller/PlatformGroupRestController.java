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
import yll.entity.PlatformGroup;
import yll.service.PlatformGroupService;

import java.util.List;


/**
 * 平台自定义属性-组 管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/platform-group")
public class PlatformGroupRestController {

    // ==============================Fields===========================================
    @Autowired
    private PlatformGroupService platformGroupService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/platform-group/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody PlatformGroup entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            platformGroupService.insert(entity);
        } else {
            platformGroupService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [GET] /rest/platform-group/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, PlatformGroup condition) {
        condition = ObjectUtils.defaultIfNull(condition, new PlatformGroup());
        Page<PlatformGroup> page = platformGroupService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [DELETE] /rest/platform-group/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        platformGroupService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/platform-group/{id} <br>
     * 根据id查询详情
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        PlatformGroup entity = platformGroupService.getById(id);
        return Result.ok(entity);
    }


    /**
     * [GET] /rest/platform-group/creator/{id} <br>
     * 根据用户查询详情
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        PlatformGroup condition = new PlatformGroup();
        condition.setCreator(cid);
        PlatformGroup entity = platformGroupService.getByCondition(condition);
        return Result.ok(entity);
    }

    /**
     * [POST] /rest/platform-group/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        platformGroupService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [POST] /rest/platform-group/all <br>
     * 查询所所有数据列表
     */
    @PostMapping(value = "/all")
    public Result<?> pagedQuery(PlatformGroup condition) {
        condition.setEnabled(1);
        condition.setState(2);
        List<PlatformGroup> list = platformGroupService.all(condition);
        return Result.ok(list);
    }


}
