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
import yll.entity.ActivitiesModules;
import yll.service.ActivitiesModulesService;
import yll.service.model.vo.ActivitiesModulesVo;

/**
 * 活动详情模块
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/activities-modules")
public class ActivitiesModulesRestController {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesModulesService activitiesModulesService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/activities-modules/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody ActivitiesModulesVo entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            activitiesModulesService.insert(entity);
        } else {
            activitiesModulesService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/activities-modules/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        activitiesModulesService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/activities-modules/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        activitiesModulesService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/activities-modules/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        ActivitiesModules entity = activitiesModulesService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/activities-modules/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ActivitiesModulesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ActivitiesModulesVo());
        Page<ActivitiesModules> page = activitiesModulesService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

}
