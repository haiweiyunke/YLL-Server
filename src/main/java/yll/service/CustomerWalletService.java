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
import yll.entity.CustomerWallet;
import yll.entity.CustomerWalletDetails;
import yll.mapper.CustomerWalletMapper;
import yll.service.model.YllConstants;

import java.util.List;

/**
 *  用户钱包
 */
@Transactional
@Service
public class CustomerWalletService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerWalletMapper customerWalletMapper;
    @Autowired
    private AppSecuritys securitys;
    @Autowired
    private Securitys adminSecuritys;

    // ==============================Methods==========================================
    /**
     * 新增-外部调用
     * @param
     */
    public void init(Customer entity) {
        String customerId = entity.getId();
        CustomerWallet vo = new CustomerWallet();
        vo.setTargetId(customerId);
        vo.setPrice(YllConstants.ZERO);
        insert(vo);
    }

    /**
     * 新增
     * @param
     */
    public void insert(CustomerWallet vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerWallet entity = new CustomerWallet();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getTargetId());
        entity.setPrice(vo.getPrice());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        customerWalletMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerWalletMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerWalletId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerWallet vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerWallet entity = customerWalletMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setPrice(vo.getPrice());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerWalletMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = adminSecuritys.getPrincipal();
        CustomerWallet entity = new CustomerWallet();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerWalletMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerWallet getById(String id) {
        CustomerWallet entity = customerWalletMapper.getById(id);
        return entity;
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerWallet getByTargetId(CustomerWallet condition) {
        List<CustomerWallet> list = customerWalletMapper.findBy(condition);
        if(null == list || list.size() == 0){
            String customerId = AppSecuritysUtil.getCustomerId();
            CustomerWallet entity = new CustomerWallet();
            entity.setTargetId(customerId);
            entity.setPrice(YllConstants.ZERO);
            this.insert(entity);
            return entity;
        }
        return list.get(0);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerWallet> pagedQuery(Pagination pagination, CustomerWallet condition) {
        return MybatisHelper.selectPage(pagination, () -> customerWalletMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerWallet> pagedQueryWithName(Pagination pagination, CustomerWallet condition) {
        return MybatisHelper.selectPage(pagination, () -> customerWalletMapper.findByWithName(condition));
    }

    /**
     * 计算金额
     */
    public void calculationPrice(CustomerWallet cp, CustomerWalletDetails cpd){
        Integer price = cp.getPrice();
        if(YllConstants.ZERO.equals(cpd.getSigns())){
            //0-减，1-加
            price = price - cpd.getPrice();
        } else {
            price = price + cpd.getPrice();
        }
        cp.setPrice(price);
        customerWalletMapper.update(cp);
    }

    /**
     * 查询(App使用)
     * @param
     * @return 实体
     */
    public CustomerWallet getAppDetail(CustomerWallet condition) {
        condition = customerWalletMapper.getAppDetail(condition);
        return condition;
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerWallet vo) {
        String id = vo.getId();
        String targetId = vo.getTargetId();
        Integer price = vo.getPrice();

        if (StringUtils.isEmpty(targetId)) {
            throw ExceptionHelper.prompt("归属用户不能为空");
        }
        if (price == null) {
            throw ExceptionHelper.prompt("用户钱包不能为空");
        }
    }

}
