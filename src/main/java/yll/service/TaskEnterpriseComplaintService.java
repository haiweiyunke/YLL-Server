package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.identifier.IdHelper;
import yll.common.security.app.AppPrincipal;
import yll.common.security.app.AppSecuritys;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.TaskEnterpriseComplaint;
import yll.mapper.CommentMapper;
import yll.mapper.TaskEnterpriseComplaintMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityLiveVo;
import yll.service.model.vo.TaskEnterpriseComplaintVo;

import java.util.List;


/**
 * 任务达人直播结案
 */
@Transactional
@Service
public class TaskEnterpriseComplaintService {

    // ==============================Fields===========================================
    @Autowired
    private TaskEnterpriseComplaintMapper taskEnterpriseComplaintMapper;
   @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Securitys securitys;
    @Autowired
    private AppSecuritys appSecuritys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(TaskEnterpriseComplaint vo) {

        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        TaskEnterpriseComplaint entity = new TaskEnterpriseComplaint();
        IdHelper.setIfEmptyId(entity);

        entity.setTid(vo.getTid());
        entity.setTcId(vo.getTcId());
        entity.setReason(vo.getReason());
        entity.setImage(vo.getImage());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, appPrincipal);
        taskEnterpriseComplaintMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        taskEnterpriseComplaintMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(TaskEnterpriseComplaint vo) {
        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        TaskEnterpriseComplaint entity = taskEnterpriseComplaintMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTid(vo.getTid());
        entity.setTcId(vo.getTcId());
        entity.setReason(vo.getReason());
        entity.setImage(vo.getImage());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, appPrincipal);
        taskEnterpriseComplaintMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public TaskEnterpriseComplaint getById(String id) {
        TaskEnterpriseComplaint entity = taskEnterpriseComplaintMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        TaskEnterpriseComplaint entity = new TaskEnterpriseComplaint();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskEnterpriseComplaintMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public TaskEnterpriseComplaint findByCondition(TaskEnterpriseComplaintVo condition) {
        return taskEnterpriseComplaintMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskEnterpriseComplaint> pagedQuery(Pagination pagination, TaskEnterpriseComplaint condition) {
        return MybatisHelper.selectPage(pagination, () -> taskEnterpriseComplaintMapper.findBy(condition));
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskEnterpriseComplaint> findBy(TaskEnterpriseComplaintVo condition) {
        return taskEnterpriseComplaintMapper.findBy(condition);
    }


    /**
     * 列表查询
     * @param condition 查询条件
     */
    public List<TaskEnterpriseComplaintVo> getList(TaskEnterpriseComplaintVo condition) {
        return taskEnterpriseComplaintMapper.getAppList(condition);
    }


    /**
     * 列表查询-App
     * @param condition 查询条件
     */
    public List<TaskEnterpriseComplaintVo> getAppList(TaskEnterpriseComplaintVo condition) {
        return taskEnterpriseComplaintMapper.getAppList(condition);
    }


    /**
     * 分页查询
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskEnterpriseComplaintVo> pagedQueryList(Pagination pagination, TaskEnterpriseComplaintVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskEnterpriseComplaintMapper.getList(condition));
    }

    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskEnterpriseComplaintVo> pagedQueryAppList(Pagination pagination, TaskEnterpriseComplaintVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskEnterpriseComplaintMapper.getAppList(condition));
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(TaskEnterpriseComplaint vo) {
        String id = vo.getId();

    }


}
