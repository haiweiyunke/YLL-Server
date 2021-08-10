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
import yll.entity.ActivitiesAnswer;
import yll.mapper.ActivitiesAnswerMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesAnswerVo;
import yll.service.model.vo.ActivitiesQuestionsVo;

import java.util.List;

/**
 * 活动竞赛答案
 */
@Transactional
@Service
public class ActivitiesAnswerService {

    // ==============================Fields===========================================
    @Autowired
    private ActivitiesAnswerMapper activitiesAnswerMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(ActivitiesAnswer vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        ActivitiesAnswer entity = new ActivitiesAnswer();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getTargetId());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setAnswer(vo.getAnswer());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        activitiesAnswerMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        activitiesAnswerMapper.deleteById(id);
//        voRoleMapper.deleteByActivitiesAnswerId(id);   关联删除
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(ActivitiesAnswer vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        ActivitiesAnswer entity = activitiesAnswerMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setAnswer(vo.getAnswer());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesAnswerMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        ActivitiesAnswer entity = new ActivitiesAnswer();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        activitiesAnswerMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public ActivitiesAnswer getById(String id) {
        ActivitiesAnswer entity = activitiesAnswerMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesAnswer> pagedQuery(Pagination pagination, ActivitiesAnswer condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesAnswerMapper.findBy(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesAnswerVo> pagedQueryWithType(Pagination pagination, ActivitiesAnswerVo condition) {
        return MybatisHelper.selectPage(pagination, () -> activitiesAnswerMapper.findByWithType(condition));
    }

    /**
     * 查询全部
     * @param condition 查询条件
     */
    public List<ActivitiesAnswer> allQuery(ActivitiesAnswer condition) {
        return activitiesAnswerMapper.findBy(condition);
    }

    /**
     * 删除-根据问题删除
     * @param
     */
    public void deleteByCondition(ActivitiesAnswerVo condition) {
        activitiesAnswerMapper.deleteByCondition(condition);
    }

    /**
     * 批量新增（先删后增）
     * @param vo
     */
    public void insertBatch(ActivitiesQuestionsVo vo) {
        String targetId = vo.getId();
        if(StringUtils.isBlank(targetId)){
            throw ExceptionHelper.prompt("所属题不能为空");
        }
        ActivitiesAnswerVo aaVo = new ActivitiesAnswerVo();
        aaVo.setTargetId(targetId);
        deleteByCondition(aaVo);
        List<ActivitiesAnswerVo> answerList = vo.getAnswerList();
        for (ActivitiesAnswerVo aa :
                answerList) {
            aa.setTargetId(targetId);
            //布尔值转换
            if(Boolean.parseBoolean(aa.getAnswerFlag())){
                aa.setAnswer(YllConstants.ONE);
            } else{
                aa.setAnswer(YllConstants.ZERO);
            }
            insert(aa);
        }
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(ActivitiesAnswer vo) {
        String id = vo.getId();
        String content = vo.getContent();
        Integer answer = vo.getAnswer();

        if (StringUtils.isBlank(content)) {
            throw ExceptionHelper.prompt("选项不能为空");
        }
        if (null == answer) {
            throw ExceptionHelper.prompt("答案标识不能为空");
        }
    }
}
