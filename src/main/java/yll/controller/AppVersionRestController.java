package yll.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.AppVersion;
import yll.service.AppVersionService;


/**
 * APP版本
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/app-version")
public class AppVersionRestController {

    // ==============================Fields===========================================
    @Autowired
    private AppVersionService appVersionService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/app-version/save <br>
     * 保存数据(新增|更新)
     */
    @PostMapping(value = "/save")
    public Result<?> save(@RequestBody AppVersion entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            appVersionService.insert(entity);
        } else {
            appVersionService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/app-version/{id} <br>
     * 根据编号删除
     */
    @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        appVersionService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/app-version/{id} <br>
     * 保存数据(新增|更新)
     */
    @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        AppVersion entity = appVersionService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/app-version/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, AppVersion condition) {
        condition = ObjectUtils.defaultIfNull(condition, new AppVersion());
        Page<AppVersion> page = appVersionService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

}
