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
import yll.entity.Activities;
import yll.service.ActivitiesService;
import yll.service.model.vo.ActivitiesVo;

import java.util.List;

/**
 * 活动管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/activities")
public class ActivitiesRestController {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesService activitiesService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/activities/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Activities entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            activitiesService.insert(entity);
        } else {
            activitiesService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/activities/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        activitiesService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/activities/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        activitiesService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/activities/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Activities entity = activitiesService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/activities/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ActivitiesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ActivitiesVo());
        Page<ActivitiesVo> page = activitiesService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/activities/all <br>
     * 查询全部数据
     */
    @GetMapping(value = "/all")
    public Result<?> allQuery(ActivitiesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ActivitiesVo());
        List<ActivitiesVo> list = activitiesService.allQueryWithType(condition);
        return Result.ok(list);
    }

}
