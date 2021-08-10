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
import yll.entity.Shops;
import yll.mapper.ShopsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ShopsVo;

import java.util.List;

/**
 *  店铺
 */
@Transactional
@Service
public class ShopsService {

    // ==============================Fields===========================================
    @Autowired
    private ShopsMapper shopsMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Shops insert(Shops vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Shops entity = new Shops();
        IdHelper.setIfEmptyId(entity);

        entity.setName(vo.getName());
        entity.setEid(vo.getEid());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setPicture(vo.getPicture());
        entity.setVideo(vo.getVideo());
        entity.setProfile(vo.getProfile());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());

        entity.setSales(vo.getSales());
        entity.setOther(vo.getOther());

        entity.setSlide(YllConstants.ZERO);
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、App新增
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
        }
        shopsMapper.insert(entity);
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
        shopsMapper.deleteById(id);
//        voRoleMapper.deleteByShopsId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(Shops vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Shops entity = shopsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setName(vo.getName());
        entity.setEid(vo.getEid());
        entity.setCover(vo.getCover());
        entity.setImage(vo.getImage());
        entity.setPicture(vo.getPicture());
        entity.setVideo(vo.getVideo());
        entity.setProfile(vo.getProfile());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());

        entity.setSales(vo.getSales());
        entity.setOther(vo.getOther());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        }
        if(null != vo.getOrdinal()){
            entity.setOrdinal(vo.getOrdinal());
        }

        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        shopsMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Shops entity = new Shops();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        shopsMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Shops getById(String id) {
        Shops entity = shopsMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Shops> pagedQuery(Pagination pagination, Shops condition) {
        return MybatisHelper.selectPage(pagination, () -> shopsMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Shops> pagedQueryWithType(Pagination pagination, Shops condition) {
        return MybatisHelper.selectPage(pagination, () -> shopsMapper.findByWithType(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ShopsVo> getAppList(Pagination pagination, ShopsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> shopsMapper.getAppList(condition));
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public ShopsVo getAppDetail(ShopsVo condition) {
        return shopsMapper.getAppDetail(condition);
    }

    /**
     *  轮播
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Shops> findBySlide(Shops condition) {
        return shopsMapper.findBySlide(condition);
    }

    /**
     * 轮播修改
     * @param
     * @return 分页结果
     */
    public void slide(ShopsVo condition) {
        String oldShopsId = condition.getOldSlideId();
        if(StringUtils.isNotBlank(oldShopsId)){
            Shops oldShops = shopsMapper.getById(oldShopsId);
            oldShops.setSlide(0);
            oldShops.setOrdinal(YllConstants.LAST);
            shopsMapper.update(oldShops);
        }
        condition.setSlide(YllConstants.ONE);
        shopsMapper.update(condition);
    }


    /**
     * 查询所有2
     * @param condition 查询条件
     * @return 结果
     */
    public List<Shops> all(Shops condition) {
        return shopsMapper.findBy(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Shops vo) {
        String id = vo.getId();
        String name = vo.getName();
        String content = vo.getContent();
        String type = vo.getType();

       /* if (StringUtils.isEmpty(name)) {
            throw ExceptionHelper.prompt("名称不能为空");
        }*/
    }
}
