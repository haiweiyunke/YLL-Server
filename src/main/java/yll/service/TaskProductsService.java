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
import yll.entity.TaskProducts;
import yll.mapper.CommentMapper;
import yll.mapper.TaskProductsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityProductsVo;
import yll.service.model.vo.TaskCelebrityVo;
import yll.service.model.vo.TaskProductsVo;

import java.util.List;


/**
 * 任务商品
 */
@Transactional
@Service
public class TaskProductsService {

    // ==============================Fields===========================================
    @Autowired
    private TaskProductsMapper taskProductsMapper;
   @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(TaskProducts vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskProducts entity = new TaskProducts();
        IdHelper.setIfEmptyId(entity);

        entity.setTaskId(vo.getTaskId());
        entity.setPid(vo.getPid());
        entity.setType(vo.getType());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        taskProductsMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        taskProductsMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(TaskProducts vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskProducts entity = taskProductsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTaskId(vo.getTaskId());
        entity.setPid(vo.getPid());
        entity.setType(vo.getType());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        taskProductsMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public TaskProducts getById(String id) {
        TaskProducts entity = taskProductsMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        TaskProducts entity = new TaskProducts();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskProductsMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public TaskProducts findByCondition(TaskProductsVo condition) {
        return taskProductsMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskProducts> pagedQuery(Pagination pagination, TaskProducts condition) {
        return MybatisHelper.selectPage(pagination, () -> taskProductsMapper.findBy(condition));
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskProducts> findBy(TaskProductsVo condition) {
        return taskProductsMapper.findBy(condition);
    }



    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskProductsVo> getList(TaskProductsVo condition) {
        return taskProductsMapper.getList(condition);
    }


    /**
     * 分页查询-后台使用
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskProductsVo> pagedQueryAdminList(Pagination pagination, TaskCelebrityProductsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskProductsMapper.getAdminProductsList(condition));
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(TaskProducts vo) {
        String id = vo.getId();

    }


}
