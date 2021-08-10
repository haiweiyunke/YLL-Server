package yll.common.security.app;

import com.github.relucent.base.util.expection.ExceptionHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import yll.common.BaseConstants.BoolInts;
import yll.component.util.CommonUtil;
import yll.entity.Customer;
import yll.service.CustomerPointsService;
import yll.service.CustomerService;
import yll.service.model.YllConstants;

/**
 * APP权限验证类<br>
 * @author cc
 */
public class AppRealm {

    // ==============================Fields===========================================
    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==============================Constructors=====================================
    public AppRealm() {
        super();
    }

    // ==============================Methods==========================================
    /**
     * (登陆验证)认证回调函数,登录时调用.
     * @param token 认证凭据
     * @return 认证信息
     */
    protected AppPrincipal doGetAuthenticationInfo(AppToken token) throws Exception{

        String username = token.getUsername();
        Integer type = token.getType();

        if (null == type) {
            throw ExceptionHelper.prompt("登录方式不能为空");
        }

        if ((YllConstants.LOGIN_CODE.equals(type) || YllConstants.LOGIN_PASSWORD.equals(type)) && StringUtils.isEmpty(username)) {
            throw ExceptionHelper.prompt("账号不能为空");
        }

        Customer customer = null;
        if(YllConstants.LOGIN_CODE.equals(type)){
            customer = doCode(token);
        } else if(YllConstants.LOGIN_PASSWORD.equals(type)){
            customer = doPassword(token);
        } else if(YllConstants.LOGIN_WECHAT.equals(type)){
            customer = doWeChat(token);
        } else if(YllConstants.LOGIN_ALI.equals(type)){
            customer = doAli(token);
        } else{
            throw ExceptionHelper.prompt("登录方式有误");
        }

        if (customer == null) {
            throw ExceptionHelper.prompt("用户不存在");
        }

        if (customer.getEnabled().intValue() == BoolInts.FALSE) {
            throw ExceptionHelper.prompt("用户已禁用，请联系管理员");
        }
        if (customer.getDeleted().intValue() == BoolInts.TRUE) {
            throw ExceptionHelper.prompt("用户不存在，或者已经停用，请联系管理员");
        }

        //登录实体
        AppPrincipal principal = new AppPrincipal();
        principal.setCustomerId(customer.getId());
        principal.setUsername(customer.getUsername());
        principal.setNickname(customer.getNickname());
        principal.setAliId(customer.getAliId());
        principal.setWechatId(customer.getWechatId());

        return principal;
    }

    /**
     *  验证码登录
     * @param appToken
     * @return
     */
    protected Customer doCode(AppToken appToken) throws Exception {
        commonUtil.checkCode(appToken);
        Customer vo = new Customer();
        vo.setUsername(appToken.getUsername());
        return customerService.getByCondition(vo);
    }

    /**
     *  密码登录
     * @param token
     * @return
     */
    protected Customer doPassword(AppToken token) {
        String password = token.getPassword();
        if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }
        return customerService.login(token);
    }

    /**
     *  微信登录
     * @param token
     * @return
     */
    protected Customer doWeChat(AppToken token) {
        String wechatId = token.getWechatId();
        Customer vo = new Customer();
        vo.setWechatId(wechatId);
        return handleOauthCustomer(token, vo);
    }

    /**
     *  支付宝登录
     * @param token
     * @return
     */
    protected Customer doAli(AppToken token) {
        String aliId = token.getAliId();
        Customer vo = new Customer();
        vo.setAliId(aliId);
        return handleOauthCustomer(token, vo);
    }

    /**
     * 新增|更新用户第三方信息
     * @param token
     * @param vo
     * @return
     */
    private Customer handleOauthCustomer(AppToken token, Customer vo) {
        Customer entity = customerService.getByCondition(vo);
        //获取第三方平台登录后的用户信息
        vo = initOauthCustomer(token);
        if(null == entity){
            //新增用户
            entity = customerService.insert(vo);
        } else{
            vo.setId(entity.getId());
            entity = customerService.update(vo);
        }
        return entity;
    }

    /**
     * 第三方注册初始化用户
     * @param token
     * @return
     */
    private Customer initOauthCustomer(AppToken token) {
        Customer vo = new Customer();
        vo.setNickname(token.getNickname());
        vo.setHeadImg(token.getHeadImg());
        if(StringUtils.isNotBlank(token.getPhone())){
            vo.setPhone(token.getPhone());
            vo.setUsername(token.getPhone());
        }
        return vo;
    }

}
