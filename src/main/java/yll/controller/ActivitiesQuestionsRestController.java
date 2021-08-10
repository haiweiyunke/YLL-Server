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
import yll.entity.ActivitiesQuestions;
import yll.service.ActivitiesQuestionsService;
import yll.service.model.vo.ActivitiesQuestionsVo;

/**
 * 活动竞赛题目管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/activities-questions")
public class ActivitiesQuestionsRestController {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesQuestionsService activitiesQuestionsService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/activities-questions/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody ActivitiesQuestionsVo entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            activitiesQuestionsService.insert(entity);
        } else {
            activitiesQuestionsService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/activities-questions/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        activitiesQuestionsService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/activities-questions/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        activitiesQuestionsService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/activities-questions/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        ActivitiesQuestions entity = activitiesQuestionsService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/activities-questions/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ActivitiesQuestionsVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ActivitiesQuestionsVo());
        Page<ActivitiesQuestionsVo> page = activitiesQuestionsService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

}
