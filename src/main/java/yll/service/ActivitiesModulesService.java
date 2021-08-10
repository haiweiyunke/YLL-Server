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
import yll.entity.ActivitiesModules;
import yll.mapper.ActivitiesModulesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesModulesVo;

import java.util.List;

/**
 *  活动详情模块
 */
@Transactional
@Service
public class ActivitiesModulesService {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesModulesMapper activitiesModulesMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(ActivitiesModulesVo vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        ActivitiesModules entity = new ActivitiesModules();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getActivityId());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        activitiesModulesMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        activitiesModulesMapper.deleteById(id);
//        voRoleMapper.deleteByActivitiesModulesId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(ActivitiesModulesVo vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        ActivitiesModules entity = activitiesModulesMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());

        if(null != vo.getActivityId()){
            entity.setTargetId(vo.getActivityId());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesModulesMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        ActivitiesModules entity = new ActivitiesModules();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesModulesMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public ActivitiesModules getById(String id) {
        ActivitiesModules entity = activitiesModulesMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesModules> pagedQuery(Pagination pagination, ActivitiesModules condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesModulesMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesModules> pagedQueryWithType(Pagination pagination, ActivitiesModules condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesModulesMapper.findByWithType(condition));
    }

    /**
     * 列表查询-App
     * @param condition 查询条件
     * @return 分果
     */
    public List<ActivitiesModulesVo> getAppList(ActivitiesModulesVo condition) {
        return activitiesModulesMapper.getAppList(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(ActivitiesModules vo) {
        String id = vo.getId();
        String targetId = vo.getTargetId();
        String content = vo.getContent();
        String type = vo.getType();

        if (StringUtils.isEmpty(content)) {
            throw ExceptionHelper.prompt("内容不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw ExceptionHelper.prompt("类型不能为空");
        }
    }
}
