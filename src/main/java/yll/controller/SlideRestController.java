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
import yll.entity.*;
import yll.service.*;


/**
 * 混合轮播管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/slide")
public class SlideRestController {

    // ==============================Fields===========================================
    @Autowired
    private SlideService slideService;
    @Autowired
    private InformationService informationService;
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ConferencesService conferencesService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/slide/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Slide entity) {
        String id = entity.getId();
         String type = entity.getType();
         String targetId = entity.getTargetId();
         if(StringUtils.isBlank(type)){
            return Result.error("请选择轮播图类型");
        }
         if(StringUtils.isBlank(targetId)){
            return Result.error("请选择需要设定的数据");
        }
        if("information".equals(type)){
            Information vo = informationService.getById(targetId);
            if(vo == null){
                return Result.error("未找到相关数据");
            } else{
                entity.setName(vo.getName());
                entity.setImg(vo.getCover());
            }
        } else if("customer".equals(type)){
            InternetCelebrity vo = internetCelebrityService.getById(targetId);
            if(vo == null){
                return Result.error("未找到相关数据");
            } else{
                Customer cvo = customerService.getById(vo.getCreator());
                entity.setName(cvo.getNickname());
                if(StringUtils.isNotBlank(vo.getPersonalPortraits())){
                    String[] imgs = vo.getPersonalPortraits().split(",");
                    entity.setImg(imgs[0]);
                }
            }
        } else if("task".equals(type)){
            Task vo = taskService.getById(targetId);
            if(vo == null){
                return Result.error("未找到相关数据");
            } else{
                entity.setName(vo.getTaskName());
                entity.setImg(vo.getCover());
            }
        } else if("conferences".equals(type)){
            Conferences vo = conferencesService.getById(targetId);
            if(vo == null){
                return Result.error("未找到相关数据");
            } else{
                entity.setName(vo.getTheme());
                entity.setImg(vo.getCover());
            }
        }
         if (StringUtils.isBlank(id)) {
            slideService.insert(entity);
        } else {
            slideService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/slide/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        slideService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/slide/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        slideService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/slide/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Slide entity = slideService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/slide/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Slide condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Slide());
        Page<Slide> page = slideService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

}
