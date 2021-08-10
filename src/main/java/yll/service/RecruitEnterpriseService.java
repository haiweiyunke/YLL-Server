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
import yll.entity.RecruitEnterprise;
import yll.mapper.CommentMapper;
import yll.mapper.RecruitEnterpriseMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.RecruitEnterpriseVo;

import java.util.List;


/**
 * 聘达人简历
 */
@Transactional
@Service
public class RecruitEnterpriseService {

    // ==============================Fields===========================================
    @Autowired
    private RecruitEnterpriseMapper recruitEnterpriseMapper;
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
    public void insert(RecruitEnterprise vo) {

        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitEnterprise entity = new RecruitEnterprise();
        IdHelper.setIfEmptyId(entity);

        entity.setCid(vo.getCid());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setCreditCode(vo.getCreditCode());
        entity.setName(vo.getName());
        entity.setEstablishTime(vo.getEstablishTime());
        entity.setRegisteredCapital(vo.getRegisteredCapital());
        entity.setSuperintendent(vo.getSuperintendent());
        entity.setIndustry(vo.getIndustry());
        entity.setDescription(vo.getDescription());
        entity.setStaff(vo.getStaff());

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
        recruitEnterpriseMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        recruitEnterpriseMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(RecruitEnterprise vo) {
        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitEnterprise entity = recruitEnterpriseMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setCid(vo.getCid());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setCreditCode(vo.getCreditCode());
        entity.setName(vo.getName());
        entity.setEstablishTime(vo.getEstablishTime());
        entity.setRegisteredCapital(vo.getRegisteredCapital());
        entity.setSuperintendent(vo.getSuperintendent());
        entity.setIndustry(vo.getIndustry());
        entity.setDescription(vo.getDescription());
        entity.setStaff(vo.getStaff());

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
        recruitEnterpriseMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public RecruitEnterprise getById(String id) {
        RecruitEnterprise entity = recruitEnterpriseMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        RecruitEnterprise entity = new RecruitEnterprise();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        recruitEnterpriseMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitEnterprise findByCondition(RecruitEnterpriseVo condition) {
        return recruitEnterpriseMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitEnterprise> pagedQuery(Pagination pagination, RecruitEnterprise condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitEnterpriseMapper.findBy(condition));
    }


    /**
     * 条件查询列表
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<RecruitEnterprise> findBy(RecruitEnterpriseVo condition) {
        return recruitEnterpriseMapper.findBy(condition);
    }


    /**
     * 详情查询-App
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitEnterpriseVo getAppDetails(RecruitEnterpriseVo condition) {
        return recruitEnterpriseMapper.getAppDetails(condition);
    }


    /**
     * 列表查询-App
     * @param condition 查询条件
     */
    public List<RecruitEnterpriseVo> getAppList(RecruitEnterpriseVo condition) {
        return recruitEnterpriseMapper.getAppList(condition);
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitEnterpriseVo> pagedQueryAppList(Pagination pagination, RecruitEnterpriseVo condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitEnterpriseMapper.getAppList(condition));
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(RecruitEnterprise vo) {
        String id = vo.getId();

    }


}
