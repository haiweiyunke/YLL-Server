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
import yll.entity.Shops;
import yll.service.ShopsService;
import yll.service.model.YllConstants;
import yll.service.model.vo.ShopsVo;

import java.util.List;

/**
 * 店铺管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/shops")
public class ShopsRestController {

    // ==============================Fields===========================================
    @Autowired
    private ShopsService shopsService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/shops/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Shops entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            entity.setEnabled(YllConstants.ONE);
            entity.setState(YllConstants.TWO);   //2-已审核
            shopsService.insert(entity);
        } else {
            shopsService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/shops/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        shopsService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/shops/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        shopsService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/shops/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Shops entity = shopsService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/shops/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Shops condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Shops());
        Page<Shops> page = shopsService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/shops/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(@RequestBody ShopsVo entity) {
        List<Shops> list = shopsService.findBySlide(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/shops/slide/save <br>
     * 保存数据-轮播(新增|更新)
     */
    @PostMapping(value = "/slide/save")
    public Result<?> slideSave(@RequestBody ShopsVo entity) {
        shopsService.slide(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/shops/slide/{id} <br>
     * 根据编号删除-轮播
     */
    @DeleteMapping(value = "/slide/{id}")
    public Result<?> slideDelete(@PathVariable("id") String id) {
        Shops entity = shopsService.getById(id);
        entity.setSlide(YllConstants.ZERO);
        entity.setOrdinal(null);
        shopsService.update(entity);
        return Result.ok();
    }

    /**
     * [POST] /rest/shops/all <br>
     * 查询所所有数据列表
     */
    @PostMapping(value = "/all")
    public Result<?> pagedQuery(Shops condition) {
        condition.setEnabled(1);
        condition.setState(2);
        List<Shops> list = shopsService.all(condition);
        return Result.ok(list);
    }

}
