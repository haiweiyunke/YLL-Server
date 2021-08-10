package yll.app.controller;

import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.Shops;
import yll.service.CommonService;
import yll.service.DicService;
import yll.service.ProductsService;
import yll.service.ShopsService;
import yll.service.model.YllConstants;
import yll.service.model.vo.ProductsVo;

import java.util.List;


/**
 * APP商品
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/products")
public class ProductsAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommonService commonService;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private DicService dicService;
    @Autowired
    private ShopsService shopsService;

    // ==============================Methods==========================================
    /*
    *   TODO
    *    1、对着PDF，搞商品APP接口（新增、列表、详情）。 ok
    *    2、商品接口全部完事儿后，测试App发布任务接口，  ok
    *   3、接着写任务详情ok、列表接口ok
    *   4、任务后台
    *   5、对着流程图PDF，搞任务流程
     */

    /**
     * [POST] /app/products/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ProductsVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ProductsVo());
        condition.setEnabled(YllConstants.ONE);
        condition.setState(YllConstants.TWO);   //2-已审核
        Page<ProductsVo> page = productsService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/products/myself/list <br>
     * 查询数据列表(当前用户自己的数据)
     */
    @PostMapping(value = "/myself/list")
    public Result<?> myselfList(Pagination pagination, ProductsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition = ObjectUtils.defaultIfNull(condition, new ProductsVo());
        condition.setEnabled(YllConstants.ONE);
        condition.setCreator(customerId);
        Page<ProductsVo> page = productsService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/products/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //封装
        ProductsVo entity = new ProductsVo();
        entity.setId(id);
        //获取详情
        entity = productsService.getAppDetail(entity);
        return Result.ok(entity);
    }

    /**
     * [POST] /app/products/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(Pagination pagination, ProductsVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ProductsVo());
        pagination.setOffset(0);
        pagination.setLimit(4);
        condition.setSlide(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        Page<ProductsVo> page = productsService.getAppList(pagination, condition);
        return Result.ok(page.getRecords());
    }


    /**
     * [POST] /app/products/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(ProductsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        String sid = "";
                //有商铺用户才允许新增
        Shops shops = new Shops();
        shops.setEnabled(1);
        shops.setDeleted(0);
        shops.setEid(customerId);
        List<Shops> shopsList = shopsService.all(shops);
        if(shopsList.size() < 1){
            //暂时自动新增店铺
            shops = shopsService.insert(shops);
            sid = shops.getId();
//            return Result.error("您暂无店铺，请新建后尝试");
        } else{
            sid = shopsList.get(0).getId();
        }
        condition.setSid(sid);
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            condition.setEnabled(1);
            condition.setCreator(customerId);
            condition.setCreatedTime(DateUtil.now());
            productsService.insert(condition);
        } else {
            condition.setModifier(customerId);
            condition.setModifiedTime(DateUtil.now());
            productsService.update(condition);
        }
        return Result.ok();
    }


}
