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
import yll.common.security.app.AppSecuritysUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.Comment;
import yll.entity.CustomerCollects;
import yll.entity.Dynamic;
import yll.mapper.CommentMapper;
import yll.mapper.DynamicMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CommentVo;

import java.util.List;


/**
 * 评论
 */
@Transactional
@Service
public class CommentService {

    // ==============================Fields===========================================
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Comment vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        Comment entity = new Comment();
        IdHelper.setIfEmptyId(entity);

        entity.setDynamicId(vo.getDynamicId());
        entity.setCommentId(vo.getCommentId());

        entity.setRemark(vo.getRemark());
        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        commentMapper.insert(entity);

        //评论数+1
        Dynamic dynamic = dynamicMapper.getById(vo.getDynamicId());
        dynamic.setComments(dynamic.getComments() + 1);
        dynamicMapper.update(dynamic);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        commentMapper.deleteById(id);
//        voRoleMapper.deleteByCommentId(id);   关联删除
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(Comment vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        Comment entity = commentMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setDynamicId(vo.getDynamicId());
        entity.setCommentId(vo.getCommentId());

        entity.setRemark(vo.getRemark());

        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        commentMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public Comment getById(String id) {
        Comment entity = commentMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Comment> pagedQuery(Pagination pagination, Comment condition) {
        return MybatisHelper.selectPage(pagination, () -> commentMapper.findBy(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CommentVo> getAppList(Pagination pagination, CommentVo condition) {
        return MybatisHelper.selectPage(pagination, () -> commentMapper.getAppList(condition));
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Comment vo) {
        String id = vo.getId();

    }

}
