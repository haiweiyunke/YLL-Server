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
import yll.entity.Information;
import yll.entity.Slide;
import yll.service.InformationService;
import yll.service.SlideService;
import yll.service.model.YllConstants;
import yll.service.model.vo.InformationVo;

import java.util.List;

/**
 * 资讯管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/information")
public class InformationRestController {

    // ==============================Fields===========================================
    @Autowired
    private InformationService informationService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/information/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Information entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            informationService.insert(entity);
        } else {
            informationService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/information/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        informationService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/information/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        informationService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/information/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Information entity = informationService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/information/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Information condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Information());
        Page<Information> page = informationService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/information/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(@RequestBody InformationVo entity) {
        List<Information> list = informationService.findBySlide(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/information/slide/save <br>
     * 保存数据-轮播(新增|更新)
     */
    @PostMapping(value = "/slide/save")
    public Result<?> slideSave(@RequestBody InformationVo entity) {
        informationService.slide(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/information/slide/{id} <br>
     * 根据编号删除-轮播
     */
    @DeleteMapping(value = "/slide/{id}")
    public Result<?> slideDelete(@PathVariable("id") String id) {
        Information entity = informationService.getById(id);
        entity.setSlide(YllConstants.ZERO);
        entity.setOrdinal(null);
        informationService.update(entity);
        return Result.ok();
    }

}
