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
import yll.entity.Customer;
import yll.entity.Dic;
import yll.entity.TaskCelebrity;
import yll.entity.TaskProcess;
import yll.service.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityVo;
import yll.service.model.vo.TaskProcessVo;
import yll.service.model.vo.TaskProductsVo;
import yll.service.model.vo.TaskVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * APP任务
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/task")
public class TaskAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommonService commonService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskProductsService taskProductsService;
    @Autowired
    private TaskCelebrityService taskCelebrityService;
    @Autowired
    private TaskProcessService taskProcessService;
    @Autowired
    private DicService dicService;
    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/task/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, TaskVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new TaskVo());
        condition.setEnabled(YllConstants.ONE);
        condition.setState(YllConstants.TWO);   //2-已审核
        Page<TaskVo> page = taskService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/task/myself/list <br>
     * 查询数据列表(当前用户自己的数据)-弃用
     */
    @PostMapping(value = "/myself/list")
    public Result<?> myselfList(Pagination pagination, TaskVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition = ObjectUtils.defaultIfNull(condition, new TaskVo());
        condition.setEnabled(YllConstants.ONE);
        condition.setCreator(customerId);
        Page<TaskVo> page = taskService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/task/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //封装
        TaskVo entity = new TaskVo();
        entity.setId(id);

        //获取详情
        entity = taskService.getAppDetail(entity);

        //商品
        TaskProductsVo tp = new TaskProductsVo();
        tp.setTaskId(id);
        List<TaskProductsVo> productsList = taskProductsService.getList(tp);
        entity.setProductsOut(productsList);
//        Map<String, Object> result = new HashMap<>();
//        result.put("task", entity);
//        result.put("products", productsList);

        return Result.ok(entity);
    }

    /**
     * [POST] /app/task/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(Pagination pagination, TaskVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new TaskVo());
        pagination.setOffset(0);
        pagination.setLimit(4);
        condition.setSlide(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        Page<TaskVo> page = taskService.getAppList(pagination, condition);
        return Result.ok(page.getRecords());
    }

    /**
     * [POST] /app/task/extension/list <br>
     * 查询推广数据
     */
    @PostMapping(value = "/extension/list")
    public Result<?> extensionList(Pagination pagination, TaskVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new TaskVo());
        pagination.setOffset(0);
        pagination.setLimit(4);
        condition.setExtension(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        Page<TaskVo> page = taskService.getAppList(pagination, condition);
        return Result.ok(page.getRecords());
    }


    /**
     * [POST] /app/task/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(TaskVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            condition.setCreator(customerId);
            condition.setCreatedTime(DateUtil.now());
            taskService.insert(condition);
        } else {
            condition.setModifier(customerId);
            condition.setModifiedTime(DateUtil.now());
            taskService.update(condition);
        }
        return Result.ok();
    }


    /**
     * [GET] /app/task/dic <br>
     * 任务筛选
     */
    @GetMapping(value = "/dic")
    public Result<?> dic() {
        List<Dic> attendanceFee = dicService.getAppList("attendanceFee");
        List<Dic> commission = dicService.getAppList("commission");
        List<Dic> platform = dicService.getAppList("platform");
        List<Dic> expertise = dicService.getAppList("expertise");

        Map<String,Object> result = new HashMap<>();
        result.put("attendanceFee", attendanceFee);
        result.put("commission", commission);
        result.put("platform", platform);
        result.put("expertise", expertise);

        return Result.ok(result);
    }


    /**
     * [GET] /app/task/save/dic <br>
     * 新增编辑任务的字典数据
     */
    @GetMapping(value = "/save/dic")
    public Result<?> saveDic() {
        //带货方式
        List<Dic> typeList = dicService.getAppList("bringType");
        //达人领域
        List<Dic> expertiseList = dicService.getAppList("expertise");
        //带货平台
        List<Dic> platformList = dicService.getAppList("platform");
        //所属类目
        List<Dic> categoryList = dicService.getAppList("category");
        //优惠方式
        List<Dic> discountList = dicService.getAppList("discount");

        Map<String,Object> result = new HashMap<>();
        result.put("type", typeList);
        result.put("expertise", expertiseList);
        result.put("platform", platformList);
        result.put("category", categoryList);
        result.put("discount", discountList);

        return Result.ok(result);
    }


    /**
     * [POST] /app/task/celebrity  <br>
     * 查询达人列表
     */
    @PostMapping(value = "/celebrity")
    public Result<?> celebrity(Customer condition) {
        condition.setEnabled(1);
        condition.setDeleted(0);
        condition.setRoleType("2");
        List<Customer> list = customerService.findAllForTask(condition);
        return Result.ok(list);
    }

}
