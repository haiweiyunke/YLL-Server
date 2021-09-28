package yll.app.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yll.common.security.app.AppSecuritysUtil;
import yll.component.pay.exception.RequestWechatException;
import yll.component.pay.parameter.StaticParameter;
import yll.component.pay.tools.PayNotifyUtils;
import yll.component.pay.tools.PayRequestUtils;
import yll.component.pay.tools.PayResponseUtils;
import yll.component.pay.vo.NotifyResourceVO;
import yll.component.pay.vo.PayNotifyVO;
import yll.entity.*;
import yll.service.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerRechargeVo;
import yll.service.model.vo.CustomerWalletDetailsVo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * APP钱包
 * @author cc
 */
@Slf4j
@RestController
@RequestMapping(value = "/app/wallet")
public class WalletAppController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerWalletService customerWalletService;

    @Autowired
    private CustomerWalletDetailsService customerWalletDetailsService;

    @Autowired
    private CustomerRechargeService customerRechargeService;

    @Autowired
    private DicService dicService;

    @Autowired
    private WechatPayService wechatPayService;


    // ==============================Test==========================================
    /**
     * [GET] /app/wallet/test <br>
     * 查询数据主页       （测试使用）
     */
    @GetMapping(value = "/test")
    public Result<?> test(Pagination pagination, CustomerWalletDetailsVo condition) {

        String wechatMchId = StaticParameter.wechatMchId;

        condition.setTargetId("2020040416083636300001");
        condition.setPrice(1);
        condition.setRemark(YllConstants.PAY_TYPE_ONE);

//        return wechatSave(condition);
//        return list(pagination, condition);
//        return rechargeList();
        return explain();
    }



    // ==============================Methods==========================================
    /**
     * [GET] /app/wallet/index <br>
     * 查询数据主页
     */
    @PostMapping(value = "/index")
    public Result<?> index(Pagination pagination, CustomerWalletDetailsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        CustomerWallet customerWallet = new CustomerWallet();
        customerWallet.setTargetId(customerId);
        condition.setTargetId(customerId);
        //查询用户钱包记录
        customerWallet = customerWalletService.getAppDetail(customerWallet);
        if(null == customerWallet){
            //用户钱包表新增数据
            customerWallet = new CustomerWallet();
            customerWallet.setTargetId(customerId);
            customerWallet.setPrice(YllConstants.ZERO);
            customerWalletService.insert(customerWallet);
        }
        //明细列表
        //Page<CustomerWalletDetailsVo> list = customerWalletDetailsService.getAppList(pagination, condition);
        //组合返回报文
        Map result = new HashMap();
        result.put("customerWallet", customerWallet.getPrice());
//        result.put("list", list);
        return Result.ok(result);
    }


    /**
     * [POST] /app/wallet/details/list <br>
     * 查询钱包流水列表
     */
    @PostMapping(value = "/details/list")
    public Result<?> list(Pagination pagination, CustomerWalletDetailsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        //金额明细
        condition = ObjectUtils.defaultIfNull(condition, new CustomerWalletDetailsVo());
        condition.setTargetId(customerId);
        Page<CustomerWalletDetailsVo> page = customerWalletDetailsService.getAppList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [POST] /app/wallet/details/save <br>
     *  新增钱包流水明细（通用）
     */
    @PostMapping(value = "/details/save")
    public Result<?> collect(CustomerWalletDetailsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setTargetId(customerId);
        if(StringUtils.isBlank(condition.getOrderType())){
            return Result.error("下单类型不能为空");
        }
        //金额说明
        Dic dic = dicService.getByCode(condition.getOrderType());
        if(null == dic){
            return Result.error("当前订单类型不存在");
        }
        String remarks = dic.getRemarks();
        String[] array = remarks.split("-");
        String signs = array[0];    //加减符号(0-减，1-加)
        String price = array[1];    //金额
        condition.setSigns(Integer.parseInt(signs));
        condition.setPrice(Integer.parseInt(price));
        condition.setOrderName(dic.getCodename());

        customerWalletDetailsService.insert(condition);
        return Result.ok();
    }


    /**
     * [GET] /app/wallet/explain <br>
     * 查询充值说明
     */
    @GetMapping(value = "/explain")
    public Result<?> explain() {
        //金额说明
        Dic dic = dicService.getByCode("coinStatement");
        //组合
        Map result = new HashMap();
        result.put("explain", dic.getRemarks());
        return Result.ok(result);
    }


    /**
     * [POST] /app/wallet/recharge/list <br>
     * 查询充值金额
     */
    @PostMapping(value = "/recharge/list")
    public Result<?> rechargeList() {
        List<Dic> list = dicService.getAppList("walletRechargePrice");
        return Result.ok(list);
    }


    /**
     * 获取增加金额
     * @param vo
     * @param map
     */
    public void getPrice(Dic vo, Map<String, Object> map) {
        if(StringUtils.isNotBlank(vo.getRemark())){
            String[] arges = vo.getRemark().split("\\+");
            if(arges.length > 1){
                map.put("price", "+" +arges[1]);
            } else{
                map.put("price", "");
            }
        } else{
            map.put("price", "");
        }
    }


    /**
     * [POST] /app/wallet/recharge//list <br>
     * 查询钱包流水列表
     */
    @PostMapping(value = "/recharge/list")
    public Result<?> rechargeList(Pagination pagination, CustomerRechargeVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        //金额明细
        condition = ObjectUtils.defaultIfNull(condition, new CustomerRechargeVo());
        condition.setTargetId(customerId);
        Page<CustomerRechargeVo> page = customerRechargeService.getAppList(pagination, condition);
        return PageResult.of(page);
    }


    // ============================== 微信支付 ==========================================
    /**
     * [POST] /app/wallet/details/wechat/save <br>
     *  新增钱包流水明细 （微信下单）
     */
    @PostMapping(value = "/details/wechat/save")
    public Result<?> wechatSave(CustomerWalletDetailsVo condition) {
        Integer price = condition.getPrice();
        String remark = condition.getRemark();
        String customerId = AppSecuritysUtil.getCustomerId();     //TODO 封闭测试时注释，测试后恢复
        condition.setTargetId(customerId);                                      //TODO 封闭测试时注释，测试后恢复
        if(price <= 0){
            return Result.error("入参金额有误");
        }
        if(!YllConstants.PAY_TYPE_ONE.equals(remark) && !YllConstants.PAY_TYPE_TWO.equals(remark) ){
            return Result.error("下单渠道有误");
        }
        if(!YllConstants.PAY_ORDER_TYPE_ONE.equals(condition.getOrderType()) ){
            return Result.error("下单类型有误");
        }
        condition.setOrderType(YllConstants.PAY_ORDER_TYPE_ONE);
        condition.setState(YllConstants.PAY_STATE_ONE);
        condition.setSigns(1);      //(0-减，1-加)
        condition.setPrice(price * 10);      //1RMB=10粉条币
        //下单
        String orderNumber = customerWalletDetailsService.insert(condition);
        if(StringUtils.isBlank(orderNumber)){
            return Result.error("获取内部支付订单号失败");
        }
        //返回系统内部支付订单id
        Map<String, String> result = new HashMap();
        result.put("orderNumber", orderNumber);
        return Result.ok(result);
    }


    /**
     * [POST] /app/wallet/details/wechat/prepay <br>
     *  微信充值下单，获取prepayId
     */
    @PostMapping(value = "/details/wechat/prepay")
    public Result<?> wechatPrepay(CustomerWalletDetailsVo condition) {
        if(StringUtils.isBlank(condition.getOrderNumber())){
            return Result.error("订单号不能为空");
        }
        String remark = condition.getRemark();
        if(!YllConstants.PAY_TYPE_ONE.equals(remark) && !YllConstants.PAY_TYPE_TWO.equals(remark) ){
            return Result.error("下单渠道有误");
        }
        //钱包订单流水（1、用于查询条件单是否存在，2、赋值给vo，方便在service层下支付订单时获取所需price）
        CustomerWalletDetails customerWalletDetails = new CustomerWalletDetails();
        customerWalletDetails.setOrderNumber(condition.getOrderNumber());
        customerWalletDetails = customerWalletDetailsService.findBy(customerWalletDetails);
        if(null == customerWalletDetails){
            return Result.error("未查到该订单");
        }
        //封装prepayId的条件实体（新new一个vo实体不复用入参的condition，是因为防止作为入参时，service层获取prepatId时条件混乱）
        CustomerWalletDetailsVo vo = new CustomerWalletDetailsVo();
        BeanUtils.copyProperties(customerWalletDetails, vo);
        vo.setRemark(remark);   //添加前端传入的下单渠道。微信小程序为“wechatMini”，APP为“wechatApp”，用于区分service层小程序、App下单

        String customerId = AppSecuritysUtil.getCustomerId();     //TODO 封闭测试时注释，测试后恢复
        condition.setTargetId(customerId);                                      //TODO 封闭测试时注释，测试后恢复

        //检查小程序下单用户的openid
        if(YllConstants.PAY_TYPE_TWO.equals(remark)){
            String openid = condition.getOpenid();
            Customer customer = customerService.getById(condition.getTargetId());
            String wechatId = customer.getWechatId();
            if(StringUtils.isBlank(openid) && StringUtils.isBlank(wechatId)){
                return Result.error("该用户暂无openid，请先获取openid");
            } else if(StringUtils.isNotBlank(openid) && StringUtils.isBlank(wechatId)){
                //将openid存入用户表中
                customer.setWechatId(openid);
                customerService.update(customer);
            }
            //将openid赋值，方便后续小程序支付下单
            vo.setOpenid(customer.getWechatId());
        }

        String prepayId = customerWalletDetailsService.getPrepayId(vo);
        //根据下单渠道，返回前端不同的下单入参
        Map<Object, Object> result = new HashMap();
        try {
            if (YllConstants.PAY_TYPE_TWO.equals(remark)) {
                //小程序支付所需参数
                result = getJsapiParams(prepayId);
            } else if (YllConstants.PAY_TYPE_ONE.equals(remark)) {
                //App支付所需参数
                result = getAppParams(prepayId);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return Result.ok(result);
    }


    /**
     * [POST] /app/wallet/details/wechat/cancel <br>
     *  修改钱包流水明细（微信充值取消）
     */
    @PostMapping(value = "/details/wechat/cancel")
    public Result<?> wechatCancel(CustomerWalletDetails condition) {
        String id = condition.getId();
        if(StringUtils.isBlank(id)){
            return Result.error("缺少流水明细id");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setTargetId(customerId);
        condition.setOrderType(YllConstants.PAY_ORDER_TYPE_ONE);
        condition.setState(YllConstants.PAY_STATE_THREE);
        customerWalletDetailsService.update(condition);
        return Result.ok();
    }


    /**
     * [POST] /app/wallet/details/wechat/notify <br> ***
     *  微信支付回调函数（）
     */
    @PostMapping(value = "/details/wechat/notify")
    public Map<String,String> wechatNotify(HttpServletRequest request, CustomerWalletDetails condition) {
        log.info("#################微信支付回调--开始##################");
        //开始解析微信回调
        Map<String,String> map = new HashMap<>(2);
        try {
            //微信返回的请求体
            String body = PayNotifyUtils.getRequestBody(request);
            //如果验证签名序列号通过
            if (PayNotifyUtils.verifiedSign(request,body)){
                //微信支付通知实体类
                PayNotifyVO payNotifyVO = JSONObject.parseObject(body,PayNotifyVO.class);
                //如果支付成功
                if ("TRANSACTION.SUCCESS".equals(payNotifyVO.getEvent_type())){
                    //通知资源数据
                    PayNotifyVO.Resource resource = payNotifyVO.getResource();
                    //解密后资源数据
                    String notifyResourceStr = PayResponseUtils.decryptResponseBody(resource.getAssociated_data(),resource.getNonce(),resource.getCiphertext());
                    //通知资源数据对象
                    NotifyResourceVO notifyResourceVO = JSONObject.parseObject(notifyResourceStr,NotifyResourceVO.class);
                    //查询订单 可以优化把订单放入redis
                    CustomerWalletDetails order = new CustomerWalletDetails();
                    order.setOrderNumber(notifyResourceVO.getOut_trade_no());
                    order = customerWalletDetailsService.findBy(order);

                    if (order != null){
                        //如果订单状态是提交状态
                        if (YllConstants.PAY_STATE_ONE == order.getState()){
                            //如果付款成功
                            if ("SUCCESS".equals(notifyResourceVO.getTrade_state())){
                                //修改充值订单状态、钱包金额
                                BeanUtils.copyProperties(order, condition);
                                condition.setOrderType(YllConstants.PAY_ORDER_TYPE_ONE);
                                condition.setState(YllConstants.PAY_STATE_TWO);
                                customerWalletDetailsService.update(condition);
                                log.info("============微信支付-回调成功！商户订单号为：" + notifyResourceVO.getOut_trade_no() + "，报文如下============");
                                log.info(JSONObject.toJSONString(notifyResourceVO));
                                //微信支付回调成功时返回的json   {"amount":{"currency":"CNY","payer_currency":"CNY","payer_total":1,"total":1},"appid":"wx8638923ceb1cccfd","bank_type":"OTHERS","mchid":"1607190935","out_trade_no":"CZ2021092813162447400002","payer":{"openid":"ot-7t4kkCcshvgkV98bdW5dcb7Xg"},"success_time":"2021-09-28T13:16:41+08:00","trade_state":"SUCCESS","trade_state_desc":"支付成功","trade_type":"JSAPI","transaction_id":"4200001156202109286225281507"}
                            } else {
                                //打印失败参数
                                log.error("============微信支付-回调失败！商户订单号为：" + notifyResourceVO.getOut_trade_no() + "，报文如下============");
                                log.error(JSONObject.toJSONString(notifyResourceVO));
                            }
                        }
                    }
                }else{
                    log.info("微信返回支付错误摘要："+payNotifyVO.getSummary());
                }
                //通知微信正常接收到消息,否则微信会轮询该接口
                map.put("code","SUCCESS");
                map.put("message","");
                return map;
            }
            log.error("#################微信支付回调--结束##################");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (RequestWechatException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return map;

    }


    /**
     * [POST] /app/wallet/details/wechat/openid <br>
     *  根据jsCode，获取openid。（小程序使用）
     */
    @PostMapping(value = "/details/wechat/openid")
    public Result<?> getOpenid(String jsCode) {

        if(StringUtils.isBlank(jsCode)){
            return Result.error("缺少jsCode");
        }
        //返回openid
        Map<String, String> resultMap = null;
        try {
            resultMap = wechatPayService.getWechatOpenid(jsCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok(resultMap);
    }


    // ============================== 微信支付--工具 ==========================================
    /**
     *  处理jsapi下单所需参数
     */
    private Map<Object, Object> getJsapiParams(String prepayId) throws MalformedURLException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //应用ID
        String appId = StaticParameter.appIdMini;
        //时间戳，自1970年以来的秒数
        long timeStamp = System.currentTimeMillis();
        //随机串
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //微信签名方式
        String signType = "RSA";
        //预支付交易会话ID
        prepayId = "prepay_id=" + prepayId;
        //签名
        String paySign =  PayRequestUtils.miniSign(timeStamp, nonceStr, prepayId);

        Map<Object, Object> result = new HashMap<>();
        result.put("appId", appId);
        result.put("timeStamp", timeStamp + "");
        result.put("nonceStr", nonceStr);
        result.put("package", prepayId);
        result.put("signType", signType);
        result.put("paySign", paySign);
        return result;
    }


    /**
     *  处理app下单所需参数
     */
    private Map<Object, Object> getAppParams(String prepayId) throws MalformedURLException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        //应用ID
        String appId = StaticParameter.wechatAppId;
        //商户号
        String partnerid = StaticParameter.wechatMchId;
        ////时间戳，自1970年以来的秒数
        long timeStamp = System.currentTimeMillis();
        //随机串
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        //签名
        String sign = PayRequestUtils.appSign(timeStamp, nonceStr, prepayId);

        Map<Object, Object> result = new HashMap<>();
        result.put("appid", appId);
        result.put("partnerid", partnerid);
        result.put("prepayid", prepayId);
        result.put("package", "Sign=WXPay");    //订单详情扩展字符串，暂填写固定值Sign=WXPay
        result.put("noncestr", nonceStr);
        result.put("timestamp", timeStamp + "");
        result.put("sign", sign);
        return result;
    }


    public static void main(String[] args) {
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(nonceStr);
    }

}
