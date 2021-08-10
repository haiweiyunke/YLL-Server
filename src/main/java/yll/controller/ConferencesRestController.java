package yll.controller;

import com.github.relucent.base.util.collect.Mapx;
import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.Conferences;
import yll.entity.Conferences;
import yll.service.ConferencesService;
import yll.service.model.YllConstants;
import yll.service.model.vo.ConferencesVo;
import yll.service.model.vo.InternetCelebrityVo;

import java.util.List;


/**
 * 发布会管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/conferences")
public class ConferencesRestController {

    // ==============================Fields===========================================
    @Autowired
    private ConferencesService conferencesService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/conferences/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Conferences entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            entity.setCreatedTime(DateUtil.now());
            conferencesService.insert(entity);
        } else {
            conferencesService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/conferences/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        conferencesService.enable(id, enabled);
        return Result.ok();
    }


    /**
     * [DELETE] /rest/conferences/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        conferencesService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/conferences/{id} <br>
     * 根据id查询详情
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Conferences entity = conferencesService.getById(id);
        return Result.ok(entity);
    }


    /**
     * [GET] /rest/conferences/creator/{id} <br>
     * 根据用户查询详情
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        Conferences condition = new Conferences();
        condition.setCreator(cid);
        Conferences entity = conferencesService.getByCondition(condition);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/conferences/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ConferencesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ConferencesVo());
        Page<ConferencesVo> page = conferencesService.findByAdminList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [POST] /rest/conferences/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(@RequestBody ConferencesVo entity) {
        List<ConferencesVo> list = conferencesService.findBySlide(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/conferences/slide/save <br>
     * 保存数据-轮播(新增|更新)
     */
    @PostMapping(value = "/slide/save")
    public Result<?> slideSave(@RequestBody ConferencesVo entity) {
        conferencesService.slide(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/conferences/slide/{id} <br>
     * 根据编号删除-轮播
     */
    @DeleteMapping(value = "/slide/{id}")
    public Result<?> slideDelete(@PathVariable("id") String id) {
        Conferences entity = conferencesService.getById(id);
        entity.setSlide(YllConstants.ZERO);
        entity.setOrdinal(null);
        conferencesService.update(entity);
        return Result.ok();
    }

    /**
     * [GET] /rest/conferences/extension-list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/extension-list")
    public Result<?> pagedQueryExtension(Pagination pagination, ConferencesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ConferencesVo());
        Page<ConferencesVo> page = conferencesService.pagedQueryExtension(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/conferences/extension/list <br>
     * 查询推广数据
     */
    @PostMapping(value = "/extension/list")
    public Result<?> extensionList(@RequestBody ConferencesVo entity) {
        List<ConferencesVo> list = conferencesService.findByExtension(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/conferences/extension/save <br>
     * 保存数据-推广(新增|更新)
     */
    @PostMapping(value = "/extension/save")
    public Result<?> extensionSave(@RequestBody ConferencesVo entity) {
        conferencesService.extension(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/conferences/extension/{id} <br>
     * 根据编号删除-推广
     */
    @DeleteMapping(value = "/extension/{id}")
    public Result<?> extensionDelete(@PathVariable("id") String id) {
        Conferences entity = conferencesService.getById(id);
        entity.setExtension(YllConstants.ZERO);
        entity.setExtensionOrdinal(YllConstants.LAST);
        conferencesService.update(entity);
        return Result.ok();
    }



}
