package yll.app.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.constants.ProjectConstants;
import yll.common.security.app.AppSecuritysUtil;
import yll.component.hesign.entity.HeSignResult;
import yll.component.hesign.util.SignConstants;
import yll.entity.*;
import yll.service.*;
import yll.service.model.vo.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * APP流程操作处理类
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/process")
public class ProcessAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommonService commonService;
    @Autowired
    private DicService dicService;
    @Autowired
    private TaskProcessService taskProcessService;
    @Autowired
    private TaskCelebrityService taskCelebrityService;
    @Autowired
    private TaskCelebrityProductsService taskCelebrityProductsService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private TaskCelebrityContractService taskCelebrityContractService;
    @Autowired
    private TaskCelebrityLiveService taskCelebrityLiveService;
    @Autowired
    private TaskEnterpriseComplaintService taskEnterpriseComplaintService;

    // ==============================Methods==========================================

    /**
     * [POST] /app/process/operation <br>
     * 统一操作入口
     */
    @PostMapping(value = "/operation")
    public Result<?> operation(OperationVo condition) {
        String code = condition.getCode();
        if(StringUtils.isBlank(code)){
            return Result.error("缺少当前操作状态");
        }
        String tid = condition.getTid();
        if(StringUtils.isBlank(tid)){
            return Result.error("缺少任务标识");
        }
        String tcId = condition.getTcId();
        if(StringUtils.isBlank(tcId)){
            //tcId为空时，表示当前操作的是任务流程（a开头的任务编码）
            tcId = tid;
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        try {
            //达人操作订单流程时，身份证必须是已上传状态
            if("b3,b5-2".contains(code)){
                Customer cust = customerService.getById(customerId);
                String roleType = cust.getRoleType();
                if("2".equals(roleType)){  //2-当前操作者角色为达人
                    InternetCelebrity internetCelebrity = new InternetCelebrity();
                    internetCelebrity.setCreator(customerId);
                    internetCelebrity = internetCelebrityService.getByCondition(internetCelebrity);
                    if(null == internetCelebrity || StringUtils.isBlank(internetCelebrity.getIdCard())){
                        return Result.error("请先完善身份证上传");
                    }
                }
            }
            //流程处理
            if(ProjectConstants.TaskLive.C1.equals(code)){
                //签字流程处理
                // 此处只返回前端和签H5签字连接，流程更新在和签回调本地方法(/app/process/sign/{type})后进行
                TaskProcessVo tp = taskProcessService.getTaskProcess(tid, tcId, code);
                HeSignResult heSignResult = taskProcessService.signH5(tp);
                //返回H5签字url给前端
                String url = heSignResult.getUrl();
                return Result.ok(url);
            } else if(ProjectConstants.TaskLive.C3.equals(code)){
                //发货|签收 流程处理
                String cid = operationReceive(code, tid, tcId, customerId);
                //达人签收后，代表C3流程结束，改变状态为c4
                if(customerId.equals(cid)){
                    taskProcessService.processHandle(tid, tcId, ProjectConstants.TaskLive.C4);
                }
            } else{
                //其他状态处理
                taskProcessService.processHandle(tid, tcId, code);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            e.printStackTrace();
            return Result.error(message);
        }
        return Result.ok();
    }


    /**
     * [POST] /app/process/list <br>
     * 流程列表
     */
    @PostMapping(value = "/list")
    public Result<?> list(ProcessVo condition) {
        List<ProcessVo> result = processService.getAppList(condition);
        return Result.ok(result);
    }

    /**
     * [GET] /app/process/sign/{type} <br>
     * 签字回调
     */
    @GetMapping(value = "/sign/{type}")
    public void get(@PathVariable("type") String type, String from, String errno, String projectSn, HttpServletRequest request, HttpServletResponse response) throws IOException {
      if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(from) && StringUtils.isNotBlank(errno) && StringUtils.isNotBlank(projectSn)){
            TaskCelebrityContract tcc = new TaskCelebrityContract();
            tcc.setProjectSn(projectSn);
            tcc = taskCelebrityContractService.findByCondition(tcc);
            if("2".equals(type)){
                //达人签字
                tcc.setCelebrityContract(2);        //1-未签字，2-已签字
            } else if("4".equals(type)){
                //企业主签字
                tcc.setEnterpriseContract(2);
            }
            //修改签字属性
            taskCelebrityContractService.update(tcc);
            Integer celebrityContract = tcc.getCelebrityContract();
            Integer enterpriseContract = tcc.getEnterpriseContract();
            if(celebrityContract == 2 && enterpriseContract == 2){
                //全部签字完毕，更改订单流程状态
                String tcId = tcc.getTcId();
                String taskId = tcc.getTaskId();
                String code = ProjectConstants.TaskLive.C2;
                try {
                    taskProcessService.processHandle(taskId, tcId, code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //重定向
        response.sendRedirect(SignConstants.REDIRECT_URL);

    }

    // ==============================企业主==========================================
    /**
     * [POST] /app/process/enterprise/list <br>
     * 企业主任务列表
     */
    @PostMapping(value = "/enterprise/list")
    public Result<?> enterpriseList(Pagination pagination, TaskVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCreator(customerId);
        //处理流程状态tab
        List<String> tabStateList = getCurrentState(condition.getCurrentState());
        condition.setTabStateList(tabStateList);
        Page<TaskVo> result = taskService.pagedQueryEnterpriseTaskList(pagination, condition);
        return PageResult.of(result);
    }


    /**
     * [POST] /app/process/enterprise/detail   <br>
     * 企业主任务详情
     * part-1.1
     */
    @PostMapping(value = "/enterprise/detail")
    public Result<?> enterpriseTaskDetail(TaskVo condition) {
        String tid = condition.getId();
        if(StringUtils.isBlank(tid)){
            return Result.error("缺少任务标识");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCreator(customerId);
        //任务详情
        TaskVo result = taskService.getAppEnterpriseTaskDetail(condition);
        return Result.ok(result);
    }


    /**
     * [POST] /app/process/enterprise/task/products/list <br>
     * 企业主任务商品列表
     * part-1.2
     */
    @PostMapping(value = "/enterprise/task/products/list")
    public Result<?> enterpriseTaskProductsList(Pagination pagination, TaskProductsVo condition) {
        String taskId = condition.getTaskId();
        if(StringUtils.isBlank(taskId)){
            return Result.error("缺少任务标识");
        }
        //商品
        Page<ProductsVo> result = productsService.getTaskProductList(pagination, condition);
        return PageResult.of(result);
    }


    /**
     * [POST] /app/process/enterprise/task/celebrity/list <br>
     * 企业主任务达人列表
     * part-1.3
     */
    @PostMapping(value = "/enterprise/task/celebrity/list")
    public Result<?> enterpriseTaskProductsList(Pagination pagination, TaskCelebrityVo condition) {
        String taskId = condition.getTaskId();
        if(StringUtils.isBlank(taskId)){
            return Result.error("缺少任务标识");
        }
        //达人列表
        Page<TaskVo> result = taskService.getAppEnterpriseTaskCelebrityList(pagination, condition);
        return PageResult.of(result);
    }


    /**
     * [POST] /app/process/enterprise/task/celebrity/products <br>
     * 企业主任务达人商品列表
     * part-1.4
     */
    @PostMapping(value = "/enterprise/task/celebrity/products")
    public Result<?> enterpriseTaskProductsList(TaskCelebrityProductsVo condition) {
        String taskId = condition.getTaskId();
        String cid = condition.getCid();
        String tcId = condition.getTcId();
        if(StringUtils.isBlank(taskId) || StringUtils.isBlank(cid) || StringUtils.isBlank(tcId)){
            return Result.error("缺少任务或达人或中间标识");
        }
        //任务达人商品顶部信息
        Task t = taskService.getById(taskId);
        Customer cust = customerService.getById(cid);
        TaskVo order = new TaskVo();
        order.setDeposit(t.getDeposit());
        order.setHeadImg(cust.getHeadImg());
        order.setNickname(cust.getNickname());

        //任务达人商品列表
        List<ProductsVo> products = productsService.getAppEnterpriseTaskCelebrityProductsList(condition);
        order.setQuantity(products.size() + "");

        Map<String,Object> result = new HashMap<>();
        result.put("order", order);
        result.put("products", products);
        return Result.ok(result);
    }


    /**
     * [POST] /app/process/enterprise/order/list <br>
     * 企业主任务订单列表
     *
     */
    @PostMapping(value = "/enterprise/order/list")
    public Result<?> enterpriseOrderList(Pagination pagination, TaskVo condition) {
        //所有任务订单混在一起查询
        /*String tid = condition.getId();
        if(StringUtils.isBlank(tid)){
            return Result.error("缺少任务标识");
        }*/
        String customerId = AppSecuritysUtil.getCustomerId();
        //处理流程状态tab
        List<String> tabStateList = getCurrentState(condition.getCurrentState());
        condition.setTabStateList(tabStateList);
        condition.setCreator(customerId);
        Page<TaskVo> result = taskService.pagedQueryEnterpriseOrderList(pagination, condition);
        return PageResult.of(result);
    }


    /**
     * [POST] /app/process/enterprise/order/detail   <br>
     * 企业主任务订单详情
     * part-2.1
     */
    @PostMapping(value = "/enterprise/order/detail")
    public Result<?> enterpriseOrderDetail(TaskVo condition) {
        return  celebrityTaskDetail(condition);
    }


    /**
     * [POST] /app/process/enterprise/order/processes   <br>
     * 企业主任务订单流程集合
     * part-2.2
     */
    @PostMapping(value = "/enterprise/order/processes")
    public Result<?> enterpriseProcesses(TaskVo condition) {
        return celebrityProcesses(condition);
    }


    /**
     * [POST] /app/process/enterprise/order/products   <br>
     * 企业主任务订单商品列表
     * part-2.3
     */
    @PostMapping(value = "/enterprise/order/products")
    public Result<?> enterpriseProducts(Pagination pagination, TaskVo condition) {
        return celebrityProducts(pagination, condition);
    }


    /**
     * [POST] /app/process/enterprise/complaint   <br>
     * 任务企业主申诉
     */
    @PostMapping(value = "/enterprise/complaint")
    public Result<?> enterpriseComplaint(TaskEnterpriseComplaint condition) {
        String tid = condition.getTid();
        String tcId = condition.getTcId();
        String image = condition.getImage();
        String reason = condition.getReason();
        if(StringUtils.isBlank(tid) || StringUtils.isBlank(tcId) || StringUtils.isBlank(image) ||
                StringUtils.isBlank(reason)){
            return Result.error("缺少参数");
        }
        taskEnterpriseComplaintService.insert(condition);
        try {
            //流程处理
            taskProcessService.processHandle(tid, tcId, ProjectConstants.TaskOrder.B6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok();
    }

    // ==============================达人==========================================
    /**
     * [POST] /app/process/celebrity/list <br>
     * 达人任务订单列表
     */
    @PostMapping(value = "/celebrity/list")
    public Result<?> celebrityList(Pagination pagination, TaskVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCid(customerId);
        //处理流程状态tab
        List<String> tabStateList = getCurrentState(condition.getCurrentState());
        condition.setTabStateList(tabStateList);
        Page<TaskVo> result = taskService.pagedQueryCelebrityTaskList(pagination, condition);   //当mcnid为空时，达人才可点击操作
        return PageResult.of(result);
    }


    /**
     * [POST] /app/process/celebrity/detail   <br>
     * 达人任务订单详情
     * part-1.1
     */
    @PostMapping(value = "/celebrity/detail")
    public Result<?> celebrityTaskDetail(TaskVo condition) {
        String tcId = condition.getTcId();
        if(StringUtils.isBlank(tcId)){
            return Result.error("缺少任务达人标识");
        }
        Map<String,Object> result = new HashMap<>();
        TaskCelebrity tc = taskCelebrityService.getById(tcId);
        String tid = tc.getTaskId();
        String cid = tc.getCid();
        TaskCelebrityVo tcv = new TaskCelebrityVo();
        tcv.setCid(cid);
        tcv.setTaskId(tid);
        tcv.setId(tcId);
        //任务详情
        TaskVo task = taskService.getAppCelebrityTaskDetail(tcv);
        //接单人--达人订单详情使用
        Customer c = customerService.getById(cid);
        Customer cust = new Customer();
        cust.setId(cid);
        cust.setHeadImg(c.getHeadImg());
        cust.setNickname(c.getNickname());
        //C1时返回签约状态
        String currentState = tc.getCurrentState();
        if(ProjectConstants.TaskLive.C1.equals(currentState)){
            TaskCelebrityContractVo tccVo = new TaskCelebrityContractVo();
            tccVo.setTcId(tcId);
            TaskCelebrityContract tcc = taskCelebrityContractService.findByCondition(tccVo);
            Integer celebrityContract = tcc.getCelebrityContract();
            Integer enterpriseContract = tcc.getEnterpriseContract();
            String customerId = AppSecuritysUtil.getCustomerId();
            Customer customer = customerService.getById(customerId);
            String roleType = customer.getRoleType();
            Map<String,Object> contract = new HashMap<>();
            contract.put("roleType", roleType);     //2-达人，3-MCN，4-企业主
            contract.put("celebrityContract", celebrityContract);
            contract.put("enterpriseContract", enterpriseContract);
            result.put("contract", contract);
        }
        //发货 收货状态返回
        if(ProjectConstants.TaskLive.C3.equals(currentState)){
            TaskCelebrityProducts tcp = new TaskCelebrityProducts();
            tcp.setTcId(tcId);
            List<TaskCelebrityProducts> tcpList = taskCelebrityProductsService.findBy(tcp);
            if(tcpList.size() < 1){
                return Result.error("当前订单下没有商品");
            }
            tcp = tcpList.get(0);
            Integer receive = tcp.getReceive();     //0-未发货，1-运送中，2-已签收
            result.put("receive", receive);
        }
        result.put("task", task);
        result.put("celebrity", cust);
        return Result.ok(result);
    }


    /**
     * [POST] /app/process/celebrity/processes   <br>
     * 达人任务订单流程集合
     * part-1.2
     */
    @PostMapping(value = "/celebrity/processes")
    public Result<?> celebrityProcesses(TaskVo condition) {
        String tcId = condition.getTcId();
        if(StringUtils.isBlank(tcId)){
            return Result.error("缺少任务达人标识");
        }
        //当前流程
        TaskCelebrity tc = taskCelebrityService.getById(tcId);
        String currentState = tc.getCurrentState();
        if(StringUtils.isBlank(currentState) || !currentState.contains("c")){
            //为空串时，表示订单为非执行状态
            currentState = "";
        }
        //流程集合
        ProcessVo pv = new ProcessVo();
        pv.setType("process-task-live");
        List<ProcessVo> processList = processService.getAppList(pv);

        Map<String,Object> result = new HashMap<>();
        result.put("currentState", currentState);
        result.put("processList", processList);
        return Result.ok(result);
    }


    /**
     * [POST] /app/process/celebrity/products   <br>
     * 达人任务订单商品列表
     * part-1.3
     */
    @PostMapping(value = "/celebrity/products")
    public Result<?> celebrityProducts(Pagination pagination, TaskVo condition) {
        String tcId = condition.getTcId();
        if(StringUtils.isBlank(tcId)){
            return Result.error("缺少任务达人标识");
        }
        TaskCelebrity tc = taskCelebrityService.getById(tcId);
        String tid = tc.getTaskId();
        String cid = tc.getCid();

        TaskCelebrityProductsVo tcpv = new TaskCelebrityProductsVo();
        tcpv.setTaskId(tid);
        tcpv.setCid(cid);
        //任务达人商品列表
        Page<ProductsVo> result = productsService.getAppTaskCelebrityProducts(pagination, tcpv);
        return PageResult.of(result);
    }


    /**
     * [POST] /app/process/celebrity/receive   <br>
     * 达人任务订单商品签收
     */
    @PostMapping(value = "/celebrity/receive")
    public Result<?> celebrityReceive(TaskVo condition) {
        String tcpIdsStr = condition.getProductsIn();
        if(StringUtils.isBlank(tcpIdsStr)){
            return Result.error("缺少任务达人商品标识");
        }
        String cid = "";
        String taskId = "";
        //商品签收
        String[] tcpIds = tcpIdsStr.split(",");
        for (String tcpId :
                tcpIds) {
            TaskCelebrityProducts tcp = taskCelebrityProductsService.getById(tcpId);
            cid = tcp.getCid();
            taskId = tcp.getTaskId();
            if(null != tcp &&  2 != tcp.getReceive()){
                tcp.setReceive(2);  //1-运送中，2-已签收
                taskCelebrityProductsService.update(tcp);
            }
        }
        //检查签收状态，若全签收后，进入下一流程状态
        TaskCelebrityProductsVo tcpVo = new TaskCelebrityProductsVo();
        tcpVo.setCid(cid);
        tcpVo.setTaskId(taskId);
        tcpVo.setReceive(1);
        List<TaskCelebrityProducts> list = taskCelebrityProductsService.findBy(tcpVo);
        if(list.size() < 1){
            //全部签收
            TaskCelebrityVo tcVo = new TaskCelebrityVo();
            tcVo.setTaskId(taskId);
            tcVo.setCid(cid);
            TaskCelebrity tc = taskCelebrityService.findByCondition(tcVo);
            if(null != tc){
                //修改订单流程
                try {
                    taskProcessService.processHandle(taskId, tc.getId(), ProjectConstants.TaskLive.C4);
                } catch (Exception e) {
                    e.printStackTrace();
                    return Result.error(e.getMessage());
                }
            }
        }
        return Result.ok();
    }

    /**
     * [POST] /app/process/celebrity/order/save   <br>
     * 达人任务订单申请
     */
    @PostMapping(value = "/celebrity/order/save")
    public Result<?> orderSave(TaskVo condition) {
        String taskId = condition.getId();
        if(StringUtils.isBlank(taskId)){
            return Result.error("缺少任务标识");
        }
        String productsIn = condition.getProductsIn();
        if(StringUtils.isBlank(productsIn)){
            return Result.error("缺少商品标识");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCid(customerId);
        InternetCelebrity internetCelebrity = new InternetCelebrity();
        internetCelebrity.setCreator(customerId);
        internetCelebrity = internetCelebrityService.getByCondition(internetCelebrity);
        if(null == internetCelebrity || StringUtils.isBlank(internetCelebrity.getIdCard())){
            return Result.error("请先完善身份证上传");
        }

        //每个达人当前同一任务完成前，只可发起一次申请
        TaskCelebrityVo taskCelebrity = new TaskCelebrityVo();
        taskCelebrity.setTaskId(taskId);
        taskCelebrity.setCid(customerId);
        List<TaskCelebrity> tcList = taskCelebrityService.findBy(taskCelebrity);
        Boolean isApply = true;
        for (TaskCelebrity vo :
                tcList) {
            String currentState = vo.getCurrentState();
            if(StringUtils.isBlank(currentState) || currentState.indexOf("b5") == -1){
                isApply = false;
                break;
            }
        }
        if(isApply){
            //申请任务订单
            TaskCelebrity tc = taskService.taskApply(condition);
            return Result.ok();
        }
        return Result.error("当前任务存在未完成订单，请完成后再进行申请");
    }

    /**
     * [POST] /app/process/celebrity/complete/list   <br>
     * 达人任务订单提交结案列表
     */
    @PostMapping(value = "/celebrity/complete/list")
    public Result<?> celebrityCompleteList(TaskCelebrityLiveVo condition) {
        String tcId = condition.getTcId();
        if(StringUtils.isBlank(tcId)){
            return Result.error("缺少参数");
        }
        //结案记录列表
        List<TaskCelebrityLiveVo> result = taskCelebrityLiveService.getAppList(condition);
        return Result.ok(result);
    }

    /**
     * [POST] /app/process/celebrity/complete/detail   <br>
     * 达人任务订单提交结案详情
     */
    @PostMapping(value = "/celebrity/complete/detail")
    public Result<?> celebrityCompleteDetail(TaskCelebrityLiveVo condition) {
        String id = condition.getId();
        if(StringUtils.isBlank(id)){
            return Result.error("缺少参数");
        }
        //结案记录列表
        List<TaskCelebrityLiveVo> list = taskCelebrityLiveService.getAppList(condition);
        TaskCelebrityLiveVo result = new TaskCelebrityLiveVo();
        if(list.size() == 1){
            result = list.get(0);
        }
        return Result.ok(result);
    }

    /**
     * [POST] /app/process/celebrity/complete   <br>
     * 达人任务订单提交结案
     */
    @PostMapping(value = "/celebrity/complete")
    public Result<?> celebrityComplete(TaskCelebrityLiveVo condition) {
        String tcId = condition.getTcId();
        String productsIn = condition.getProductsIn();
        String image = condition.getImage();
        String sessions = condition.getSessions();
        Integer state = condition.getState();
        Date completeCime = condition.getCompleteTime();
        if(StringUtils.isBlank(tcId) || StringUtils.isBlank(productsIn) || StringUtils.isBlank(image)
                || StringUtils.isBlank(sessions) || null == completeCime|| null == state){
            return Result.error("缺少参数");
        }
        condition.setPids(productsIn);
        //新增结案记录
        TaskCelebrity tc = taskCelebrityService.getById(tcId);
        String taskId = tc.getTaskId();
        condition.setTid(taskId);
        taskCelebrityLiveService.insert(condition);
        if(2 == state){
            //提交结案流程处理    state为2时修改状态为B4，为1时只记录数据
            try {
                taskProcessService.processHandle(taskId, tcId, ProjectConstants.TaskOrder.B4);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return Result.ok();
    }


    /**
     * [POST] /app/process/celebrity/complete/products   <br>
     * 获取提交结案的全部商品
     */
    @PostMapping(value = "/celebrity/complete/products")
    public Result<?> celebrityCompleteProducts(TaskCelebrityProductsVo condition) {
        String tcId = condition.getTcId();
        if(StringUtils.isBlank(tcId)){
            return Result.error("缺少参数");
        }
        List<TaskCelebrityProductsVo> productsList = taskCelebrityProductsService.getAppProductsList(condition);
        return Result.ok(productsList);
    }

    // ==============================MCN==========================================
    /**
     * [POST] /app/process/mcn/list <br>
     * MCN的达人任务订单列表
     */
    @PostMapping(value = "/mcn/list")
    public Result<?> celebrityListMcn(Pagination pagination, TaskVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setMcnId(customerId);
        Page<TaskVo> result = taskService.pagedQueryCelebrityTaskList(pagination, condition);
        return PageResult.of(result);
    }


    // ==============================ToolMethods======================================

    /**
     * 发货签收处理
     * @param code
     * @param tid
     * @param tcId
     * @param customerId
     * @return
     */
    private String operationReceive(String code, String tid, String tcId, String customerId) {
        TaskProcessVo tp = taskProcessService.getTaskProcess(tid, tcId, code);
        Task task = taskService.getById(tid);
        String eid = task.getCreator();
        TaskCelebrity tc = taskCelebrityService.getById(tcId);
        String cid = tc.getCid();
        Integer receive = 0;
        if(customerId.equals(eid)){
            //企业主发货
            receive = 1;
        } else if(customerId.equals(cid)){
            receive = 2;
        }
        //修改订单商品状态
        TaskCelebrityProducts tcp = new TaskCelebrityProducts();
        tcp.setTcId(tcId);
        List<TaskCelebrityProducts> tcpList = taskCelebrityProductsService.findBy(tcp);
        for (TaskCelebrityProducts tcpvo :
                tcpList) {
            tcpvo.setReceive(receive);
            taskCelebrityProductsService.update(tcpvo);
        }
        return cid;
    }


    private List<String> getCurrentState(String state){
        List<String> result = new ArrayList<>();
        if(StringUtils.isNotBlank(state)){
            if(ProjectConstants.Task.A1.equals(state)){
                result.add(ProjectConstants.Task.A1);
            } else if(ProjectConstants.Task.A2.equals(state)){
                result.add(ProjectConstants.Task.A2);
            } else if("a3".equals(state)){
                result.add(ProjectConstants.Task.A3_1);
                result.add(ProjectConstants.Task.A3_2);
                result.add(ProjectConstants.Task.A3_3);
            } else if("b1".equals(state)){
                result.add(ProjectConstants.TaskOrder.B1_1);
                result.add(ProjectConstants.TaskOrder.B1_2);
            } else if("b2".equals(state)){
                result.add(ProjectConstants.TaskOrder.B2_1);
                result.add(ProjectConstants.TaskOrder.B2_2);
                result.add(ProjectConstants.TaskOrder.B2_3);
            }  else if("b3".equals(state)){
                result.add(ProjectConstants.TaskOrder.B3);
                result.add(ProjectConstants.TaskLive.C1);
                result.add(ProjectConstants.TaskLive.C2);
                result.add(ProjectConstants.TaskLive.C3);
                result.add(ProjectConstants.TaskLive.C4);
                result.add(ProjectConstants.TaskLive.C5);
                result.add(ProjectConstants.TaskLive.C6);
            }  else if("b4".equals(state)){
                result.add(ProjectConstants.TaskOrder.B4);
            }   else if("b5".equals(state)){
                result.add(ProjectConstants.TaskOrder.B5_1);
                result.add(ProjectConstants.TaskOrder.B5_2);
                result.add(ProjectConstants.TaskOrder.B5_3);
                result.add(ProjectConstants.TaskOrder.B5_4);
                result.add(ProjectConstants.TaskOrder.B5_5);
            }
            return result;
        } else{
            return null;
        }
    }


    //TODO  1、搞任务、订单 4个列表页及详情页  ok
    //TODO  2、后台任务审核通过处 开始测试   ok
    //TODO  3、继续对照流程图，搞b1-1分支流程各操作业物逻辑。如果有选品中心首页效果图，搞选品中心首页
    //TODO  4、第三方签约集成  ok
    //TODO  5、H5签字 main方法测试回调，记得注释修改签字状态的if判断  ok
    //TODO  6、H5签字 测试整体 ok
    //TODO  7、达人、企业主 点发货或收货，该订单的dh_task_celebrity_products所有记录receive状态进行统一更改  ok
    //TODO  8、达人、企业主 详情页（订单/app/process/celebrity/detail接口）返回时，收货状态返回给app用于区分   ok
    //TODO  9、新建提交结案、申诉表，搞提交结案、申诉 接口、后台模块     ok
    //TODO  10、达人身份证，商家信用代码 接口   ok
    //TODO  11、达人接任务时，认证身份证是否填写。ok
    //TODO 12、继续搞申诉新增接口ok
    //TODO  13、测试后台任务订单列表 或 任务商品列表 ok
    //TODO  14、任务订单申诉表。 ok



}
