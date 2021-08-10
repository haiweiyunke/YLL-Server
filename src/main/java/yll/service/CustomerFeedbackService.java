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
import yll.common.standard.CommonAttributeUtil;
import yll.entity.CustomerFeedback;
import yll.mapper.CustomerFeedbackMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerFeedbackVo;

/**
 *  意见反馈
 */
@Transactional
@Service
public class CustomerFeedbackService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerFeedbackMapper customerFeedbackMapper;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(CustomerFeedback vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerFeedback entity = new CustomerFeedback();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(principal.getCustomerId());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        customerFeedbackMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerFeedbackMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerFeedbackId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerFeedback vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerFeedback entity = customerFeedbackMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(principal.getCustomerId());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());


        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerFeedbackMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerFeedback getById(String id) {
        CustomerFeedback entity = customerFeedbackMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerFeedback> pagedQuery(Pagination pagination, CustomerFeedback condition) {
        return MybatisHelper.selectPage(pagination, () -> customerFeedbackMapper.findBy(condition));
    }

    /**
     * 分页查询-包含用户
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerFeedbackVo> findByCustomer(Pagination pagination, CustomerFeedbackVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerFeedbackMapper.findByCustomer(condition));
    }

    /**
     * 查询详情-包含用户
     * @param id 查询条件
     * @return 分页结果
     */
    public CustomerFeedbackVo getDetailByCustomer(String id) {
        return customerFeedbackMapper.getDetailByCustomer(id);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerFeedback vo) {
        String id = vo.getId();
        String content = vo.getContent();

        if (StringUtils.isEmpty(content)) {
            throw ExceptionHelper.prompt("内容不能为空");
        }
    }
}
