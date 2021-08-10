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
import yll.entity.ActivitiesAnswer;
import yll.service.ActivitiesAnswerService;
import yll.service.model.vo.ActivitiesAnswerVo;

import java.util.List;

/**
 * 活动竞赛答案管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/activities-answer")
public class ActivitiesAnswerRestController {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesAnswerService activitiesAnswerService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/activities-answer/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody ActivitiesAnswer entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            activitiesAnswerService.insert(entity);
        } else {
            activitiesAnswerService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/activities-answer/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        activitiesAnswerService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/activities-answer/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        activitiesAnswerService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/activities-answer/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        ActivitiesAnswer entity = activitiesAnswerService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/activities-answer/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ActivitiesAnswerVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ActivitiesAnswerVo());
        Page<ActivitiesAnswerVo> page = activitiesAnswerService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/activities-answer/all <br>
     * 查询全部数据
     */
    @GetMapping(value = "/all")
    public Result<?> allQuery(ActivitiesAnswer condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ActivitiesAnswer());
        List<ActivitiesAnswer> list = activitiesAnswerService.allQuery(condition);
        return Result.ok(list);
    }

}
