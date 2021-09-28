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
import yll.common.standard.CommonAttributeUtil;
import yll.entity.CustomerWallet;
import yll.entity.CustomerRecharge;
import yll.mapper.CustomerRechargeMapper;
import yll.mapper.CustomerWalletMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerRechargeVo;

import java.util.List;

/**
 *  用户充值
 */
@Transactional
@Service
public class CustomerRechargeService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerRechargeMapper customerRechargeMapper;
    @Autowired
    private CustomerWalletMapper customerWalletMapper;
    @Autowired
    private CustomerWalletService customerWalletService;
    @Autowired
    private DicService dicService;
    @Autowired
    private AppSecuritys securitys;
    @Autowired
    private Securitys adminSecuritys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(CustomerRecharge vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerRecharge entity = new CustomerRecharge();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(principal.getCustomerId());
        entity.setPrice(vo.getPrice());

        entity.setOrderNumber(vo.getOrderNumber());
        entity.setOrderName(vo.getOrderName());

        entity.setSigns(vo.getSigns());
        entity.setPayType(vo.getPayType());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        customerRechargeMapper.insert(entity);

        //更改金额
        changePrice(principal.getCustomerId(), entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerRechargeMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerRechargeId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerRecharge vo) {
        validate(vo);

        Principal principal = adminSecuritys.getPrincipal();

        CustomerRecharge entity = customerRechargeMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setPrice(vo.getPrice());
        entity.setOrderNumber(vo.getOrderNumber());
        entity.setOrderName(vo.getOrderName());

        entity.setSigns(vo.getSigns());
        entity.setPayType(vo.getPayType());
        entity.setRemark(vo.getRemark());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerRechargeMapper.update(entity);

        //更改金额
        changePrice(vo.getTargetId(), entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerRecharge entity = new CustomerRecharge();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerRechargeMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerRecharge getById(String id) {
        CustomerRecharge entity = customerRechargeMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerRecharge> pagedQuery(Pagination pagination, CustomerRecharge condition) {
        return MybatisHelper.selectPage(pagination, () -> customerRechargeMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerRecharge> pagedQueryWithName(Pagination pagination, CustomerRecharge condition) {
        return MybatisHelper.selectPage(pagination, () -> customerRechargeMapper.findByWithName(condition));
    }

    /**
     * 分页查询
     * @param condition 查询条件
     * @return 分页结果
     */
    public Integer findSum(CustomerRecharge condition) {
        return customerRechargeMapper.findSum(condition);
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerRechargeVo> getAppList(Pagination pagination, CustomerRechargeVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerRechargeMapper.getAppList(condition));
    }

    /**
     * 完成次数
     * @param condition 查询条件
     * @return
     */
    public CustomerRechargeVo getCompletions(CustomerRechargeVo condition) {
        return customerRechargeMapper.getCompletions(condition);
    }


    // ==============================ToolMethods======================================
    /** 更改金额 */
    private void changePrice(String customerId, CustomerRecharge entity) {
        if(StringUtils.isBlank(customerId)){
            throw ExceptionHelper.prompt("缺少用户唯一标识");
        }
        CustomerWallet cp = new CustomerWallet();
        cp.setTargetId(customerId);
        List<CustomerWallet> list = customerWalletMapper.findBy(cp);
        //TODO
        //1、查询是否存在钱包、流水明细
        //2、修改流水明细信息（更新微信渠道订单号（已存在流水明细情况下））、新增流水明细信息（无流水明细情况下，保存订单号、金额等）
        //3、支付成功回调后，修改流水明细、钱包金额

        if(null == list || list.size() == 0){
            cp.setPrice(YllConstants.ZERO);
            //新增钱包
            customerWalletService.insert(cp);
            //新增流水

        } else{
            cp = list.get(0);
            //customerWalletService.calculationPrice(cp, entity);
        }
    }

    /** 验证数据 */
    private void validate(CustomerRecharge vo) {
        String id = vo.getId();
        Integer signs = vo.getSigns();
        String targetId = vo.getTargetId();
        Integer price = vo.getPrice();

        if (StringUtils.isEmpty(targetId)) {
            throw ExceptionHelper.prompt("归属用户不能为空");
        }
        if (signs == null) {
            throw ExceptionHelper.prompt("加减不能为空");
        }
        if (price == null) {
            throw ExceptionHelper.prompt("金额不能为空");
        }
    }

}
