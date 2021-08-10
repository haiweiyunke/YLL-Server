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
import yll.entity.TaskCelebrityProducts;
import yll.mapper.CommentMapper;
import yll.mapper.TaskCelebrityProductsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityProductsVo;

import java.util.List;


/**
 * 任务达人
 */
@Transactional
@Service
public class TaskCelebrityProductsService {

    // ==============================Fields===========================================
    @Autowired
    private TaskCelebrityProductsMapper taskCelebrityProductsMapper;
       @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(TaskCelebrityProducts vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskCelebrityProducts entity = new TaskCelebrityProducts();
        IdHelper.setIfEmptyId(entity);

        entity.setTaskId(vo.getTaskId());
        entity.setCid(vo.getCid());
        entity.setTcId(vo.getTcId());
        entity.setPid(vo.getPid());
        entity.setType(vo.getType());
        entity.setReceive(vo.getReceive());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        taskCelebrityProductsMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        taskCelebrityProductsMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(TaskCelebrityProducts vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        TaskCelebrityProducts entity = taskCelebrityProductsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTaskId(vo.getTaskId());
        entity.setCid(vo.getCid());
        entity.setTcId(vo.getTcId());
        entity.setPid(vo.getPid());
        entity.setType(vo.getType());
        entity.setReceive(vo.getReceive());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        taskCelebrityProductsMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public TaskCelebrityProducts getById(String id) {
        TaskCelebrityProducts entity = taskCelebrityProductsMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        TaskCelebrityProducts entity = new TaskCelebrityProducts();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskCelebrityProductsMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public TaskCelebrityProducts findByCondition(TaskCelebrityProductsVo condition) {
        return taskCelebrityProductsMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskCelebrityProducts> pagedQuery(Pagination pagination, TaskCelebrityProducts condition) {
        return MybatisHelper.selectPage(pagination, () -> taskCelebrityProductsMapper.findBy(condition));
    }


    /**
     * 条件查询-App
     * @param condition 查询条件
     */
    public List<TaskCelebrityProducts> findBy(TaskCelebrityProducts condition) {
        return taskCelebrityProductsMapper.findBy(condition);
    }


    /**
     * 获取列表-App
     * @param condition 查询条件
     */
    public List<TaskCelebrityProductsVo> getList(TaskCelebrityProductsVo condition) {
        return taskCelebrityProductsMapper.getList(condition);
    }


    /**
     * 获取商品列表-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskCelebrityProductsVo> getAppProductsList(TaskCelebrityProductsVo condition) {
        return taskCelebrityProductsMapper.getAppProductsList(condition);
    }


    /**
     * 获取商品列表-后台列表
     * @param condition
     */
    public Page<TaskCelebrityProductsVo> pagedQueryProductsList(Pagination pagination, TaskCelebrityProductsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> taskCelebrityProductsMapper.pagedQueryProductsList(condition));
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(TaskCelebrityProducts vo) {
        String id = vo.getId();

    }


}
