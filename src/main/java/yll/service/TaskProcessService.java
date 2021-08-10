package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.constants.ProjectConstants;
import yll.common.identifier.IdHelper;
import yll.common.security.app.AppSecuritysUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.component.hesign.entity.HeSignAuth;
import yll.component.hesign.entity.HeSignBase;
import yll.component.hesign.entity.HeSignFiles;
import yll.component.hesign.entity.HeSignResult;
import yll.component.hesign.using.He51Upload;
import yll.component.hesign.using.He54SignH5;
import yll.component.hesign.util.SignConstants;
import yll.entity.*;
import yll.entity.Process;
import yll.mapper.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskProcessVo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * 任务达人订单流程
 */
@Transactional
@Service
public class TaskProcessService {

    // ==============================Fields===========================================
    @Autowired
    private TaskProcessMapper taskProcessMapper;
    @Autowired
    private ProcessMapper processMapper;
   @Autowired
    private CommentMapper commentMapper;
   @Autowired
    private TaskMapper taskMapper;
   @Autowired
    private TaskCelebrityMapper taskCelebrityMapper;
   @Autowired
    private InternetCelebrityMapper internetCelebrityMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private McnMapper mcnMapper;
    @Autowired
    private TaskCelebrityContractMapper taskCelebrityContractMapper;

