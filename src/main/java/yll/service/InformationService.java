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
import yll.entity.Information;
import yll.entity.Slide;
import yll.mapper.InformationMapper;
import yll.mapper.SlideMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.InformationVo;

import java.util.List;

/**
 * 资讯
 */
@Transactional
@Service
public class InformationService {

    // ==============================Fields===========================================
    @Autowired
    private InformationMapper informationMapper;
    @Autowired
    private SlideMapper slideMapper;

    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Information vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Information entity = new Information();
        IdHelper.setIfEmptyId(entity);

        entity.setName(vo.getName());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setContent(vo.getContent());
        entity.setStartTime(vo.getStartTime());
        entity.setEndTime(vo.getEndTime());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());

        entity.setSlide(YllConstants.ZERO);
        entity.setShare(YllConstants.ZERO);
        entity.setLikes(YllConstants.ZERO);
        entity.setCollects(YllConstants.ZERO);
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        informationMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        informationMapper.deleteById(id);
//        voRoleMapper.deleteByInformationId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(Information vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Information entity = informationMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setName(vo.getName());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setContent(vo.getContent());
        entity.setStartTime(vo.getStartTime());
        entity.setEndTime(vo.getEndTime());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());

        if(null != vo.getLikes()){
            entity.setLikes(vo.getLikes());
        }
        if(null != vo.getCollects()){
            entity.setCollects(vo.getCollects());
        }
        if(null != vo.getShare()){
            entity.setShare(vo.getShare());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        }

        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        informationMapper.update(entity);
        //修改轮播图
        Slide svo = new Slide();
        svo.setTargetId(entity.getId());
        svo = slideMapper.findByCondition(svo);
        if(null != svo && StringUtils.isNotBlank(entity.getCover())){
            svo.setImg(entity.getCover());
        }
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Information entity = new Information();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        informationMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Information getById(String id) {
        Information entity = informationMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Information> pagedQuery(Pagination pagination, Information condition) {
        return MybatisHelper.selectPage(pagination, () -> informationMapper.findBy(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Information> pagedQueryWithType(Pagination pagination, Information condition) {
        return MybatisHelper.selectPage(pagination, () -> informationMapper.findByWithType(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<InformationVo> getAppList(Pagination pagination, InformationVo condition) {
        return MybatisHelper.selectPage(pagination, () -> informationMapper.getAppList(condition));
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public InformationVo getAppDetail(InformationVo condition) {
        return informationMapper.getAppDetail(condition);
    }

    /**
     *  轮播
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Information> findBySlide(Information condition) {
        return informationMapper.findBySlide(condition);
    }

    /**
     * 轮播修改
     * @param
     * @return 分页结果
     */
    public void slide(InformationVo condition) {
        String oldInformationId = condition.getOldInformationId();
        if(StringUtils.isNotBlank(oldInformationId)){
            Information oldInformation = informationMapper.getById(oldInformationId);
            oldInformation.setSlide(0);
            oldInformation.setOrdinal(YllConstants.LAST);
            informationMapper.update(oldInformation);
        }
        condition.setSlide(YllConstants.ONE);
        informationMapper.update(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Information vo) {
        String id = vo.getId();
        String name = vo.getName();
        String content = vo.getContent();
        String type = vo.getType();

        if (StringUtils.isEmpty(name)) {
            throw ExceptionHelper.prompt("标题不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw ExceptionHelper.prompt("内容不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw ExceptionHelper.prompt("类型不能为空");
        }
    }
}
