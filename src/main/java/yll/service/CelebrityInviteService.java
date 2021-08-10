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
import yll.common.security.app.AppSecuritysUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.Customer;
import yll.entity.CelebrityInvite;
import yll.mapper.CustomerMapper;
import yll.mapper.CelebrityInviteMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesVo;
import yll.service.model.vo.CelebrityInviteVo;


/**
 * 达人邀请信息
 */
@Transactional
@Service
public class CelebrityInviteService {

    // ==============================Fields===========================================
    @Autowired
    private CelebrityInviteMapper celebrityInviteMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private Securitys securitys;
    @Autowired
    private AppSecuritys appSecuritys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public CelebrityInvite insert(CelebrityInvite vo) {

        validate(vo);

//        Principal principal = securitys.getPrincipal();
        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        CelebrityInvite entity = new CelebrityInvite();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setCelebrityId(vo.getCelebrityId());
        entity.setMcnId(vo.getMcnId());

        entity.setRemark(vo.getRemark());

        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setState(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, appPrincipal);
        celebrityInviteMapper.insert(entity);

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
        celebrityInviteMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public CelebrityInvite update(CelebrityInvite vo) {
        validate(vo);

        AppPrincipal appPrincipal = appSecuritys.getAppPrincipal();

        CelebrityInvite entity = celebrityInviteMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, appPrincipal);
        celebrityInviteMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        CelebrityInvite entity = new CelebrityInvite();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        celebrityInviteMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CelebrityInvite getById(String id) {
        CelebrityInvite entity = celebrityInviteMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @paramgetAppList
     * @return 实体
     */
    public CelebrityInvite getByCondition(CelebrityInvite condition) {
        return celebrityInviteMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CelebrityInvite> pagedQuery(Pagination pagination, CelebrityInvite condition) {
        return MybatisHelper.selectPage(pagination, () -> celebrityInviteMapper.findBy(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CelebrityInvite> getAppListCelebrity(Pagination pagination, CelebrityInvite condition) {
        return MybatisHelper.selectPage(pagination, () -> celebrityInviteMapper.getAppListCelebrity(condition));
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CelebrityInvite vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
