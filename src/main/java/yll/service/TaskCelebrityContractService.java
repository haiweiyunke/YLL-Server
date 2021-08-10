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
import yll.common.standard.CommonAttributeUtil;
import yll.entity.TaskCelebrityContract;
import yll.mapper.CommentMapper;
import yll.mapper.TaskCelebrityContractMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityContractVo;

import java.util.List;


/**
 * 任务达人合同
 */
@Transactional
@Service
public class TaskCelebrityContractService {

    // ==============================Fields===========================================
    @Autowired
    private TaskCelebrityContractMapper taskCelebrityContractMapper;
       @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(TaskCelebrityContract vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskCelebrityContract entity = new TaskCelebrityContract();
        IdHelper.setIfEmptyId(entity);

        entity.setTaskId(vo.getTaskId());
        entity.setCid(vo.getCid());
        entity.setTcId(vo.getTcId());
        entity.setType(vo.getType());
        entity.setEid(vo.getEid());

        entity.setProjectSn(vo.getProjectSn());
        entity.setProjectName(vo.getProjectName());
        entity.setFileSn(vo.getFileSn());
        entity.setEnterpriseContract(vo.getEnterpriseContract());
        entity.setCelebrityContract(vo.getCelebrityContract());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        taskCelebrityContractMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        taskCelebrityContractMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(TaskCelebrityContract vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskCelebrityContract entity = taskCelebrityContractMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTaskId(vo.getTaskId());
        entity.setCid(vo.getCid());
        entity.setTcId(vo.getTcId());
        entity.setType(vo.getType());
        entity.setEid(vo.getEid());

        entity.setProjectSn(vo.getProjectSn());
        entity.setProjectName(vo.getProjectName());
        entity.setFileSn(vo.getFileSn());
        entity.setEnterpriseContract(vo.getEnterpriseContract());
        entity.setCelebrityContract(vo.getCelebrityContract());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        taskCelebrityContractMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public TaskCelebrityContract getById(String id) {
        TaskCelebrityContract entity = taskCelebrityContractMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        TaskCelebrityContract entity = new TaskCelebrityContract();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskCelebrityContractMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public TaskCelebrityContract findByCondition(TaskCelebrityContract condition) {
        return taskCelebrityContractMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskCelebrityContract> pagedQuery(Pagination pagination, TaskCelebrityContract condition) {
        return MybatisHelper.selectPage(pagination, () -> taskCelebrityContractMapper.findBy(condition));
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskCelebrityContract> findBy(TaskCelebrityContractVo condition) {
        return taskCelebrityContractMapper.findBy(condition);
    }



    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskCelebrityContractVo> getList(TaskCelebrityContractVo condition) {
        return taskCelebrityContractMapper.getList(condition);
    }



    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(TaskCelebrityContract vo) {
        String id = vo.getId();

    }


}
