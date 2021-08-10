package yll.controller;

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
import yll.service.model.vo.*;

import java.util.HashMap;
import java.util.Map;


/**
 * 任务订单管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/task/celebrity")
public class TaskCelebrityRestController {

    // ==============================Fields===========================================
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskCelebrityService taskCelebrityService;
    @Autowired
    private TaskProductsService taskProductsService;
    @Autowired
    private TaskCelebrityProductsService taskCelebrityProductsService;
    @Autowired
    private TaskEnterpriseComplaintService taskEnterpriseComplaintService;
    @Autowired
    private DicService dicService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/task/celebrity/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody TaskCelebrity entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            taskCelebrityService.insert(entity);
        } else {
            taskCelebrityService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [GET] /rest/task/celebrity/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQueryAdminList(Pagination pagination, TaskCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new TaskCelebrityVo());
        Page<TaskCelebrityVo> page = taskCelebrityService.pagedQueryAdminList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [DELETE] /rest/task/celebrity/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        //删除订单相关数据
         taskCelebrityService.deleteByAboutOrder(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/task/celebrity/{id} <br>
     * 根据id查询详情
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
         TaskCelebrity tc = taskCelebrityService.getById(id);
         String tid = tc.getTaskId();
         String cid = tc.getCid();
         TaskCelebrityVo tcv = new TaskCelebrityVo();
         tcv.setCid(cid);
         tcv.setTaskId(tid);
         tcv.setId(id);
         //任务详情
         TaskVo task = taskService.getAppCelebrityTaskDetail(tcv);
        return Result.ok(task);
    }

    /**
     * [GET] /rest/task/celebrity/products/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/products/list")
    public Result<?> pagedQueryProductsList(Pagination pagination, TaskCelebrityProductsVo condition) {
        String tcpId = condition.getTcpId();
        String taskId = condition.getTaskId();
        condition = ObjectUtils.defaultIfNull(condition, new TaskCelebrityProductsVo());
        if(StringUtils.isNotBlank(tcpId)){
            Page<TaskCelebrityProductsVo> page = taskCelebrityProductsService.pagedQueryProductsList(pagination, condition);
            return PageResult.of(page);
        } else if(StringUtils.isNotBlank(taskId)){
            Page<TaskProductsVo> page = taskProductsService.pagedQueryAdminList(pagination, condition);
            return PageResult.of(page);
        }
        return Result.ok();
    }

    /**
     * [GET] /rest/task/celebrity/complaint/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/complaint/list")
    public Result<?> pagedQueryComplaintList(Pagination pagination, TaskEnterpriseComplaintVo condition) {
        String tcId = condition.getTcId();
        condition = ObjectUtils.defaultIfNull(condition, new TaskEnterpriseComplaintVo());
        if(StringUtils.isNotBlank(tcId)){
            return Result.error("缺少参数");
        }
        //申诉记录列表
        Page<TaskEnterpriseComplaintVo> page = taskEnterpriseComplaintService.pagedQueryList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [GET] /rest/task/celebrity/complaint/{id} <br>
     * 根据id查询申诉详情
     */
    @GetMapping(value = "/complaint/{id}")
    public Result<?> getComplaintById(@PathVariable("id") String id) {
        //申诉记录详情
        TaskEnterpriseComplaint tec = taskEnterpriseComplaintService.getById(id);
        String tcId = tec.getTcId();
        String tid = tec.getTid();
        //企业主信息
        Task task = new Task();
        task.setId(tid);
        task = taskService.getById(tid);
        Enterprise enterprise = new Enterprise();
        enterprise.setCreator(task.getCreator());
        enterprise = enterpriseService.getByCondition(enterprise);
        String enterpriseName = enterprise.getEnterpriseName();
        Customer eCust = customerService.getById(task.getCreator());
        String ePhone = eCust.getPhone();
        //达人
        TaskCelebrity tc = taskCelebrityService.getById(tcId);
        InternetCelebrity internetCelebrity = new InternetCelebrity();
        internetCelebrity.setCreator(tc.getCid());
        internetCelebrity = internetCelebrityService.getByCondition(internetCelebrity);
        String realName = internetCelebrity.getRealName();
        Customer iCust = customerService.getById(tc.getCid());
        String iPhone = iCust.getPhone();
        Map<String, Object> result = new HashMap<>();
        result.put("enterpriseName",enterpriseName);
        result.put("ePhone",ePhone);
        result.put("celebrityName",realName);
        result.put("iPhone",iPhone);
        result.put("complaint",tec);
        return Result.ok(result);
    }


}
