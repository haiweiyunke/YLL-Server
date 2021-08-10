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
import yll.entity.Customer;
import yll.entity.Conferences;
import yll.entity.InternetCelebrity;
import yll.mapper.CustomerMapper;
import yll.mapper.ConferencesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ConferencesVo;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.TrainVo;

import java.util.List;


/**
 * 发布会信息
 */
@Transactional
@Service
public class ConferencesService {

    // ==============================Fields===========================================
    @Autowired
    private ConferencesMapper conferencesMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Conferences insert(Conferences vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();
        String userId = principal.getUserId();

        Conferences entity = new Conferences();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setIssuedTime(vo.getIssuedTime());
        entity.setTheme(vo.getTheme());
        entity.setMaterial(vo.getMaterial());
        entity.setPhotos(vo.getPhotos());
        entity.setType(vo.getType());
        entity.setVisible(vo.getVisible());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setReason(vo.getReason());

        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setEnabled(YllConstants.ONE);
        entity.setSlide(YllConstants.ZERO);
        entity.setExtension(YllConstants.ZERO);
        entity.setDeleted(YllConstants.ZERO);
        if(null != vo.getVisible()){
            entity.setVisible(vo.getVisible());
        } else{
            entity.setVisible(YllConstants.ONE);
        }
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
        }
        conferencesMapper.insert(entity);

        return entity;
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        conferencesMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public Conferences update(Conferences vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Conferences entity = conferencesMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setIssuedTime(vo.getIssuedTime());
        entity.setTheme(vo.getTheme());
        entity.setMaterial(vo.getMaterial());
        entity.setPhotos(vo.getPhotos());
        entity.setType(vo.getType());
        entity.setImage(vo.getImage());
        entity.setCover(vo.getCover());
        entity.setVideo(vo.getVideo());
        entity.setReason(vo.getReason());

        entity.setRemark(vo.getRemark());

        if(null != vo.getVisible()){
            entity.setVisible(vo.getVisible());
        } else{
            entity.setVisible(YllConstants.ONE);
        }
         if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
             entity.setState(YllConstants.ONE);
        }
      if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        } else{
              entity.setSlide(YllConstants.ZERO);
        }

        if(null != vo.getExtension()){
            entity.setExtension(vo.getExtension());
        } else{
              entity.setExtension(YllConstants.ZERO);
        }

//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        if(StringUtils.isBlank(vo.getModifier())){
            CommonAttributeUtil.setUpdated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            if(StringUtils.isNotBlank(vo.getCreator())){
                entity.setCreator(vo.getCreator());
            }
            entity.setModifier(vo.getModifier());
            entity.setModifiedTime(vo.getModifiedTime());
        }
        conferencesMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Conferences entity = new Conferences();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        conferencesMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Conferences getById(String id) {
        Conferences entity = conferencesMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public Conferences getByCondition(Conferences condition) {
        return conferencesMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Conferences> pagedQuery(Pagination pagination, Conferences condition) {
        return MybatisHelper.selectPage(pagination, () -> conferencesMapper.findBy(condition));
    }



    public Page<ConferencesVo> getAppList(Pagination pagination, ConferencesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> conferencesMapper.getAppList(condition));
    }

    /**
     *  轮播
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<ConferencesVo> findBySlide(ConferencesVo condition) {
        return conferencesMapper.findBySlide(condition);
    }

    /**
     * 轮播修改
     * @param
     */
    public void slide(ConferencesVo condition) {
        String oldSlideId = condition.getOldSlideId();
        if(StringUtils.isNotBlank(oldSlideId)){
            Conferences old = conferencesMapper.getById(oldSlideId);
            old.setSlide(0);
            old.setOrdinal(YllConstants.LAST);
            conferencesMapper.update(old);
        }
        condition.setSlide(YllConstants.ONE);
        conferencesMapper.update(condition);
    }

    /**
     * 分页查询-轮播图
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ConferencesVo> pagedQuerySlide(Pagination pagination, ConferencesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> conferencesMapper.findBySlide(condition));
    }

    /**
     * 分页查询-后台列表
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ConferencesVo> findByAdminList(Pagination pagination, ConferencesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> conferencesMapper.findByAdminList(condition));
    }


    /**
     *  推广
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<ConferencesVo> findByExtension(ConferencesVo condition) {
        return conferencesMapper.findByExtension(condition);
    }

    /**
     * 推广修改
     * @param
     */
    public void extension(ConferencesVo condition) {
        String oldExtensionId = condition.getOldExtensionId();
        if(StringUtils.isNotBlank(oldExtensionId)){
            Conferences old = conferencesMapper.getById(oldExtensionId);
            old.setExtension(0);
            old.setExtensionOrdinal(YllConstants.LAST);
            conferencesMapper.update(old);
        }
        condition.setExtension(YllConstants.ONE);
        conferencesMapper.update(condition);
    }


    /**
     * 分页查询-推广广告
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ConferencesVo> pagedQueryExtension(Pagination pagination, ConferencesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> conferencesMapper.findByExtension(condition));
    }


    /**
     * 查询-App
     * @param condition 查询条件
     */
    public ConferencesVo getAppDetail(ConferencesVo condition) {
        return conferencesMapper.getAppDetail(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Conferences vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
