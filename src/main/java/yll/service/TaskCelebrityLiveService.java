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
import yll.entity.TaskCelebrityLive;
import yll.mapper.CommentMapper;
import yll.mapper.TaskCelebrityLiveMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.TaskCelebrityLiveVo;

import java.util.List;


/**
 * 任务达人直播结案
 */
@Transactional
@Service
public class TaskCelebrityLiveService {

    // ==============================Fields===========================================
    @Autowired
    private TaskCelebrityLiveMapper taskCelebrityLiveMapper;
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
    public void insert(TaskCelebrityLive vo) {

        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        TaskCelebrityLive entity = new TaskCelebrityLive();
        IdHelper.setIfEmptyId(entity);

        entity.setTid(vo.getTid());
        entity.setTcId(vo.getTcId());
        entity.setPids(vo.getPids());
        entity.setProductList(vo.getProductList());
        entity.setImage(vo.getImage());
        entity.setCompleteTime(vo.getCompleteTime());
        entity.setSessions(vo.getSessions());

        entity.setRemark(vo.getRemark());
        entity.setState(vo.getState());
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, appPrincipal);
        taskCelebrityLiveMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        taskCelebrityLiveMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(TaskCelebrityLive vo) {
        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        TaskCelebrityLive entity = taskCelebrityLiveMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTid(vo.getTid());
        entity.setTcId(vo.getTcId());
        entity.setPids(vo.getPids());
        entity.setProductList(vo.getProductList());
        entity.setImage(vo.getImage());
        entity.setCompleteTime(vo.getCompleteTime());
        entity.setSessions(vo.getSessions());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, appPrincipal);
        taskCelebrityLiveMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public TaskCelebrityLive getById(String id) {
        TaskCelebrityLive entity = taskCelebrityLiveMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        TaskCelebrityLive entity = new TaskCelebrityLive();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        taskCelebrityLiveMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public TaskCelebrityLive findByCondition(TaskCelebrityLiveVo condition) {
        return taskCelebrityLiveMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<TaskCelebrityLive> pagedQuery(Pagination pagination, TaskCelebrityLive condition) {
        return MybatisHelper.selectPage(pagination, () -> taskCelebrityLiveMapper.findBy(condition));
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<TaskCelebrityLive> findBy(TaskCelebrityLiveVo condition) {
        return taskCelebrityLiveMapper.findBy(condition);
    }


    /**
     *  列表查询-App
     * @param condition 查询条件
     */
    public List<TaskCelebrityLiveVo> getAppList(TaskCelebrityLiveVo condition) {
        return taskCelebrityLiveMapper.getAppList(condition);
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(TaskCelebrityLive vo) {
        String id = vo.getId();

    }


}
