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
import yll.entity.CustomerMessages;
import yll.mapper.CustomerMessagesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerMessagesVo;

/**
 *  我的消息
 */
@Transactional
@Service
public class CustomerMessagesService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerMessagesMapper customerMessagesMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(CustomerMessages vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        CustomerMessages entity = new CustomerMessages();
        IdHelper.setIfEmptyId(entity);

        entity.setName(vo.getName());
        entity.setProfile(vo.getProfile());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        customerMessagesMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerMessagesMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerMessagesId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerMessages vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        CustomerMessages entity = customerMessagesMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setName(vo.getName());
        entity.setProfile(vo.getProfile());
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
        customerMessagesMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        CustomerMessages entity = new CustomerMessages();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerMessagesMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerMessages getById(String id) {
        CustomerMessages entity = customerMessagesMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerMessages> pagedQuery(Pagination pagination, CustomerMessages condition) {
        return MybatisHelper.selectPage(pagination, () -> customerMessagesMapper.findBy(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerMessagesVo> getAppList(Pagination pagination, CustomerMessagesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerMessagesMapper.getAppList(condition));
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public CustomerMessagesVo getAppDetail(CustomerMessagesVo condition) {
        return customerMessagesMapper.getAppDetail(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerMessages vo) {
        String id = vo.getId();
        String name = vo.getName();
        String content = vo.getContent();

        if (StringUtils.isEmpty(name)) {
            throw ExceptionHelper.prompt("标题不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw ExceptionHelper.prompt("内容不能为空");
        }
    }
}
