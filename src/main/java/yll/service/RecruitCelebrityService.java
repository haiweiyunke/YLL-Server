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
import yll.entity.RecruitCelebrity;
import yll.mapper.CommentMapper;
import yll.mapper.RecruitCelebrityMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.RecruitCelebrityVo;

import java.util.List;


/**
 * 聘达人简历
 */
@Transactional
@Service
public class RecruitCelebrityService {

    // ==============================Fields===========================================
    @Autowired
    private RecruitCelebrityMapper recruitCelebrityMapper;
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
    public void insert(RecruitCelebrity vo) {

        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitCelebrity entity = new RecruitCelebrity();
        IdHelper.setIfEmptyId(entity);

        entity.setCid(vo.getCid());
        entity.setName(vo.getName());
        entity.setImage(vo.getImage());
        entity.setAge(vo.getAge());
        entity.setGender(vo.getGender());
        entity.setNation(vo.getNation());
        entity.setProvince(vo.getProvince());
        entity.setCity(vo.getCity());
        entity.setEducation(vo.getEducation());
        entity.setEmail(vo.getEmail());
        entity.setDescription(vo.getDescription());
        entity.setExperience(vo.getExperience());
        entity.setInterest(vo.getInterest());
        entity.setWorkNature(vo.getWorkNature());
        entity.setPhone(vo.getPhone());

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
        recruitCelebrityMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        recruitCelebrityMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(RecruitCelebrity vo) {
        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        RecruitCelebrity entity = recruitCelebrityMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setCid(vo.getCid());
        entity.setName(vo.getName());
        entity.setImage(vo.getImage());
        entity.setAge(vo.getAge());
        entity.setGender(vo.getGender());
        entity.setNation(vo.getNation());
        entity.setProvince(vo.getProvince());
        entity.setCity(vo.getCity());
        entity.setEducation(vo.getEducation());
        entity.setEmail(vo.getEmail());
        entity.setDescription(vo.getDescription());
        entity.setExperience(vo.getExperience());
        entity.setInterest(vo.getInterest());
        entity.setWorkNature(vo.getWorkNature());
        entity.setPhone(vo.getPhone());

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
        recruitCelebrityMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public RecruitCelebrity getById(String id) {
        RecruitCelebrity entity = recruitCelebrityMapper.getById(id);
        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        RecruitCelebrity entity = new RecruitCelebrity();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        recruitCelebrityMapper.update(entity);
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitCelebrity findByCondition(RecruitCelebrityVo condition) {
        return recruitCelebrityMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitCelebrity> pagedQuery(Pagination pagination, RecruitCelebrity condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitCelebrityMapper.findBy(condition));
    }


    /**
     * 条件查询列表
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<RecruitCelebrity> findBy(RecruitCelebrityVo condition) {
        return recruitCelebrityMapper.findBy(condition);
    }


    /**
     * 详情查询-App
     * @param condition 查询条件
     * @return 详情结果
     */
    public RecruitCelebrityVo getAppDetails(RecruitCelebrityVo condition) {
        return recruitCelebrityMapper.getAppDetails(condition);
    }


    /**
     * 列表查询-App
     * @param condition 查询条件
     */
    public List<RecruitCelebrityVo> getAppList(RecruitCelebrityVo condition) {
        return recruitCelebrityMapper.getAppList(condition);
    }


    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<RecruitCelebrityVo> pagedQueryAppList(Pagination pagination, RecruitCelebrityVo condition) {
        return MybatisHelper.selectPage(pagination, () -> recruitCelebrityMapper.getAppList(condition));
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(RecruitCelebrity vo) {
        String id = vo.getId();

    }


}
