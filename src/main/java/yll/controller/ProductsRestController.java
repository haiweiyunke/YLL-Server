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
import yll.entity.Products;
import yll.service.ProductsService;
import yll.service.model.YllConstants;
import yll.service.model.vo.ProductsVo;

import java.util.List;

/**
 * 商品管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/products")
public class ProductsRestController {

    // ==============================Fields===========================================
    @Autowired
    private ProductsService productsService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/products/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Products entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            entity.setEnabled(YllConstants.ONE);
            entity.setState(YllConstants.TWO);   //2-已审核
            productsService.insert(entity);
        } else {
            productsService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/products/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        productsService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/products/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        productsService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/products/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Products entity = productsService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/products/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Products condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Products());
        Page<Products> page = productsService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/products/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(@RequestBody ProductsVo entity) {
        List<Products> list = productsService.findBySlide(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/products/slide/save <br>
     * 保存数据-轮播(新增|更新)
     */
    @PostMapping(value = "/slide/save")
    public Result<?> slideSave(@RequestBody ProductsVo entity) {
        productsService.slide(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/products/slide/{id} <br>
     * 根据编号删除-轮播
     */
    @DeleteMapping(value = "/slide/{id}")
    public Result<?> slideDelete(@PathVariable("id") String id) {
        Products entity = productsService.getById(id);
        entity.setSlide(YllConstants.ZERO);
        entity.setOrdinal(null);
        productsService.update(entity);
        return Result.ok();
    }

}