    @Autowired
    private Securitys securitys;
    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(TaskProcess vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskProcess entity = new TaskProcess();
        IdHelper.setIfEmptyId(entity);

        entity.setTid(vo.getTid());
        entity.setTcId(vo.getTcId());
        entity.setProcess(vo.getProcess());
        entity.setType(vo.getType());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        taskProcessMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        taskProcessMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(TaskProcess vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskProcess entity = taskProcessMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTid(vo.getTid());
        entity.setTcId(vo.getTcId());
        entity.setProcess(vo.getProcess());
        entity.setType(vo.getType());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        taskProcessMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public TaskProcess getById(String id) {
        TaskProcess entity = taskProcessMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        TaskProcess entity = new TaskProcess();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskProcessMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public TaskProcess findByCondition(TaskProcessVo condition) {
        return taskProcessMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskProcess> pagedQuery(Pagination pagination, TaskProcess condition) {
        return MybatisHelper.selectPage(pagination, () -> taskProcessMapper.findBy(condition));
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskProcess> findBy(TaskProcessVo condition) {
        return taskProcessMapper.findBy(condition);
    }



    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskProcessVo> getList(TaskProcessVo condition) {
        return taskProcessMapper.getList(condition);
    }

    /**
     * 获取流程信息
     * @param p 查询条件
     */
    public Process getProcess(Process p) {
        if(null == p){
            p = new Process();
            p.setCode("a1");
        }
        p = processMapper.findByCondition(p);
        return p;
    }

    /**
     * 处理流程业务逻辑包装方法
     * @param tid 任务id
     * @param tcId 任务达人中间表id
     * @param processCodeCurrent 当前操作状态
     */
    public void processHandle(String tid, String tcId, String processCodeCurrent) throws Exception {
        Process p = new Process();
        p.setCode(processCodeCurrent);
        p = getProcess(p);
        if(null != p){
            TaskProcessVo condition = new TaskProcessVo();
            //流程编码存在，可往下处理流程业务逻辑
            condition.setTid(tid);
            condition.setTcId(tcId);
            condition.setType(p.getType());
            condition.setProcessCodeCurrent(processCodeCurrent);
            processHandle(condition);
        }
    }

    /**
     * 处理流程业务逻辑包装方法-不调用流程修改
     * @param tid 任务id
     * @param tcId 任务达人中间表id
     * @param processCodeCurrent 当前操作状态
     */
    public TaskProcessVo getTaskProcess(String tid, String tcId, String processCodeCurrent) {
        Process p = new Process();
        p.setCode(processCodeCurrent);
        p = getProcess(p);
        TaskProcessVo condition = new TaskProcessVo();
        if(null != p){
            //流程编码存在，可往下处理流程业务逻辑
            condition.setTid(tid);
            condition.setTcId(tcId);
            condition.setType(p.getType());
            condition.setProcessCodeCurrent(processCodeCurrent);
        }
        return condition;
    }


    /** TODO 从a2开始，继续写个流程业务处理逻辑
     * 处理流程业务逻辑，处理完毕后 ，讲对应订单状态修改至当前最新状态
     * @param condition 查询条件
     */
    public void processHandle(TaskProcessVo condition) throws Exception {
        String processCodeCurrent = condition.getProcessCodeCurrent();
        //初始化创建信息
        condition.setProcess(processCodeCurrent);
        initCreator(condition);
        //新增流程记录
        insert(condition);

        //更新任务流程最新状态
        if("a1,a2,a3-1,a3-2,a3-3,a3-4".contains(processCodeCurrent)){
            //task总表
            Task task = taskMapper.getById(condition.getTid());
            task.setCurrentState(processCodeCurrent);
            taskMapper.update(task);
        } else {
            //tc表
            TaskCelebrity taskCelebrity = taskCelebrityMapper.getById(condition.getTcId());
            taskCelebrity.setCurrentState(processCodeCurrent);
            taskCelebrityMapper.update(taskCelebrity);
        }

        //处理各流程业务逻辑
        if(ProjectConstants.Task.A1.equals(processCodeCurrent)){
            //客服审核
        }
        if(ProjectConstants.Task.A2.equals(processCodeCurrent)){
            //任务公布执行
            //任务达人表状态更新-（企业主|MCN创建任务时指定的达人）
            TaskCelebrity tc = new TaskCelebrity();
            tc.setTaskId(condition.getTid());
            List<TaskCelebrity> list = taskCelebrityMapper.findBy(tc);
            TaskProcessVo tpvo = new TaskProcessVo();
            tpvo.setTid(condition.getTid());
            tpvo.setProcessCodeCurrent(ProjectConstants.TaskOrder.B1_2);
            tpvo.setType("process-task-order"); //任务订单流程标识
            for (TaskCelebrity tcvo :
                    list) {
                tpvo.setTcId(tcvo.getId());
                processHandle(tpvo);
                tcvo.setCurrentState(ProjectConstants.TaskOrder.B1_2);
                taskCelebrityMapper.update(tcvo);
            }
        }
        if(ProjectConstants.Task.A3_1.equals(processCodeCurrent)){
            //任务超时结束
        }
        if(ProjectConstants.Task.A3_2.equals(processCodeCurrent)){
            //任务订单结束
        }
        if(ProjectConstants.Task.A3_3.equals(processCodeCurrent)){
            //审核失败
        }
        if(ProjectConstants.Task.A3_4.equals(processCodeCurrent)){
            //任务取消（企业主|MCN操作）
        }
        if(ProjectConstants.TaskOrder.B1_1.equals(processCodeCurrent)){
            //主播接任务
            String customerId = AppSecuritysUtil.getCustomerId();
            InternetCelebrity ic = new InternetCelebrity();
            ic.setCreator(customerId);
            ic = internetCelebrityMapper.findByCondition(ic);
            if (null != ic) {
                String mcnId = ic.getMcnId();
                //有MCN，状态转b2-1。否则转b2-2
                TaskProcessVo tpvo = new TaskProcessVo();
                if (StringUtils.isNotBlank(mcnId)) {
                    tpvo = getTaskProcessVo(condition, ProjectConstants.TaskOrder.B2_1);
                } else {
                    tpvo = getTaskProcessVo(condition, ProjectConstants.TaskOrder.B2_2);
                }
                processHandle(tpvo);
            }
        }
        if(ProjectConstants.TaskOrder.B2_1.equals(processCodeCurrent)){
            //MCN确认
        }
        if(ProjectConstants.TaskOrder.B2_2.equals(processCodeCurrent)){
            //企业主|MCN审核
        }
        if(ProjectConstants.TaskOrder.B5_1.equals(processCodeCurrent)){
            //订单取消结束
        }
        if(ProjectConstants.TaskOrder.B1_2.equals(processCodeCurrent)){
            //主播待接单
            String customerId = AppSecuritysUtil.getCustomerId();
            InternetCelebrity ic = new InternetCelebrity();
            ic.setCreator(customerId);
            ic = internetCelebrityMapper.findByCondition(ic);
            if (null != ic) {
                String mcnId = ic.getMcnId();
                //有MCN，状态转b2-3。否则等达人确认后再转B3操作
                TaskProcessVo tpvo = new TaskProcessVo();
                if (StringUtils.isNotBlank(mcnId)) {
                    tpvo = getTaskProcessVo(condition, ProjectConstants.TaskOrder.B2_3);
                    processHandle(tpvo);
                }
            }
        }
        if(ProjectConstants.TaskOrder.B5_2.equals(processCodeCurrent)){
            //订单取消结束
        }
        if(ProjectConstants.TaskOrder.B2_3.equals(processCodeCurrent)){
            //MCN确认
        }
        if(ProjectConstants.TaskOrder.B3.equals(processCodeCurrent)){
            //直播带货
            //1、创建合同
            uploadContract(condition);
            //2、流程状态记录
            TaskProcessVo tpvo = getTaskProcessVo(condition, ProjectConstants.TaskLive.C1);
            processHandle(tpvo);
        }
        if(ProjectConstants.TaskOrder.B5_3.equals(processCodeCurrent)){
            //超时未提交
        }
        if(ProjectConstants.TaskOrder.B4.equals(processCodeCurrent)){
            //主播提交订单
        }
        if(ProjectConstants.TaskOrder.B5_4.equals(processCodeCurrent)){
            //订单完成
        }
        if(ProjectConstants.TaskOrder.B6.equals(processCodeCurrent)){
            //企业主|MCN申诉
        }
        if(ProjectConstants.TaskOrder.B5_5.equals(processCodeCurrent)){
            //订单取消
        }
        if(ProjectConstants.TaskLive.C1.equals(processCodeCurrent)){
            //签合同（电子签约）
//            signH5(condition);
        }
        if(ProjectConstants.TaskLive.C2.equals(processCodeCurrent)){
            //支付  未接入-暂时跳过
            TaskProcessVo tpvo = getTaskProcessVo(condition, ProjectConstants.TaskLive.C3);
            processHandle(tpvo);
        }
        if(ProjectConstants.TaskLive.C3.equals(processCodeCurrent)){
            //发样品  TODO 企业主|MCN点样品发货

        }
        if(ProjectConstants.TaskLive.C4.equals(processCodeCurrent)){
            //主播试用
        }
        if(ProjectConstants.TaskLive.C5.equals(processCodeCurrent)){
            //脚本编写
        }
        if(ProjectConstants.TaskLive.C6.equals(processCodeCurrent)){
            //安排直播
        }

    }


    /**
     * 签电子合同
     * @param condition
     * @throws Exception
     */
    public HeSignResult signH5(TaskProcessVo condition) throws Exception {
        //1、获取合同信息
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer cust = customerMapper.getById(customerId);
        TaskCelebrityContract tcc = new TaskCelebrityContract();
        tcc.setTcId(condition.getTcId());
        tcc = taskCelebrityContractMapper.findByCondition(tcc);
        //2、封装签字信息
        HeSignBase heSignBase = new HeSignBase();
        HeSignFiles heSignFiles = new HeSignFiles();
        HeSignAuth heSignAuth = new HeSignAuth();
        heSignBase.setProjectSn(tcc.getProjectSn());
        heSignBase.setVersion(SignConstants.VERSION);
        heSignFiles.setFileSn(tcc.getFileSn());
        if(customerId.equals(tcc.getCid())){
            //达人
            InternetCelebrity ic = new InternetCelebrity();
            ic.setCreator(customerId);
            ic = internetCelebrityMapper.findByCondition(ic);
            heSignAuth.setIdCardType(SignConstants.ID_CARD_TYPE_CELEBRITY);
            heSignAuth.setIdCardNo(ic.getIdCard());
            heSignAuth.setMobile(cust.getPhone());
            heSignAuth.setName(ic.getRealName());
            heSignAuth.setMethod(SignConstants.METHOD_ALL);
            heSignAuth.setType("2");    //2-达人
        } else if(customerId.equals(tcc.getEid())){
            //获取角色类型
            if("3".equals(cust.getRoleType())){
                //MCN
                Mcn m = new Mcn();
                m.setCreator(customerId);
                m = mcnMapper.findByCondition(m);
                heSignAuth.setIdCardType(SignConstants.ID_CARD_TYPE_ENTERPRISE);
                heSignAuth.setIdCardNo(m.getCreditCode());
                heSignAuth.setMobile(cust.getPhone());
                heSignAuth.setName(m.getMcnName());
                heSignAuth.setMethod(SignConstants.METHOD_SMS);
                heSignAuth.setType("3");    //3-MCN
            } else if("4".equals(cust.getRoleType())){
                //企业主
                Enterprise e = new Enterprise();
                e.setCreator(customerId);
                e = enterpriseMapper.findByCondition(e);
                heSignAuth.setIdCardType(SignConstants.ID_CARD_TYPE_ENTERPRISE);
                heSignAuth.setIdCardNo(e.getCreditCode());
                heSignAuth.setMobile(cust.getPhone());
                heSignAuth.setName(e.getEnterpriseName());
                heSignAuth.setMethod(SignConstants.METHOD_SMS);
                heSignAuth.setType("4");    //4-企业主
            }
        }
        HeSignResult heSignResult = He54SignH5.signH5(heSignBase, heSignFiles, heSignAuth);
        return heSignResult;
    }


    /**
     * 主播待接单处理
     * @param condition
     */
    private void celebrityB1(TaskProcessVo condition) throws Exception {
        String customerId = AppSecuritysUtil.getCustomerId();
        InternetCelebrity ic = new InternetCelebrity();
        ic.setCreator(customerId);
        ic = internetCelebrityMapper.findByCondition(ic);
        if (null != ic) {
            String mcnId = ic.getMcnId();
            //有MCN，状态转b2-1。否则转b2-2
            TaskProcessVo tpvo = new TaskProcessVo();
            if (StringUtils.isNotBlank(mcnId)) {
                tpvo = getTaskProcessVo(condition, ProjectConstants.TaskOrder.B2_1);
            } else {
                tpvo = getTaskProcessVo(condition, ProjectConstants.TaskOrder.B2_2);
            }
            processHandle(tpvo);
        }
    }


    /**
     * 封装任务流程实体
     * @param condition
     * @param codeCurrent
     * @return
     */
    private TaskProcessVo getTaskProcessVo(TaskProcessVo condition, String codeCurrent) {
        TaskProcessVo tpvo = new TaskProcessVo();
        tpvo.setTid(condition.getTid());
        tpvo.setTcId(condition.getTcId());
        tpvo.setType(condition.getType());
        tpvo.setProcessCodeCurrent(codeCurrent);
        return tpvo;
    }


    /**
     * 上传创建合同
     * @param condition
     */
    private void uploadContract(TaskProcessVo condition) throws Exception {
        //创建合同
        HeSignBase heSignBase = new HeSignBase();
        HeSignFiles heSignFiles = new HeSignFiles();
        List< HeSignAuth > heSignAuthList= new ArrayList<>();
        String contractNo = SignConstants.PREFIX_CONSTANTS + condition.getTcId();
        Task task = taskMapper.getById(condition.getTid());
        String eid = task.getCreator();
        String name = "";
        String creditCode = "";
        String ePhone = "";
        //获取角色类型
        Customer cust = customerMapper.getById(eid);
        if("3".equals(cust.getRoleType())){
            //MCN
            Mcn m = new Mcn();
            m.setCreator(eid);
            m = mcnMapper.findByCondition(m);
            name = m.getMcnName();
            creditCode = m.getCreditCode();
        } else if("4".equals(cust.getRoleType())){
            //企业主
            Enterprise enterprise = new Enterprise();
            enterprise.setCreator(eid);
            enterprise = enterpriseMapper.findByCondition(enterprise);
            name = enterprise.getEnterpriseName();
            creditCode = enterprise.getCreditCode();
        }
        Customer ec = customerMapper.getById(eid);
        ePhone = ec.getPhone();
        //达人
        TaskCelebrity taskCelebrity = taskCelebrityMapper.getById(condition.getTcId());
        InternetCelebrity internetCelebrity = new InternetCelebrity();
        internetCelebrity.setCreator(taskCelebrity.getCid());
        internetCelebrity = internetCelebrityMapper.findByCondition(internetCelebrity);
        Customer ic = customerMapper.getById(taskCelebrity.getCid());
        String realName = internetCelebrity.getRealName();
        String idCard = internetCelebrity.getIdCard();
        String iPhone = ic.getPhone();
        //封装参数
        heSignBase.setVersion(SignConstants.VERSION);
        heSignBase.setProjectSn(contractNo);
        heSignBase.setProjectName(contractNo);
        heSignFiles.setFileSn(contractNo);
        heSignFiles.setFilesName(contractNo);
        heSignFiles.setFilesFileName(contractNo + ".pdf");
        //签署人-企业主|MCN
        HeSignAuth eHeSignAuth = new HeSignAuth();
        eHeSignAuth.setPosition(SignConstants.POSITION_ONE);
        eHeSignAuth.setIdCardType(SignConstants.ID_CARD_TYPE_ENTERPRISE);
        eHeSignAuth.setIdCardNo(creditCode);
        eHeSignAuth.setMobile(ePhone);
        eHeSignAuth.setName(name);
        eHeSignAuth.setMethod(SignConstants.METHOD_HANDWRITE);
        eHeSignAuth.setAuto(SignConstants.AUTO);
        eHeSignAuth.setReason("");
        //签署人-达人
        HeSignAuth iHeSignAuth = new HeSignAuth();
        iHeSignAuth.setPosition(SignConstants.POSITION_TWO);
        iHeSignAuth.setIdCardType(SignConstants.ID_CARD_TYPE_CELEBRITY);
        iHeSignAuth.setIdCardNo(idCard);
        iHeSignAuth.setMobile(iPhone);
        iHeSignAuth.setName(realName);
        iHeSignAuth.setMethod(SignConstants.METHOD_ALL);
        iHeSignAuth.setAuto(SignConstants.AUTO);
        iHeSignAuth.setReason("");

        heSignAuthList.add(eHeSignAuth);
        heSignAuthList.add(iHeSignAuth);
        He51Upload.uploadContract(heSignBase, heSignFiles, heSignAuthList);
        //合同信息存储数据库
        saveContract(condition, contractNo, task, taskCelebrity);
    }


    /**
     * 新增合同
     * @param condition
     * @param contractNo
     * @param task
     * @param taskCelebrity
     */
    private void saveContract(TaskProcessVo condition, String contractNo, Task task, TaskCelebrity taskCelebrity) {
        TaskCelebrityContract tcc = new TaskCelebrityContract();
        IdHelper.setIfEmptyId(tcc);
        tcc.setTaskId(condition.getTid());
        tcc.setCid(taskCelebrity.getCid());
        tcc.setEid(task.getCreator());
        tcc.setTcId(condition.getTcId());
        tcc.setProjectSn(contractNo);
        tcc.setProjectName(contractNo);
        tcc.setFileSn(contractNo);
        tcc.setEnterpriseContract(YllConstants.ONE);   //1-未签约，2-已签约
        tcc.setCelebrityContract(YllConstants.ONE);
        tcc.setState(1);
        tcc.setEnabled(1);
        tcc.setDeleted(0);
        tcc.setCreator(task.getCreator());
        tcc.setCreatedTime(DateUtil.now());
        taskCelebrityContractMapper.insert(tcc);
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(TaskProcess vo) {
        String id = vo.getId();

    }


    /**
     * 初始化创建信息
     * @param condition
     */
    private void initCreator(TaskProcessVo condition) {
        Principal principal = securitys.getPrincipal();
        if(null == principal || StringUtils.isBlank(principal.getUserId())){
            condition.setCreator(principal.getUserId());
        } else{
            condition.setCreator(AppSecuritysUtil.getCustomerId());
        }
        condition.setCreatedTime(DateUtil.now());
    }

}
