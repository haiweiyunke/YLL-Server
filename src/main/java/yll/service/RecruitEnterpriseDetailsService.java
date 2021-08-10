package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.lang.DateUtil;
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
import yll.entity.RecruitEnterpriseDetails;
import yll.mapper.CommentMapper;
import yll.mapper.RecruitEnterpriseDetailsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.RecruitEnterpriseDetailsVo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 招聘信息
 */
@Transactional
@Service
public class RecruitEnterpriseDetailsService {

    // ==============================Fields===========================================
    @Autowired
    private RecruitEnterpriseDetailsMapper recruitEnterpriseDetailsMapper;
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
    public void insert(RecruitEnterpriseDetails vo) {

        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitEnterpriseDetails entity = new RecruitEnterpriseDetails();
        IdHelper.setIfEmptyId(entity);

        entity.setCid(vo.getCid());
        entity.setName(vo.getName());
        entity.setProvince(vo.getProvince());
        entity.setCity(vo.getCity());
        entity.setEducation(vo.getEducation());
        entity.setSalary(vo.getSalary());
        entity.setJobDescription(vo.getJobDescription());
        entity.setRecruitDescription(vo.getRecruitDescription());
        entity.setQuantity(vo.getQuantity());
        entity.setWorkExperience(vo.getWorkExperience());
        entity.setWorkNature(vo.getWorkNature());
        entity.setIndustry(vo.getIndustry());

        //30天有效期
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 30);
        Date date = cal.getTime();
        entity.setEndTime(date);

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
        recruitEnterpriseDetailsMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        recruitEnterpriseDetailsMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(RecruitEnterpriseDetails vo) {
        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitEnterpriseDetails entity = recruitEnterpriseDetailsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setCid(vo.getCid());
        entity.setName(vo.getName());
        entity.setProvince(vo.getProvince());
        entity.setCity(vo.getCity());
        entity.setEducation(vo.getEducation());
        entity.setSalary(vo.getSalary());
        entity.setJobDescription(vo.getJobDescription());
        entity.setRecruitDescription(vo.getRecruitDescription());
        entity.setQuantity(vo.getQuantity());
        entity.setWorkExperience(vo.getWorkExperience());
        entity.setWorkNature(vo.getWorkNature());
        entity.setIndustry(vo.getIndustry());
//        entity.setEndTime(vo.getEndTime());

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
        recruitEnterpriseDetailsMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public RecruitEnterpriseDetails getById(String id) {
        RecruitEnterpriseDetails entity = recruitEnterpriseDetailsMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        RecruitEnterpriseDetails entity = new RecruitEnterpriseDetails();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        recruitEnterpriseDetailsMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitEnterpriseDetails findByCondition(RecruitEnterpriseDetailsVo condition) {
        return recruitEnterpriseDetailsMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitEnterpriseDetails> pagedQuery(Pagination pagination, RecruitEnterpriseDetails condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitEnterpriseDetailsMapper.findBy(condition));
    }


    /**
     * 条件查询列表
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<RecruitEnterpriseDetails> findBy(RecruitEnterpriseDetailsVo condition) {
        return recruitEnterpriseDetailsMapper.findBy(condition);
    }


    /**
     * 详情查询-App
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitEnterpriseDetailsVo getAppDetails(RecruitEnterpriseDetailsVo condition) {
        return recruitEnterpriseDetailsMapper.getAppDetails(condition);
    }


    /**
     * 列表查询-App
     * @param condition 查询条件
     */
    public List<RecruitEnterpriseDetailsVo> getAppList(RecruitEnterpriseDetailsVo condition) {
        return recruitEnterpriseDetailsMapper.getAppList(condition);
    }

    /**
     * 列表查询-招聘广场-App
     * @param condition 查询条件
     */
    public Page<RecruitEnterpriseDetailsVo> pagedQueryGetIndexAppList(Pagination pagination, RecruitEnterpriseDetailsVo condition) {
        //拼接order by   orderBy
        String orderBy = "";
        Integer salaryOrder = condition.getSalaryOrder();
        Integer createdTimeOrder = condition.getCreatedTimeOrder();

        //薪酬
        if(null != salaryOrder){
            if(1 == salaryOrder){
                orderBy += " d7.ordinal,";
            } else if(2 == salaryOrder){
                orderBy += " d7.ordinal desc,";
            }
        }
        //创建时间
        if(null != createdTimeOrder){
            if(1 == createdTimeOrder){
                orderBy += " m.created_time,";
            } else if(2 == createdTimeOrder){
                orderBy += " m.created_time desc,";
            }
        }
        if(StringUtils.isBlank(orderBy)){
            orderBy += " m.created_time desc";
        } else{
            orderBy = orderBy.substring(0, orderBy.length()-1);
        }
        condition.setOrderBy(orderBy);
        return MybatisHelper.selectPage(pagination, () -> recruitEnterpriseDetailsMapper.getIndexAppList(condition));
    }


    /**
     * 分页查询-获取达人申请列表-App
     * @param condition 查询条件
     */
    public Page<RecruitEnterpriseDetailsVo> pagedQueryGetAppCelebrityApplyList(Pagination pagination, RecruitEnterpriseDetailsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitEnterpriseDetailsMapper.getAppCelebrityApplyList(condition));
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitEnterpriseDetailsVo> pagedQueryAppList(Pagination pagination, RecruitEnterpriseDetailsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitEnterpriseDetailsMapper.getAppList(condition));
    }


    /**
     * 定时任务列表查询
     * @param condition 查询条件
     */
    public List<RecruitEnterpriseDetailsVo> findBySchedule(RecruitEnterpriseDetailsVo condition) {
        return recruitEnterpriseDetailsMapper.findBySchedule(condition);
    }
    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(RecruitEnterpriseDetails vo) {
        String id = vo.getId();

    }


}
