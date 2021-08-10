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
import yll.entity.ActivitiesQuestions;
import yll.mapper.ActivitiesQuestionsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesQuestionsVo;

import java.util.List;


/**
 * 活动竞赛题目
 */
@Transactional
@Service
public class ActivitiesQuestionsService {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesQuestionsMapper activitiesQuestionsMapper;
    @Autowired
    private ActivitiesAnswerService activitiesAnswerService;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(ActivitiesQuestionsVo vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        ActivitiesQuestions entity = new ActivitiesQuestions();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getActivityId());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        activitiesQuestionsMapper.insert(entity);
        vo.setId(entity.getId());
        //删除原答案，重新更新答案
        activitiesAnswerService.insertBatch(vo);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        activitiesQuestionsMapper.deleteById(id);
//        voRoleMapper.deleteByActivitiesQuestionsId(id);   关联删除
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(ActivitiesQuestionsVo vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        ActivitiesQuestions entity = activitiesQuestionsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getActivityId()){
            entity.setTargetId(vo.getActivityId());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesQuestionsMapper.update(entity);

        //删除原答案，重新更新答案
        activitiesAnswerService.insertBatch(vo);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        ActivitiesQuestions entity = new ActivitiesQuestions();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesQuestionsMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public ActivitiesQuestions getById(String id) {
        ActivitiesQuestions entity = activitiesQuestionsMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesQuestions> pagedQuery(Pagination pagination, ActivitiesQuestions condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesQuestionsMapper.findBy(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesQuestionsVo> pagedQueryWithType(Pagination pagination, ActivitiesQuestionsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesQuestionsMapper.findByWithType(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<ActivitiesQuestionsVo> getAppList(ActivitiesQuestionsVo condition) {
        return activitiesQuestionsMapper.getAppList(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(ActivitiesQuestions vo) {
        String id = vo.getId();
        String content = vo.getContent();
        String type = vo.getType();

        if (StringUtils.isBlank(content)) {
            throw ExceptionHelper.prompt("题目不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw ExceptionHelper.prompt("类型不能为空");
        }
    }
}
