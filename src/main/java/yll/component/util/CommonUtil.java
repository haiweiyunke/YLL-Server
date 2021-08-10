package yll.component.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import yll.common.security.app.AppToken;
import yll.common.security.app.TokenCache;
import yll.component.app.sms.VerifyCodeFactory;
import yll.entity.Customer;
import yll.entity.MallUser;
import yll.service.CustomerPointsService;
import yll.service.CustomerService;
import yll.service.model.YllConstants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用类
 */
@Component
public class CommonUtil {

    // ==============================Fields===========================================
    @Value("${sms.appid}")
    private int appid;

    @Value("${sms.appkey}")
    private String appkey;

    @Value("${sms.templateId}")
    private int templateId;

    @Value("${sms.sign}")
    private String sign;

    @Value("${sms.effectiveTime}")
    private String effectiveTime;

    @Value("${api.add-mall-user}")
    private String apiMallUserAddUrl;

    @Value("${api.edit-mall-user}")
    private String apiMallUserEditUrl;

    @Autowired
    private TokenCache tokenCache;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerPointsService customerPointsService;

    //初始化请求client
    private final OkHttpClient client;

    public CommonUtil() {
        client = new OkHttpClient.Builder()//
                .connectTimeout(5, TimeUnit.SECONDS)//
                .writeTimeout(5, TimeUnit.SECONDS)//
                .readTimeout(20, TimeUnit.SECONDS)//
                .build();
    }

