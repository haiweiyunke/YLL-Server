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
import yll.entity.DicType;
import yll.service.DicTypeService;

import java.util.List;

/**
 * 数据字典类别管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/dic-type")
public class DicTypeRestController {

    // ==============================Fields===========================================
    @Autowired
    private DicTypeService dicTypeService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/dic-type/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody DicType entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            dicTypeService.insert(entity);
        } else {
            dicTypeService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/dic-type/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        dicTypeService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/dic-type/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        dicTypeService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/dic-type/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        DicType entity = dicTypeService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/dic-type/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, DicType condition) {
        condition = ObjectUtils.defaultIfNull(condition, new DicType());
        Page<DicType> page = dicTypeService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/dic-type/all <br>
     * 查询所有数据
     */
    @PostMapping(value = "/all")
    public Result<?> all(DicType condition) {
        condition = ObjectUtils.defaultIfNull(condition, new DicType());
        List<DicType> list = dicTypeService.all(condition);
        return Result.ok(list);
    }
}
