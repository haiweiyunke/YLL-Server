package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.identifier.IdHelper;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.TaskCelebrity;
import yll.mapper.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityVo;

import java.util.List;


/**
 * 任务达人
 */
@Transactional
@Service
public class TaskCelebrityService {

    // ==============================Fields===========================================
    @Autowired
    private TaskProcessMapper taskProcessMapper;
    @Autowired
    private TaskCelebrityProductsMapper taskCelebrityProductsMapper;
    @Autowired
    private TaskCelebrityContractMapper taskCelebrityContractMapper;
    @Autowired
    private TaskCelebrityLiveMapper taskCelebrityLiveMapper;
    @Autowired
    private TaskEnterpriseComplaintMapper taskEnterpriseComplaintMapper;
    @Autowired
    private TaskCelebrityMapper taskCelebrityMapper;
       @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(TaskCelebrity vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskCelebrity entity = new TaskCelebrity();
        IdHelper.setIfEmptyId(entity);

        entity.setTaskId(vo.getTaskId());
        entity.setCid(vo.getCid());
        entity.setType(vo.getType());
        entity.setCurrentState(vo.getCurrentState());
        entity.setProductList(vo.getProductList());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        taskCelebrityMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        taskCelebrityMapper.deleteById(id);
    }


    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(TaskCelebrity vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskCelebrity entity = taskCelebrityMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTaskId(vo.getTaskId());
        entity.setCid(vo.getCid());
        entity.setType(vo.getType());
        entity.setCurrentState(vo.getCurrentState());
        entity.setProductList(vo.getProductList());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        taskCelebrityMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public TaskCelebrity getById(String id) {
        TaskCelebrity entity = taskCelebrityMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        TaskCelebrity entity = new TaskCelebrity();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskCelebrityMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public TaskCelebrity findByCondition(TaskCelebrityVo condition) {
        return taskCelebrityMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskCelebrity> pagedQuery(Pagination pagination, TaskCelebrity condition) {
        return MybatisHelper.selectPage(pagination, () -> taskCelebrityMapper.findBy(condition));
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskCelebrity> findBy(TaskCelebrityVo condition) {
        return taskCelebrityMapper.findBy(condition);
    }



    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskCelebrityVo> getList(TaskCelebrityVo condition) {
        return taskCelebrityMapper.getList(condition);
    }



    /**
     * 分页查询-后台使用
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskCelebrityVo> pagedQueryAdminList(Pagination pagination, TaskCelebrityVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskCelebrityMapper.pagedQueryAdminList(condition));
    }


    /**
     * 删除(订单关联数据删除)
     * @param
     */
    public void deleteByAboutOrder(String tcId) {
        //删除流程
        taskProcessMapper.deleteByTcId(tcId);
        //删除商品
        taskCelebrityProductsMapper.deleteByTcId(tcId);
        //删除合同
        taskCelebrityContractMapper.deleteByTcId(tcId);
        //删除结案
        taskCelebrityLiveMapper.deleteByTcId(tcId);
        //删除申诉
        taskEnterpriseComplaintMapper.deleteByTcId(tcId);
        //删除订单
        taskCelebrityMapper.deleteById(tcId);

    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(TaskCelebrity vo) {
        String id = vo.getId();

    }


}
