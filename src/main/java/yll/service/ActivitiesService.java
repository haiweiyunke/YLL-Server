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
import yll.entity.Activities;
import yll.mapper.ActivitiesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesVo;

import java.util.List;

/**
 * 活动
 */
@Transactional
@Service
public class ActivitiesService {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesMapper activitiesMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Activities vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Activities entity = new Activities();
        IdHelper.setIfEmptyId(entity);

        entity.setName(vo.getName());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setRealNum(vo.getRealNum());
        entity.setOperateNum(vo.getOperateNum());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setStartTime(vo.getStartTime());
        entity.setEndTime(vo.getEndTime());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        activitiesMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        activitiesMapper.deleteById(id);
//        voRoleMapper.deleteByActivitiesId(id);   关联删除
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(Activities vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Activities entity = activitiesMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setName(vo.getName());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setRealNum(vo.getRealNum());
        entity.setOperateNum(vo.getOperateNum());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setStartTime(vo.getStartTime());
        entity.setEndTime(vo.getEndTime());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Activities entity = new Activities();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public Activities getById(String id) {
        Activities entity = activitiesMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Activities> pagedQuery(Pagination pagination, Activities condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesMapper.findBy(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesVo> pagedQueryWithType(Pagination pagination, ActivitiesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesMapper.findByWithType(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesVo> getAppList(Pagination pagination, ActivitiesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesMapper.getAppList(condition));
    }

    /**
     * 查询所有
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<ActivitiesVo> allQueryWithType(ActivitiesVo condition) {
        return activitiesMapper.findByWithType(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Activities vo) {
        String id = vo.getId();
        String name = vo.getName();
        String type = vo.getType();

        if (StringUtils.isEmpty(name)) {
            throw ExceptionHelper.prompt("标题不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw ExceptionHelper.prompt("类型不能为空");
        }
    }
}
