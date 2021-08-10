package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
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
import yll.entity.CustomerActivities;
import yll.entity.CustomerActivitiesAnswer;
import yll.mapper.CustomerActivitiesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesVo;
import yll.service.model.vo.CustomerActivitiesVo;

import java.util.List;


/**
 *  我的活动
 */
@Transactional
@Service
public class CustomerActivitiesService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerActivitiesMapper customerActivitiesMapper;
    @Autowired
    private CustomerActivitiesAnswerService customerActivitiesAnswerService;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public CustomerActivities insert(CustomerActivities vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerActivities entity = new CustomerActivities();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setTargetId(principal.getCustomerId());
        entity.setActivitiesId(vo.getActivitiesId());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        customerActivitiesMapper.insert(entity);
        return entity;
    }

    /**
     * 新增(带活动竞赛)
     * @param
     */
    public void insertWithQuestions(CustomerActivities ca, CustomerActivitiesAnswer caa) {
        //收藏活动
        insert(ca);
        if (caa != null) {
            //新增竞赛活动结果
            caa.setTargetId(ca.getId());
            customerActivitiesAnswerService.insert(caa);
        }
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerActivitiesMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerActivitiesId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerActivities vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerActivities entity = customerActivitiesMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(principal.getCustomerId());
        entity.setActivitiesId(vo.getActivitiesId());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerActivitiesMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerActivities entity = new CustomerActivities();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerActivitiesMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerActivities getById(String id) {
        CustomerActivities entity = customerActivitiesMapper.getById(id);
        return entity;
    }

    /**
     * 条件查询
     * @param
     * @return 实体
     */
    public CustomerActivities getByCondition(CustomerActivities condition) {
        List<CustomerActivities> list = customerActivitiesMapper.findBy(condition);
        if (list != null && list.size() > 0) {
            CustomerActivities entity = list.get(0);
            return entity;
        }
        return null;
    }

    /**
     * 条件查询
     * @param
     * @return 实体
     */
    public Integer getCountByCondition(CustomerActivities condition) {
        List<CustomerActivities> list = customerActivitiesMapper.findBy(condition);
        if (list != null && list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerActivities> pagedQuery(Pagination pagination, CustomerActivities condition) {
        return MybatisHelper.selectPage(pagination, () -> customerActivitiesMapper.findBy(condition));
    }

  /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<ActivitiesVo> getAppList(Pagination pagination, CustomerActivitiesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerActivitiesMapper.getAppList(condition));
    }

    /**
     *  查询用户是否有参与过活动
     * @param activitiesId
     * @return
     */
    public CustomerActivities getCustomerActivities(String activitiesId) {
        //用户是否参加
        String customerId = AppSecuritysUtil.getCustomerId();
        CustomerActivities vo = new CustomerActivities();
        vo.setActivitiesId(activitiesId);
        vo.setTargetId(customerId);
        vo = getByCondition(vo);
        return vo;
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerActivities vo) {
        String id = vo.getId();
        String activitiesId = vo.getActivitiesId();

        if (StringUtils.isEmpty(activitiesId)) {
            throw ExceptionHelper.prompt("活动id不能为空");
        }
    }
}
