package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
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
import yll.entity.Dynamic;
import yll.mapper.CommentMapper;
import yll.mapper.DynamicMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.DynamicVo;


/**
 * 动态
 */
@Transactional
@Service
public class DynamicService {

    // ==============================Fields===========================================
    @Autowired
    private DynamicMapper dynamicMapper;
       @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Dynamic vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        Dynamic entity = new Dynamic();
        IdHelper.setIfEmptyId(entity);

        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setWords(vo.getWords());
        entity.setVisibleScope(vo.getVisibleScope());
        entity.setShare(YllConstants.ZERO);
        entity.setLikes(YllConstants.ZERO);
        entity.setCollects(YllConstants.ZERO);
        entity.setComments(YllConstants.ZERO);

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        dynamicMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        dynamicMapper.deleteById(id);
        commentMapper.deleteByDynamicId(id);   //关联删除评论
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(Dynamic vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        Dynamic entity = dynamicMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setImage(vo.getImage());
        entity.setVideo(vo.getVideo());
        entity.setWords(vo.getWords());
        entity.setVisibleScope(vo.getVisibleScope());

        if(null != vo.getLikes()){
            entity.setLikes(vo.getLikes());
        }
        if(null != vo.getCollects()){
            entity.setCollects(vo.getCollects());
        }
        if(null != vo.getShare()){
            entity.setShare(vo.getShare());
        }
        if(null != vo.getComments()){
            entity.setComments(vo.getComments());
        }

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        dynamicMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public Dynamic getById(String id) {
        Dynamic entity = dynamicMapper.getById(id);
        return entity;
    }

    /**
     * 详情查询-条件
     * @param condition 查询条件
     * @return 详情结果
     */
    public Dynamic findByCondition(DynamicVo condition) {
        return dynamicMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Dynamic> pagedQuery(Pagination pagination, Dynamic condition) {
        return MybatisHelper.selectPage(pagination, () -> dynamicMapper.findBy(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<DynamicVo> getAppList(Pagination pagination, DynamicVo condition) {
        return MybatisHelper.selectPage(pagination, () -> dynamicMapper.getAppList(condition));
    }

    /**
     * 详情查询-App
     * @param condition 查询条件
     * @return 详情结果
     */
    public DynamicVo getAppDetail(DynamicVo condition) {
        return dynamicMapper.getAppDetail(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Dynamic vo) {
        String id = vo.getId();

    }

}
