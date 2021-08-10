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
import yll.common.standard.CommonAttributeUtil;
import yll.entity.Slide;
import yll.mapper.SlideMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.SlideVo;

import java.util.List;


/**
 * 混合轮播图
 */
@Transactional
@Service
public class SlideService {

    // ==============================Fields===========================================
    @Autowired
    private SlideMapper slideMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Slide insert(Slide vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Slide entity = new Slide();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setTargetId(vo.getTargetId());
        entity.setName(vo.getName());
        entity.setImg(vo.getImg());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());

        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);

        CommonAttributeUtil.setCreated(entity, principal);
        slideMapper.insert(entity);

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
        slideMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public Slide update(Slide vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Slide entity = slideMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setName(vo.getName());
        entity.setImg(vo.getImg());
        entity.setType(vo.getType());
        entity.setOrdinal(vo.getOrdinal());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        slideMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Slide entity = new Slide();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        slideMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Slide getById(String id) {
        Slide entity = slideMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Slide> pagedQuery(Pagination pagination, Slide condition) {
        return MybatisHelper.selectPage(pagination, () -> slideMapper.findBy(condition));
    }


    /**
     * 查询-app认证详情
     * @param
     * @return 实体
     */
    public List<SlideVo> getAppList(SlideVo condition) {
        return slideMapper.getAppList(condition);
    }


    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Slide vo) {
        String id = vo.getId();
    }

}
