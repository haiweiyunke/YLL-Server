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
import yll.entity.Task;
import yll.entity.Dic;
import yll.service.DicService;
import yll.service.TaskService;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 任务管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/task")
public class TaskRestController {

    // ==============================Fields===========================================
    @Autowired
    private TaskService taskService;
    @Autowired
    private DicService dicService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/task/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Task entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            taskService.insert(entity);
        } else {
            taskService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/task/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        taskService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/task/{id} <br>
     * 根据id查询详情
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Task entity = taskService.getById(id);
        return Result.ok(entity);
    }


    /**
     * [POST] /rest/task/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        taskService.enable(id, enabled);
        return Result.ok();
    }


    /**
     * [GET] /rest/task/creator/{id} <br>
     * 根据用户查询详情
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        Task condition = new Task();
        condition.setCreator(cid);
        Task entity = taskService.getByCondition(condition);
        return Result.ok(entity);
    }


    /**
     * [GET] /rest/task/state/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/state/list")
    public Result<?> statePagedQuery(Pagination pagination, TaskVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new TaskVo());
        Page<TaskVo> page = taskService.findByAdminList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [GET] /rest/task/dic/all <br>
     * 查询编辑页字典所需数据
     */
    @GetMapping(value = "/dic/all")
    public Result<?> dicAll() {
        //带货方式
        List<Dic> typeList = dicService.getAppList("cooperation");
        //带货场地
        List<Dic> taskPlaceList = dicService.getAppList("taskPlace");
        //达人领域
        List<Dic> expertiseList = dicService.getAppList("expertise");
        //带货平台
        List<Dic> platformList = dicService.getAppList("platform");
        //所属类目
        List<Dic> categoryList = dicService.getAppList("category");
        //优惠方式
        List<Dic> discountList = dicService.getAppList("discount");

        Map<String,Object> result = new HashMap<>();
        result.put("typeList", typeList);
        result.put("taskPlaceList", taskPlaceList);
        result.put("expertiseList", expertiseList);
        result.put("platformList", platformList);
        result.put("categoryList", categoryList);
        result.put("discountList", discountList);
        return Result.ok(result);
    }


    /**
     * [POST] /rest/task/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(@RequestBody TaskVo entity) {
        List<TaskVo> list = taskService.findBySlide(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/task/slide/save <br>
     * 保存数据-轮播(新增|更新)
     */
    @PostMapping(value = "/slide/save")
    public Result<?> slideSave(@RequestBody TaskVo entity) {
        taskService.slide(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/task/slide/{id} <br>
     * 根据编号删除-轮播
     */
    @DeleteMapping(value = "/slide/{id}")
    public Result<?> slideDelete(@PathVariable("id") String id) {
        Task entity = taskService.getById(id);
        entity.setSlide(YllConstants.ZERO);
        entity.setOrdinal(YllConstants.LAST);
        taskService.update(entity);
        return Result.ok();
    }

    /**
     * [GET] /rest/task/extension-list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/extension-list")
    public Result<?> pagedQueryExtension(Pagination pagination, TaskVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new TaskVo());
        Page<TaskVo> page = taskService.pagedQueryExtension(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/task/extension/list <br>
     * 查询推广数据
     */
    @PostMapping(value = "/extension/list")
    public Result<?> extensionList(@RequestBody TaskVo entity) {
        List<TaskVo> list = taskService.findByExtension(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/task/extension/save <br>
     * 保存数据-推广(新增|更新)
     */
    @PostMapping(value = "/extension/save")
    public Result<?> extensionSave(@RequestBody TaskVo entity) {
        taskService.extension(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/task/extension/{id} <br>
     * 根据编号删除-推广
     */
    @DeleteMapping(value = "/extension/{id}")
    public Result<?> extensionDelete(@PathVariable("id") String id) {
        Task entity = taskService.getById(id);
        entity.setExtension(YllConstants.ZERO);
        entity.setExtensionOrdinal(null);
        taskService.update(entity);
        return Result.ok();
    }

}
