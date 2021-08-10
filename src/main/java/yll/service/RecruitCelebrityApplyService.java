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
import yll.entity.RecruitCelebrityApply;
import yll.mapper.CommentMapper;
import yll.mapper.RecruitCelebrityApplyMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.RecruitCelebrityApplyVo;

import java.util.List;


/**
 * 聘达人申请
 */
@Transactional
@Service
public class RecruitCelebrityApplyService {

    // ==============================Fields===========================================
    @Autowired
    private RecruitCelebrityApplyMapper recruitCelebrityApplyMapper;
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
    public void insert(RecruitCelebrityApply vo) {

        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitCelebrityApply entity = new RecruitCelebrityApply();
        IdHelper.setIfEmptyId(entity);

        entity.setCid(vo.getCid());
        entity.setRedid(vo.getRedid());

        entity.setType(vo.getType());
        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, appPrincipal);
        /*if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
        }*/
        recruitCelebrityApplyMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        recruitCelebrityApplyMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(RecruitCelebrityApply vo) {
        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitCelebrityApply entity = recruitCelebrityApplyMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setCid(vo.getCid());
        entity.setRedid(vo.getRedid());

        entity.setType(vo.getType());
        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, appPrincipal);
        recruitCelebrityApplyMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public RecruitCelebrityApply getById(String id) {
        RecruitCelebrityApply entity = recruitCelebrityApplyMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        RecruitCelebrityApply entity = new RecruitCelebrityApply();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        recruitCelebrityApplyMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitCelebrityApply findByCondition(RecruitCelebrityApplyVo condition) {
        return recruitCelebrityApplyMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitCelebrityApply> pagedQuery(Pagination pagination, RecruitCelebrityApply condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitCelebrityApplyMapper.findBy(condition));
    }


    /**
     * 条件查询列表
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<RecruitCelebrityApply> findBy(RecruitCelebrityApplyVo condition) {
        return recruitCelebrityApplyMapper.findBy(condition);
    }


    /**
     * 详情查询-App
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitCelebrityApplyVo getAppDetails(RecruitCelebrityApplyVo condition) {
        return recruitCelebrityApplyMapper.getAppDetails(condition);
    }


    /**
     * 列表查询-App
     * @param condition 查询条件
     */
    public List<RecruitCelebrityApplyVo> getAppList(RecruitCelebrityApplyVo condition) {
        return recruitCelebrityApplyMapper.getAppList(condition);
    }

    /**
     * 招聘信息-申请人列表-App
     * @param condition 查询条件
     */
    public List<RecruitCelebrityApplyVo> getCelebrityApplyList(RecruitCelebrityApplyVo condition) {
        return recruitCelebrityApplyMapper.getCelebrityApplyList(condition);
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitCelebrityApplyVo> pagedQueryAppList(Pagination pagination, RecruitCelebrityApplyVo condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitCelebrityApplyMapper.getAppList(condition));
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(RecruitCelebrityApply vo) {
        String id = vo.getId();

    }


}
