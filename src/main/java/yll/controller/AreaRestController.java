package yll.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.Area;
import yll.service.AreaService;

import java.util.List;

/**
 * 行政区划管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/area")
public class AreaRestController {

    // ==============================Fields===========================================
    @Autowired
    private AreaService areaService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/area/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Area entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            areaService.insert(entity);
        } else {
            areaService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/area/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        areaService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/area/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Area entity = areaService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/area/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Area condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Area());
        Page<Area> page = areaService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/area/all <br>
     * 查询数据列表
     */
    @GetMapping(value = "/all")
    public Result<?> allQuery(Area condition) {
        condition = ObjectUtils.defaultIfNull(condition,  new Area());
        List<Area> list = areaService.getAppList(condition);
        return Result.ok(list);
    }

}
