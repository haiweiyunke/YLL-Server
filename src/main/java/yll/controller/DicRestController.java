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
import yll.entity.Dic;
import yll.entity.DicType;
import yll.service.DicService;

import java.util.List;

/**
 * 数据字典管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/dic")
public class DicRestController {

    // ==============================Fields===========================================
    @Autowired
    private DicService dicService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/dic/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Dic entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            dicService.insert(entity);
        } else {
            dicService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/dic/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        dicService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/dic/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        dicService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/dic/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Dic entity = dicService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/dic/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Dic condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Dic());
        Page<Dic> page = dicService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/dic/all <br>
     * 查询所有数据
     */
    @PostMapping(value = "/all")
    public Result<?> all(@RequestBody Dic condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Dic());
        List<Dic> list = dicService.all(condition);
        return Result.ok(list);
    }

}
