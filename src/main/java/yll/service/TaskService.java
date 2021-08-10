package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.constants.ProjectConstants;
import yll.common.identifier.IdHelper;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.*;
import yll.mapper.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityProductsVo;
import yll.service.model.vo.TaskCelebrityVo;
import yll.service.model.vo.TaskProcessVo;
import yll.service.model.vo.TaskVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 任务信息
 */
@Transactional
@Service
public class TaskService {

    // ==============================Fields===========================================
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private DicMapper dicMapper;
    @Autowired
    private SlideMapper slideMapper;
    @Autowired
    private Securitys securitys;
    @Autowired
    private TaskProductsMapper taskProductsMapper;
    @Autowired
    private TaskCelebrityMapper taskCelebrityMapper;
    @Autowired
    private TaskCelebrityProductsMapper taskCelebrityProductsMapper;
    @Autowired
    private TaskProcessService taskProcessService;
    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Task insert(Task vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Task entity = new Task();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setTaskName(vo.getTaskName());
        entity.setValidStartTime(vo.getValidStartTime());
        entity.setValidEndTime(vo.getValidEndTime());
        entity.setMarketingStartTime(vo.getMarketingStartTime());
        entity.setMarketingEndTime(vo.getMarketingEndTime());
        entity.setType(vo.getType());
        entity.setSponsor(vo.getSponsor());
        entity.setExpertise(vo.getExpertise());
        entity.setPlatform(vo.getPlatform());
        entity.setDescription(vo.getDescription());
        entity.setProductImages(vo.getProductImages());
        entity.setAdvertisement(vo.getAdvertisement());
        entity.setSellingPoint(vo.getSellingPoint());
        entity.setCover(vo.getCover());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setCategory(vo.getCategory());
        entity.setProductLink(vo.getProductLink());
        entity.setRetailPrice(vo.getRetailPrice());
        entity.setDiscount(vo.getDiscount());
        entity.setDiscountNum(vo.getDiscountNum());
        entity.setQuantity(vo.getQuantity());
        entity.setCommissionRatio(vo.getCommissionRatio());
        entity.setGiveBack(vo.getGiveBack());
        entity.setConferences(vo.getConferences());
        entity.setAppearanceFees(vo.getAppearanceFees());
        entity.setProductLicense(vo.getProductLicense());
        entity.setCertificate(vo.getCertificate());
        entity.setQualityCertificate(vo.getQualityCertificate());
        entity.setQualityCertificate(vo.getQualityCertificate());
        entity.setProductList(vo.getProductList());

        entity.setPhone(vo.getPhone());
        entity.setServicePrice(vo.getServicePrice());
        entity.setTaskPlace(vo.getTaskPlace());
        entity.setLivePlatform(vo.getLivePlatform());
        entity.setDeposit(vo.getDeposit());

        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setEnabled(YllConstants.ONE);
        entity.setSlide(YllConstants.ZERO);
        entity.setExtension(YllConstants.ZERO);
        entity.setDeleted(YllConstants.ZERO);
        if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
        }
        taskMapper.insert(entity);

        return entity;
    }

    /**
     * 新增（APP 带产品、达人集合字串使用）
     * @param
     */
    public void insert(TaskVo taskVo) {
        Task task = new Task();
        BeanUtils.copyProperties(taskVo, task);
        String celebrityIn = taskVo.getCelebrityIn();
        int orderNum = StringUtils.isBlank(celebrityIn) ? 0 : celebrityIn.split(",").length;
        task.setOrderNum(orderNum);
        task = insert(task);
        String tid = task.getId();
        taskVo.setId(tid);
        //处理达人、商品
        productsHandle(taskVo);
        celebrityHandle(taskVo);
        //流程初始化
        processHandle(taskVo);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        taskMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public Task update(Task vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Task entity = taskMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTaskName(vo.getTaskName());
        entity.setValidStartTime(vo.getValidStartTime());
        entity.setValidEndTime(vo.getValidEndTime());
        entity.setMarketingStartTime(vo.getMarketingStartTime());
        entity.setMarketingEndTime(vo.getMarketingEndTime());
        entity.setType(vo.getType());
        entity.setSponsor(vo.getSponsor());
        entity.setExpertise(vo.getExpertise());
        entity.setPlatform(vo.getPlatform());
        entity.setDescription(vo.getDescription());
        entity.setProductImages(vo.getProductImages());
        entity.setAdvertisement(vo.getAdvertisement());
        entity.setSellingPoint(vo.getSellingPoint());
        entity.setCover(vo.getCover());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setCategory(vo.getCategory());
        entity.setProductLink(vo.getProductLink());
        entity.setRetailPrice(vo.getRetailPrice());
        entity.setDiscount(vo.getDiscount());
        entity.setDiscountNum(vo.getDiscountNum());
        entity.setQuantity(vo.getQuantity());
        entity.setCommissionRatio(vo.getCommissionRatio());
        entity.setGiveBack(vo.getGiveBack());
        entity.setConferences(vo.getConferences());
        entity.setAppearanceFees(vo.getAppearanceFees());
        entity.setProductLicense(vo.getProductLicense());
        entity.setCertificate(vo.getCertificate());
        entity.setQualityCertificate(vo.getQualityCertificate());
        entity.setProductList(vo.getProductList());

        entity.setPhone(vo.getPhone());
        entity.setServicePrice(vo.getServicePrice());
        entity.setTaskPlace(vo.getTaskPlace());
        entity.setLivePlatform(vo.getLivePlatform());
        entity.setDeposit(vo.getDeposit());

        entity.setRemark(vo.getRemark());

        if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        } else{
            entity.setSlide(YllConstants.ZERO);
        }

        if(null != vo.getExtension()){
            entity.setExtension(vo.getExtension());
        } else{
            entity.setExtension(YllConstants.ZERO);
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));
        if(StringUtils.isBlank(vo.getModifier())){
            CommonAttributeUtil.setUpdated(entity, principal);
        } else {
            //后台直接添加、用户自己注册
            entity.setModifier(vo.getModifier());
            entity.setModifiedTime(vo.getModifiedTime());
        }