    // ==============================Methods==========================================
    /**
     * 获取验证码
     * @param username
     * @return
     */
    public String getCode2(String username) {
        if(StringUtils.isBlank(username)){
            throw ExceptionHelper.prompt("手机号不能为空");
        }
        //用户是否已存在
        Customer entity = new Customer();
        entity.setUsername(username);
        entity = customerService.getByCondition(entity);
        if(null == entity){
            //新增用户
            entity = new Customer();
            entity.setUsername(username);
            entity.setPhone(username);
            entity.setRoleType(YllConstants.ONE.toString());
            //附带积分初始化
            entity = customerService.insert(entity);
        }
        //生成验证码
        String code = VerifyCodeFactory.createSixCode();
        System.out.println(username + "=======短信验证码=======》" + code);
        try {
            //发送验证码-腾讯云短信
           SmsSingleSender sendSMS = new SmsSingleSender(appid,appkey);
            ArrayList<String> params = new ArrayList<>();
            params.add(code);
            params.add(effectiveTime);
            //nationCode 国家码，如 86 为中国；phoneNumber 不带国家码的手机号；templateId 信息内容
            // params 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
            //sign 签名，如果填空，系统会使用默认签名
            //extend扩展码，可填空；ext 服务端原样返回的参数，可填空    TODO  打包时解锁此处
            //sendSMS.sendWithParam("86", username, templateId, params, sign, "" , "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return tokenCache.putCode(code, username);
    }

    /**
     * 带附加信息的注册---使用中
     * @param condition
     * @return
     */
    public String getCode(Customer condition) {
        String username = condition.getUsername();
        String password = condition.getPassword();
        if(StringUtils.isBlank(username)){
            throw ExceptionHelper.prompt("手机号不能为空");
        }
        String code = "";
        try {
            //用户是否已存在
            Customer entity = new Customer();
            entity.setUsername(username);
            entity = customerService.getByCondition(entity);
            if(null == entity){
                //新增用户
                entity = new Customer();
                entity.setUsername(username);
                entity.setPhone(username);
                entity.setGender(YllConstants.TWO);
                if(StringUtils.isNotBlank(password)){
                    entity.setPassword(password);
                } else{
                    entity.setPassword("111111");   //默认密码
                }
                entity.setRoleType(YllConstants.ONE.toString());
                //附带积分初始化
                entity = customerService.insert(entity);
            } else{
                if(StringUtils.isNotBlank(password)){
                    entity.setPassword(password);
                } else{
                    password = yll.common.tools.StringUtils.getMD5("111111");
                    password = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
                    entity.setPassword(password);   //默认密码
                }
                customerService.update(entity);
            }
            //生成验证码
            code = VerifyCodeFactory.createSixCode();
            System.out.println(username + "=======短信验证码=======》" + code);

            //发送验证码-腾讯云短信
            SmsSingleSender sendSMS = new SmsSingleSender(appid,appkey);
            ArrayList<String> params = new ArrayList<>();
            params.add(code);
            params.add(effectiveTime);
            //nationCode 国家码，如 86 为中国；phoneNumber 不带国家码的手机号；templateId 信息内容
            // params 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
            //sign 签名，如果填空，系统会使用默认签名
            //extend扩展码，可填空；ext 服务端原样返回的参数，可填空    TODO  打包时解锁此处
            sendSMS.sendWithParam("86", username, templateId, params, sign, "" , "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //验证码放入缓存
        return tokenCache.putCode(code, username);
        /* 带附加信息-调试使用
        String token = tokenCache.putCode(code, username);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("code", code);
        return result;*/
    }
    /**
     * 获取短信验证码
     * @param username
     * @return
     */
    public Map<String, Object> getCode(String username) {
        if(StringUtils.isBlank(username)){
            throw ExceptionHelper.prompt("手机号不能为空");
        }
        //用户是否已存在
        Customer entity = new Customer();
        entity.setUsername(username);
        entity = customerService.getByCondition(entity);
        if(null == entity){
            //新增用户
            entity = new Customer();
            entity.setUsername(username);
            entity.setPhone(username);
            entity.setGender(YllConstants.TWO);
            entity.setPassword("111111");   //默认密码
            entity.setRoleType(YllConstants.ONE.toString());
            //附带积分初始化
            entity = customerService.insert(entity);
        }
        //生成验证码
        String code = VerifyCodeFactory.createSixCode();
        System.out.println(username + "=======短信验证码=======》" + code);
        try {
            //发送验证码-腾讯云短信
           SmsSingleSender sendSMS = new SmsSingleSender(appid,appkey);
            ArrayList<String> params = new ArrayList<>();
            params.add(code);
            params.add(effectiveTime);
            //nationCode 国家码，如 86 为中国；phoneNumber 不带国家码的手机号；templateId 信息内容
            // params 模板参数列表，如模板 {1}...{2}...{3}，那么需要带三个参数
            //sign 签名，如果填空，系统会使用默认签名
            //extend扩展码，可填空；ext 服务端原样返回的参数，可填空    TODO  打包时解锁此处
            sendSMS.sendWithParam("86", username, templateId, params, sign, "" , "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        String token = tokenCache.putCode(code, username);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("code", code);
        return result;
    }

    /**
     * 校验验证码
     * @param
     * @return
     */
    public void checkCode(AppToken appToken) throws Exception {
        String requestCodeToken = appToken.getToken();
        Integer requestCode = appToken.getCode();
        if (StringUtils.isEmpty(requestCodeToken)) {
            throw ExceptionHelper.prompt( "codeToken不能为空");
        }
        if (null == requestCode) {
            throw ExceptionHelper.prompt("验证码不能为空");
        }

        String cacheCodeStr = tokenCache.getCode(requestCodeToken);
        if(StringUtils.isBlank(cacheCodeStr)){
            throw ExceptionHelper.prompt("验证码已失效，请重新获取");
        }

        String[] args = cacheCodeStr.split("_");
        String username  = args[0];
        cacheCodeStr  = args[1];
        if(appToken.getType() != 5 && !appToken.getUsername().equals(username)){
            throw ExceptionHelper.prompt("当前操作用户有误");
        }
        Integer cacheCode = Integer.parseInt(cacheCodeStr);
        if(!cacheCode.equals(requestCode)){
            throw ExceptionHelper.prompt("验证码有误");
        }
        tokenCache.removeSMS(requestCodeToken);
    }

    /**
     * 密码加密
     * @param password 密码
     * @param id 数据主键id(做为盐)
     * @return
     */
    public String encoder(String  password, String id) throws UnsupportedEncodingException {
        //1、请求数据的base64解码
        Base64.Decoder decoder = Base64.getDecoder();
        password  = new String(decoder.decode(password), "UTF-8");
        //2、加盐（盐为主键id）
        password = password.concat(id);
        //3、MD5
        password = yll.common.tools.StringUtils.getMD5(password);
        //4、Base64
        password = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
        return password;
    }


    /**
     * 分享链接
     * @param url
     * @return
     */
    public static String shareUrl(String id, String  url) throws UnsupportedEncodingException {
        if(StringUtils.isBlank(id) || StringUtils.isBlank(url)){
            throw ExceptionHelper.prompt("id或路径不能为");
        }
        String params = id + "&" + url;
        url = "share/share.html?sign=" + Base64.getEncoder().encodeToString(params.getBytes("UTF-8"));
        return url;
    }

    /**
     * 正则匹配电话号码
     * @param str
     * @return
     */
    public Boolean RegExpConst(String str) {
        /**
         * 手机号码
         * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
         * 联通：130,131,132,152,155,156,185,186
         * 电信：133,1349,153,180,189
         */
        String MOBILE = "^1(3[0-9]|5[0-35-9]|8[025-9])\\d{8}$";
        /**
         * 中国移动：China Mobile
         * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
         */
        String CM = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$";
        /**
         * 中国联通：China Unicom
         * 130,131,132,152,155,156,185,186
         */
        String CU = "^1(3[0-2]|5[256]|8[56])\\d{8}$";
        /**
         * 中国电信：China Telecom
         * 133,1349,153,180,189
         */
        String CT = "^1((33|53|8[09])[0-9]|349)\\d{7}$";
        /**
         * 大陆地区固话及小灵通
         * 区号：010,020,021,022,023,024,025,027,028,029
         * 号码：七位或八位
         */
        String PHS = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";

        String[] regArray = {MOBILE, CM, CU, CT, PHS};

        //开始验证
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        for (String reg :
                regArray) {
            b = matcherResult(str, reg);
            if(b){
                break;
            }
        }
        return b;
    }

    /**
     * 校验电话号
     * @param str
     * @param regStr
     * @return
     */
    private boolean matcherResult(String str, String regStr) {
        Pattern p;
        Matcher m;
        boolean b;
        p = Pattern.compile(regStr);
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


}
