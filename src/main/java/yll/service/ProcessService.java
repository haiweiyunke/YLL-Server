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
import yll.common.security.app.AppPrincipal;
import yll.common.security.app.AppSecuritys;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.Dic;
import yll.entity.Process;
import yll.mapper.CommentMapper;
import yll.mapper.ProcessMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ProcessVo;

import java.util.List;


/**
 * 流程
 */
@Transactional
@Service
public class ProcessService {

    // ==============================Fields===========================================
    @Autowired
    private ProcessMapper processMapper;
       @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Process vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Process entity = new Process();
        IdHelper.setIfEmptyId(entity);

        entity.setPid(vo.getPid());
        entity.setCode(vo.getCode());
        entity.setName(vo.getName());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());
        entity.setNextProcess(vo.getNextProcess());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        processMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        processMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(Process vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Process entity = processMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setPid(vo.getPid());
        entity.setCode(vo.getCode());
        entity.setName(vo.getName());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());
        entity.setNextProcess(vo.getNextProcess());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        processMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public Process getById(String id) {
        Process entity = processMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Process entity = new Process();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        processMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public Process findByCondition(ProcessVo condition) {
        return processMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Process> pagedQuery(Pagination pagination, Process condition) {
        return MybatisHelper.selectPage(pagination, () -> processMapper.findBy(condition));
    }


    /**
     * 条件查询
     * @param condition 查询条件
     */
    public List<Process> findBy(ProcessVo condition) {
        return processMapper.findBy(condition);
    }


    /**
     * 自定义条件查询
     * @param condition 查询条件
     */
    public List<ProcessVo> getList(ProcessVo condition) {
        return processMapper.getList(condition);
    }


    /**
     * 列表查询-App
     * @param condition 查询条件
     */
    public List<ProcessVo> getAppList(ProcessVo condition) {
        return processMapper.getAppList(condition);
    }


    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ProcessVo> pagedQueryApp(Pagination pagination, ProcessVo condition) {
        return MybatisHelper.selectPage(pagination, () -> processMapper.getAppList(condition));
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Process vo) {
        String id = vo.getId();

    }

     /** 获取下一流程  TODO 继续流程process 封装模块，以备后续商品模块使用
      * @param id 流程id
      * */
    private String getNextProcess(String id) {
        if(StringUtils.isNotBlank(id)){
            throw ExceptionHelper.prompt("流程id不能为空");
        }
        Process entity = processMapper.getById(id);
        if(null == entity){
            throw ExceptionHelper.prompt("流程不存在");
        }
        String nextProcess = entity.getNextProcess();
        if(StringUtils.isBlank(nextProcess)){
            return "process-";
        }
        if(nextProcess.indexOf("process-") != -1){
            Process condition = new Process();
            condition.setPid("0");
            if(nextProcess.indexOf("process--task-order") != -1){
                //任务-订单
                condition.setType("process--task-order");
                Process byCondition = processMapper.findByCondition(condition);
                return "";
            }
            if(nextProcess.indexOf("process-task-live") != -1){
                //任务-直播
            }
            if(nextProcess.indexOf("process-task-live") != -1){
                //选品中心-
            }

        }
        return "";
    }


}