//        CommonAttributeUtil.setUpdated(entity, principal);

        //状态修改时，变更流程状态
        taskStateProcess(vo, entity);
        entity.setCurrentState(null);   //防止二次修改
        //TODO 修改任务状态--暂时使用，上任务后删除此处
       /* if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
            entity.setState(YllConstants.ONE);
        }*/

        taskMapper.update(entity);

        //修改轮播图
        Slide svo = new Slide();
        svo.setTargetId(entity.getId());
        svo = slideMapper.findByCondition(svo);
        if(null != svo && StringUtils.isNotBlank(entity.getCover())){
            svo.setImg(entity.getCover());
        }
        return entity;
    }

    /**
     * 变更流程状态
     * @param vo
     * @param entity
     */
    private void taskStateProcess(Task vo, Task entity) {
        Integer state = entity.getState();
        Integer newState = vo.getState();
        if(state != newState){
            TaskProcessVo tp = new TaskProcessVo();
            tp.setTid(entity.getId());
            tp.setProcess(ProjectConstants.Task.A2);
            TaskProcess tpResult = taskProcessService.findByCondition(tp);
            //状态修改，更新流程及订单状态
            if(null == tpResult){
                try {
                    if(state == 1 && newState == 2){
                        //审核通过
                        taskProcessService.processHandle(entity.getId(), entity.getId(), ProjectConstants.Task.A2);
                    } else if(state == 1 && newState == 3){
                        //审核失败
                        taskProcessService.processHandle(entity.getId(), entity.getId(), ProjectConstants.Task.A3_2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //修改任务状态
                if(null != vo.getState()){
                    entity.setState(vo.getState());
                } else{
                    entity.setState(YllConstants.ONE);
                }
            }
        }
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Task entity = new Task();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Task getById(String id) {
        Task entity = taskMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public Task getByCondition(Task condition) {
        return taskMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Task> pagedQuery(Pagination pagination, Task condition) {
        return MybatisHelper.selectPage(pagination, () -> taskMapper.findBy(condition));
    }

    /**
     * 查询全部（根据条件）-app认证详情
     * @param
     * @return 实体
     */
    public Task getAppAuth(Task condition) {
//        return taskMapper.getAppAuth(condition);
        return null;
    }

    /**
     * 分页查询-后台使用
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskVo> findByAdminList(Pagination pagination, TaskVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskMapper.findByAdminList(condition));
    }

    /**
     *  轮播
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskVo> findBySlide(TaskVo condition) {
        return taskMapper.findBySlide(condition);
    }

    /**
     *  推广
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskVo> findByExtension(TaskVo condition) {
        return taskMapper.findByExtension(condition);
    }

    /**
     * 轮播修改
     * @param
     */
    public void slide(TaskVo condition) {
        String oldSlideId = condition.getOldSlideId();
        if(StringUtils.isNotBlank(oldSlideId)){
            Task old = taskMapper.getById(oldSlideId);
            old.setSlide(0);
            old.setOrdinal(YllConstants.LAST);
            taskMapper.update(old);
        }
        condition.setSlide(YllConstants.ONE);
        taskMapper.update(condition);
    }

    /**
     * 推广修改
     * @param
     */
    public void extension(TaskVo condition) {
        String oldExtensionId = condition.getOldExtensionId();
        if(StringUtils.isNotBlank(oldExtensionId)){
            Task old = taskMapper.getById(oldExtensionId);
            old.setExtension(0);
            old.setExtensionOrdinal(YllConstants.LAST);
            taskMapper.update(old);
        }
        condition.setExtension(YllConstants.ONE);
        taskMapper.update(condition);
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public TaskVo getAppDetail(TaskVo condition) {
        return taskMapper.getAppDetail(condition);
    }

    /**
     * 分页查询-推广广告
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskVo> pagedQueryExtension(Pagination pagination, TaskVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskMapper.findByExtension(condition));
    }


    /**
     * 分页查询-app邀请列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskVo> getAppList(Pagination pagination, TaskVo condition) {
        //拼接order by   orderBy
        String orderBy = "";
        Integer expertiseOrder = condition.getExpertiseOrder();
        Integer platformOrder = condition.getPlatformOrder();
        Integer retailPriceOrder = condition.getRetailPriceOrder();
        //外部-佣金
        if(null != expertiseOrder && 2 == expertiseOrder){
            orderBy += " expertiseNum desc,";
        } else if(null != expertiseOrder && 1 == expertiseOrder){
            orderBy += " expertiseNum,";
        }
        //外部-押金
        if(null != platformOrder && 2 == platformOrder){
            orderBy += " platformNum desc,";
        } else if(null != platformOrder && 1 == platformOrder){
            orderBy += " platformNum,";
        }
        //外部-发布时间
        if(null != retailPriceOrder && 2 == retailPriceOrder){
            orderBy += " m.id desc,";
        } else if(null != platformOrder && 1 == platformOrder){
            orderBy += " m.id,";
        }

        if(StringUtils.isBlank(orderBy)){
            orderBy += " m.id desc";
        } else{
            orderBy = orderBy.substring(0, orderBy.length()-1);
        }
        condition.setOrderBy(orderBy);
        appQueryParams(condition);
        return MybatisHelper.selectPage(pagination, () -> taskMapper.getAppList(condition));
    }


    /**
     * 分页查询-企业主任务列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskVo> pagedQueryEnterpriseTaskList(Pagination pagination, TaskVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskMapper.getAppEnterpriseTaskList(condition));
    }


    /**
     * 分页查询-企业主任务订单列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskVo> pagedQueryEnterpriseOrderList(Pagination pagination, TaskVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskMapper.getAppEnterpriseOrderList(condition));
    }

    /**
     * 分页查询-达人任务订单列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskVo> pagedQueryCelebrityTaskList(Pagination pagination, TaskVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskMapper.getAppCelebrityTaskList(condition));
    }


    /**
     * 详情查询-企业主任务订单列表
     * @param condition 查询条件
     * @return 结果
     */
    public TaskVo getAppEnterpriseTaskDetail(TaskVo condition) {
       return taskMapper.getAppEnterpriseTaskDetail(condition);
    }

    /**
     * 企业主任务达人列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 结果
     */
    public  Page<TaskVo> getAppEnterpriseTaskCelebrityList(Pagination pagination, TaskCelebrityVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskMapper.getAppEnterpriseTaskCelebrityList(condition));
    }


    /**
     * 详情查询-达人主任务订单详情
     * @param condition 查询条件
     * @return 结果
     */
    public TaskVo getAppCelebrityTaskDetail(TaskCelebrityVo condition) {
        return taskMapper.getAppCelebrityTaskDetail(condition);
    }


    /**
     * 封装查询参数
     * @param condition
     */
    private void appQueryParams(TaskVo condition){
        String appearanceFees = condition.getAppearanceFees();
        String commissionRatio = condition.getCommissionRatio();

        //出场费用
        if(StringUtils.isNotBlank(appearanceFees)) {
            Dic dic = dicMapper.getByCode(appearanceFees);
            if(null != dic){
                appearanceFees = dic.getRemark();
                String[] args = appearanceFees.split("-");
                if (args.length == 1) {
                    //以上
                    String str = args[0].split("以上")[0];
                    condition.setAppearanceFeesOne(str);
                } else if (args.length == 2) {
                    //区间
                    condition.setAppearanceFeesOne(args[0]);
                    condition.setAppearanceFeesTwo(args[1]);
                }
            }
        }

        //达人佣金比
        if(StringUtils.isNotBlank(commissionRatio)) {
            Dic dic = dicMapper.getByCode(commissionRatio);
            if(null != dic){
                commissionRatio = dic.getRemark();
                String[] args = commissionRatio.split("-");
                if (args.length == 1) {
                    //以上
                    String str = args[0].split("以上")[0];
                    condition.setCommissionRatioOne(str);
                } else if (args.length == 2) {
                    //区间
                    condition.setCommissionRatioOne(args[0]);
                    condition.setCommissionRatioTwo(args[1]);
                }
            }
        }
    }


    /**
     *  商品处理
     * @param taskVo
     */
    private void productsHandle(TaskVo taskVo){
        String productsIn = taskVo.getProductsIn();
        if(StringUtils.isNoneBlank(productsIn)){
            String[] pstr = productsIn.split(",");
            for (String pid :
                    pstr) {
                TaskProducts tp = new TaskProducts();
                IdHelper.setIfEmptyId(tp);
                tp.setPid(pid);
                tp.setTaskId(taskVo.getId());
                tp.setEnabled(1);
                tp.setState(1);
                tp.setDeleted(0);
                tp.setCreator(taskVo.getCreator());
                tp.setCreatedTime(taskVo.getCreatedTime());
                taskProductsMapper.insert(tp);
            }
        }
    }

    /**
     *  达人处理
     * @param taskVo
     */
    private void celebrityHandle(TaskVo taskVo) {
        String celebrityIn = taskVo.getCelebrityIn();
        if (StringUtils.isNoneBlank(celebrityIn)) {
            String[] cstr = celebrityIn.split(",");
            //任务达人商品条件
            String productsIn = taskVo.getProductsIn();
            List<String> pstr = new ArrayList<>();
            if(StringUtils.isNoneBlank(productsIn)) {
                String[] proArray = productsIn.split(",");
                pstr = Arrays.asList(proArray);
            }
            for (String cid :
                    cstr) {
                TaskCelebrity tc = new TaskCelebrity();
                IdHelper.setIfEmptyId(tc);
                tc.setCid(cid);
                tc.setTaskId(taskVo.getId());
                tc.setProductList(taskVo.getProductList());
                tc.setEnabled(1);
                tc.setState(1);
                tc.setDeleted(0);
                tc.setCreator(taskVo.getCreator());
                tc.setCreatedTime(taskVo.getCreatedTime());
                taskCelebrityMapper.insert(tc);
                String tcId = tc.getId();
                //任务达人商品表
                for (String pid :
                        pstr) {
                    TaskCelebrityProducts tcp = new TaskCelebrityProducts();
                    IdHelper.setIfEmptyId(tcp);
                    tcp.setPid(pid);
                    tcp.setCid(cid);
                    tcp.setTcId(tcId);
                    tcp.setTaskId(taskVo.getId());
                    tcp.setEnabled(1);
                    tcp.setState(1);
                    tcp.setReceive(0);    //0,-未发货，1-运送中，2-已签收
                    tcp.setDeleted(0);
                    tcp.setCreator(taskVo.getCreator());
                    tcp.setCreatedTime(taskVo.getCreatedTime());
                    taskCelebrityProductsMapper.insert(tcp);
                }
            }
        }
    }

    /**
     *  流程处理
     * @param taskVo
     */
    private void processHandle(TaskVo taskVo) {
        //tid与tcid相同，表示为task总表流程记录
        try {
            taskProcessService.processHandle(taskVo.getId(), taskVo.getId(), ProjectConstants.Task.A1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *  达人申请任务订单
     * @param condition
     */
    public TaskCelebrity taskApply(TaskVo condition) {
        String taskId = condition.getId();
        String cid = condition.getCid();
        String productsIn = condition.getProductsIn();
        String tcId = "";
        //tc表
        TaskCelebrity tc = new TaskCelebrity();
        IdHelper.setIfEmptyId(tc);
        tc.setCid(cid);
        tc.setTaskId(taskId);
        tc.setProductList(condition.getProductList());
        tc.setCurrentState(ProjectConstants.TaskOrder.B1_1);
        tc.setEnabled(1);
        tc.setState(1);
        tc.setDeleted(0);
        tc.setCreator(cid);
        tc.setCreatedTime(DateUtil.now());
        taskCelebrityMapper.insert(tc);
        tcId = tc.getId();
        //tcp表
        String[] pstr = productsIn.split(",");
        for (String pid :
                pstr) {
            TaskCelebrityProducts tcp = new TaskCelebrityProducts();
            IdHelper.setIfEmptyId(tcp);
            tcp.setPid(pid);
            tcp.setCid(cid);
            tcp.setTaskId(taskId);
            tcp.setTcId(tcId);
            tcp.setEnabled(1);
            tcp.setState(1);
            tcp.setReceive(0);    //0-未发货，-运送中，2-已签收
            tcp.setDeleted(0);
            tcp.setCreator(cid);
            tcp.setCreatedTime(DateUtil.now());
            taskCelebrityProductsMapper.insert(tcp);
        }
        //流程处理
        try {
            taskProcessService.processHandle(taskId, tc.getId(), ProjectConstants.TaskOrder.B1_1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tc;
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Task vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
