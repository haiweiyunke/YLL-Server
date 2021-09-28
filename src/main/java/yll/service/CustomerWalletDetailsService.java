package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
import yll.entity.CustomerRecharge;
import yll.entity.CustomerWallet;
import yll.entity.CustomerWalletDetails;
import yll.entity.Dic;
import yll.mapper.CustomerWalletDetailsMapper;
import yll.mapper.CustomerWalletMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerWalletDetailsVo;

import java.math.BigDecimal;
import java.util.List;

/**
 *  钱包订单流水明细
 */
@Slf4j
@Transactional
@Service
public class CustomerWalletDetailsService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerWalletDetailsMapper customerWalletDetailsMapper;
    @Autowired
    private CustomerWalletMapper customerWalletMapper;
    @Autowired
    private CustomerWalletService customerWalletService;
    @Autowired
    private CustomerRechargeService customerRechargeService;
    @Autowired
    private DicService dicService;
    @Autowired
    private WechatPayService wechatPayService;
    @Autowired
    private AppSecuritys securitys;
    @Autowired
    private Securitys adminSecuritys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public String insert(CustomerWalletDetailsVo vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerWalletDetails entity = new CustomerWalletDetails();
        IdHelper.setIfEmptyId(entity);

//        principal.setCustomerId(vo.getTargetId());      //TODO 封闭测试代码，测试后注释
//        entity.setTargetId(vo.getTargetId());      //TODO 封闭测试代码，测试后注释
        entity.setTargetId(principal.getCustomerId());   //TODO 服务器正式代码，测试后恢复
        entity.setPrice(vo.getPrice());

        entity.setOrderName(vo.getOrderName());
        entity.setOrderType(vo.getOrderType());

        entity.setSigns(vo.getSigns());
        entity.setPayType(vo.getRemark());  //remark为前端传入的支付方式标识
        entity.setRemark(vo.getRemark());
        entity.setRemarks(vo.getRemarks());
        entity.setOtherId(vo.getOtherId());

        //内部充值订单号处理
        String orderNumber = vo.getOrderNumber();
        if(YllConstants.PAY_ORDER_TYPE_ONE.equals(vo.getOrderType())){
            orderNumber = "CZ" + entity.getId();  //拼接id作为订单号，如CZ200402205427027。
            vo.setOrderNumber(orderNumber);
        }
        entity.setOrderNumber(orderNumber);

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);

     if(!YllConstants.PAY_ORDER_TYPE_ONE.equals(entity.getOrderType())){
            //普通订单，根据订单类型，获取字典表中对应的价格，用于钱包余额加减
            String orderType = vo.getOrderType();
            Dic dic = dicService.getByCode(orderType);
            String dicRemark = dic.getRemark();
            String[] dicArry = dicRemark.split("-");
            entity.setSigns(Integer.parseInt(dicArry[0]));
            entity.setPrice(Integer.parseInt(dicArry[1]));
            entity.setRemark(dic.getCodename());
            //更改金额
            changePrice(vo.getTargetId(), entity);
        } else if(YllConstants.PAY_ORDER_TYPE_ONE.equals(entity.getOrderType())){
             //新增支付明细记录
             CustomerRecharge customerRecharge = new CustomerRecharge();
             customerRecharge.setState(YllConstants.PAY_STATE_ONE);
             customerRecharge.setSigns(1);
             customerRecharge.setPrice(entity.getPrice() / 10);
             customerRecharge.setTargetId(principal.getCustomerId());
             customerRecharge.setOrderNumber(entity.getOrderNumber());
             customerRecharge.setPayType(entity.getRemark());
             customerRecharge.setRemark("充值");
             customerRechargeService.insert(customerRecharge);
             //修改钱包流水明细备注
             entity.setRemark("充值");
        }

        customerWalletDetailsMapper.insert(entity);
        return orderNumber;
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerWalletDetailsMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerWalletDetailsId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public String update(CustomerWalletDetails vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerWalletDetails entity = customerWalletDetailsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setPrice(vo.getPrice());
        entity.setOrderNumber(vo.getOrderNumber());
        entity.setOrderName(vo.getOrderName());
        entity.setOrderType(vo.getOrderType());

        entity.setSigns(vo.getSigns());
        entity.setPayType(vo.getPayType());
        entity.setRemark(vo.getRemark());
        entity.setRemarks(vo.getRemarks());
        entity.setOtherId(vo.getOtherId());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);

        //更改金额
        if(YllConstants.PAY_ORDER_TYPE_ONE.equals(entity.getOrderType()) && (YllConstants.PAY_STATE_TWO.equals(entity.getState()))){
            //充值订单，且态为已支付时，获取钱包金额
            changePrice(vo.getTargetId(), entity);
            //修改支付明细状态
            CustomerRecharge customerRecharge = new CustomerRecharge();
            customerRecharge.setOrderNumber(entity.getOrderNumber());
            customerRecharge = customerRechargeService.findBy(customerRecharge);
            if(null != customerRecharge){
                customerRecharge.setState(YllConstants.PAY_STATE_TWO);
                customerRechargeService.update(customerRecharge);
            }
            log.error("============微信支付-成功！修改钱包余额，订单号为：" + entity.getOrderNumber() + "；============" + "总金额：" + entity.getPrice() + "============");
        }
        customerWalletDetailsMapper.update(entity);
        return "";
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerWalletDetails entity = new CustomerWalletDetails();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerWalletDetailsMapper.update(entity);
    }


    /**
     * 按条件查询
     * @param
     * @return 实体
     */
    public CustomerWalletDetails findBy(CustomerWalletDetails condition) {
        CustomerWalletDetails result = new CustomerWalletDetails();
        List<CustomerWalletDetails> list = customerWalletDetailsMapper.findBy(condition);
        if(list.size() > 0){
            result = list.get(0);
        }
        return result;
    }


    /**
     * 按条件查询
     * @param
     * @return 实体
     */
    public CustomerWalletDetails getById(String id) {
        CustomerWalletDetails entity = customerWalletDetailsMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerWalletDetails> pagedQuery(Pagination pagination, CustomerWalletDetails condition) {
        return MybatisHelper.selectPage(pagination, () -> customerWalletDetailsMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerWalletDetails> pagedQueryWithName(Pagination pagination, CustomerWalletDetails condition) {
        return MybatisHelper.selectPage(pagination, () -> customerWalletDetailsMapper.findByWithName(condition));
    }

    /**
     * 分页查询
     * @param condition 查询条件
     * @return 分页结果
     */
    public Integer findSum(CustomerWalletDetails condition) {
        return customerWalletDetailsMapper.findSum(condition);
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerWalletDetailsVo> getAppList(Pagination pagination, CustomerWalletDetailsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerWalletDetailsMapper.getAppList(condition));
    }

    /**
     * 完成次数
     * @param condition 查询条件
     * @return
     */
    public CustomerWalletDetailsVo getCompletions(CustomerWalletDetailsVo condition) {
        return customerWalletDetailsMapper.getCompletions(condition);
    }


    // ==============================ToolMethods======================================
    /**
     * 微信支付订单--获取prepayId
     * @param vo
     * @return
     */
    public String getPrepayId(CustomerWalletDetailsVo vo) {
        AppPrincipal principal = securitys.getAppPrincipal();
        //充值订单，且态为未支付时，获取prepayId
        if(YllConstants.PAY_ORDER_TYPE_ONE.equals(vo.getOrderType()) && (YllConstants.PAY_STATE_ONE.equals(vo.getState()))){
            String prepayId = getPrepayId(principal.getCustomerId(), vo);     //TODO 服务器正式代码，测试后恢复
            // String prepayId = getPrepayId(vo.getTargetId(), vo);     //TODO 封闭测试代码，测试后注释
            //将微信返回的预支付prepay_id存入充值明细表
            //修改支付明细状态
            CustomerRecharge customerRecharge = new CustomerRecharge();
            customerRecharge.setOrderNumber(vo.getOrderNumber());
            customerRecharge = customerRechargeService.findBy(customerRecharge);
            if(null != customerRecharge){
                customerRecharge.setPrepayId(prepayId);
                customerRechargeService.update(customerRecharge);
            }
//            log.error("============微信支付-下单成功！订单号为：" + vo.getOrderNumber() + "；============" + "prepayId为：" + prepayId + "============");
            return prepayId;
        }
        return "";
    }


    /**
     * 向微信发起支付下单请求，获取prepayId
     * @param customerId
     * @param entity
     * @return
     */
    private String getPrepayId(String customerId, CustomerWalletDetailsVo entity) {
        if(StringUtils.isBlank(customerId)){
            throw ExceptionHelper.prompt("缺少用户唯一标识");
        }
        CustomerWallet cp = new CustomerWallet();
        cp.setTargetId(customerId);
        List<CustomerWallet> list = customerWalletMapper.findBy(cp);
        if(null == list || list.size() == 0){
            cp.setPrice(YllConstants.ZERO);
            customerWalletService.insert(cp);
        }
        String prepayId = "";
        if(YllConstants.PAY_ORDER_TYPE_ONE.equals(entity.getOrderType()) && (YllConstants.PAY_STATE_ONE.equals(entity.getState()))){
            //充值订单，且态为待支付时，向微信提交下单请求
            String outTradeNo = entity.getOrderNumber();
            Integer total =  entity.getPrice();
            String description = YllConstants.PAY_DESCRIPTION;
            try {
                //微信支付下单
                prepayId = createOrder(outTradeNo, total, description, entity.getOpenid(), entity.getRemark());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("============微信支付-下单-失败！订单号为：" + outTradeNo + "；============" + "总金额：" + total + "============");
            }
        }
        return prepayId;
    }


    /** 更改金额 */
    private void changePrice(String customerId, CustomerWalletDetails entity) {
        if(StringUtils.isBlank(customerId)){
            throw ExceptionHelper.prompt("缺少用户唯一标识");
        }
        CustomerWallet cp = new CustomerWallet();
        cp.setTargetId(customerId);
        List<CustomerWallet> list = customerWalletMapper.findBy(cp);
        if(null == list || list.size() == 0){
            cp.setPrice(YllConstants.ZERO);
            customerWalletService.insert(cp);
            list.add(cp);
        }
        cp = list.get(0);
        if(YllConstants.PAY_ORDER_TYPE_ONE.equals(entity.getOrderType()) && (YllConstants.PAY_STATE_TWO.equals(entity.getState()))){
            //充值订单，且态为已支付时，根据（1RMB=10粉条币）规则，乘以相应倍数，打印日志
            Integer price = entity.getPrice() * 10;
            entity.setPrice(price);
            log.info("============微信支付-回调成功！订单号为：" + entity.getOrderNumber() + "；============" + "本次充值的粉条币数为：" + entity.getPrice() + "============");
        }
        customerWalletService.calculationPrice(cp, entity);

    }


    /** 微信下单
        * 重要参数：
        * • out_trade_no：商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
        * • description：商品描述
        * • notify_url：支付回调通知URL，该地址必须为直接可访问的URL，不允许携带查询串
        * • total：订单总金额，单位为分
        * # channel：订单渠道，非微信所需参数，仅做本地应用内部下单识别使用（小程序、App）
     *  @return 支付渠道预付款id
     * */
    private String createOrder(String outTradeNo, Integer total, String description, String openid, String channel) throws Exception {
        String prepayId = "";
        //根据渠道进行分别下单
        if(YllConstants.PAY_TYPE_ONE.equals(channel)){
            //App
            prepayId = wechatPayService.createOrderApp(outTradeNo, total, description);
            log.error("============微信App支付-下单成功！订单号为：" + outTradeNo + "；============" + "prepayId为：" + prepayId + "============");
        } else if(YllConstants.PAY_TYPE_TWO.equals(channel)){
            //小程序
            prepayId = wechatPayService.createOrderMini(outTradeNo, total, description, openid);
            log.error("============微信小程序支付-下单成功！订单号为：" + outTradeNo + "；============" + "prepayId为：" + prepayId + "============");
        }
        return prepayId;
    }


    /** 验证数据 */
    private void validate(CustomerWalletDetails vo) {
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
