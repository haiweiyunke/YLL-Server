package yll.app.controller;

import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yll.common.configuration.CustomProperties;
import yll.common.identifier.IdHelper;
import yll.common.security.app.AppSecuritysUtil;
import yll.common.security.app.AppToken;
import yll.component.store.domain.StoreRequest;
import yll.component.store.service.FileService;
import yll.component.store.service.IStoreService;
import yll.component.util.CommonUtil;
import yll.component.util.FileOperateUtil;
import yll.entity.AppVersion;
import yll.entity.Customer;
import yll.entity.CustomerAuthentications;
import yll.entity.Dic;
import yll.service.*;
import yll.service.model.PasswordDto;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerAuthenticationsVo;
import yll.service.model.vo.CustomerVo;
import yll.service.model.vo.ProcessVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP登录
 * @author cc
 */
@Slf4j
@RestController
@RequestMapping(value = "/app")
public class IndexAppController {       //TODO 后台版本管理、接口

    // ==============================Fields===========================================
    /** COS上传路径 */
    @Value("${store.path:/yshd/prod/}")
    protected String path;

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAuthenticationsService customerAuthenticationsService;

    @Autowired
    private DicService dicService;

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private IStoreService storeService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AlipayAuthService alipayAuthService;

    @Autowired
    private AppVersionService appVersionService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/login <br>
     * 登录
     */
    @PostMapping(value = "/login")
    public Result<?> login(HttpServletRequest request, AppToken condition) {
        String token = request.getHeader(YllConstants.TOKEN_NAME);
        try {
            /** 登录方式： 1-验证码，2-密码，3-微信，4-支付宝 */
            if(condition.getType() == YllConstants.LOGIN_CODE){
                if(StringUtils.isBlank(token)){
                    return Result.error("缺少token");
                }
                condition.setToken(token);
                if(StringUtils.isBlank(condition.getUsername()) || null == condition.getCode()){
                    return Result.error("用户名或验证码不能为空");
                }
            } else if(condition.getType() == YllConstants.LOGIN_PASSWORD){
                if(StringUtils.isBlank(condition.getUsername()) || StringUtils.isBlank(condition.getPassword())){
                    return Result.error("用户名或密码不能为空");
                }
            } else{     //第三方登录
                //微信
                if(condition.getType() == YllConstants.LOGIN_WECHAT){
                    //使用友盟登录，由手机端直接传递标识id、头像、昵称信息，服务端直接校验存储即可
                }
                //支付宝
                if(condition.getType() == YllConstants.LOGIN_ALI){
                    //采用支付宝官方sdk登录
                    Customer customer = alipayAuthService.getOauth2Token(condition.getAliCode());
                    if(null == customer){
                        throw ExceptionHelper.prompt("支付宝登录失败");
                    }
                    condition.setAliId(customer.getAliId());
                    condition.setHeadImg(customer.getPhone());
                    condition.setPhone(customer.getPhone());
                }
            }
            //执行登录
            AppSecuritysUtil.login(condition);
        } catch (Exception e) {
            e.printStackTrace();
            String errMessage = StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "登录失败";
            return Result.error(errMessage);
        }
        return Result.ok(condition.getToken());
    }

    /**
     * [GET] /app/get <br>
     * 获取用户详情
     */
     @GetMapping(value = "/get")
     public Result<?> get() {
         String customerId = AppSecuritysUtil.getCustomerId();
         Customer customer = customerService.getById(customerId);
         CustomerAuthenticationsVo condition = new CustomerAuthenticationsVo();
         condition.setTargetId(customerId);
         CustomerAuthentications entity = customerAuthenticationsService.getAppDetail(condition);
         Integer state = 3;     //0-未通过，1-待审核， 2-已通过，3-未申请
         if(null != entity){
             state = entity.getState();
         }
        //封装
         Map<String,Object> result = new HashMap();
         result.put("headImg", customer.getHeadImg());
         result.put("nickname", customer.getNickname());
         result.put("phone", customer.getPhone());
         result.put("state", state);
         return Result.ok(result);
     }

    /**
     * [POST] /app/password <br>
     * 修改用户密码
     */
    @PostMapping(value = "/password")
    public Result<?> updateCurrentPassword(PasswordDto passwordDto) {

        String requestPassword = passwordDto.getNewPassword();
        if(StringUtils.isBlank(requestPassword)){
            return Result.error("密码不能为空");
        }
        if(requestPassword.contains("\n")){
            requestPassword = requestPassword.replace("\n", "");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer entity = customerService.getById(customerId);
        try {
            //密码加密
//            requestPassword = commonUtil.encoder(requestPassword, customerId);
            entity.setPassword(requestPassword);
            customerService.update(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("设置密码失败");
        }
        return Result.ok();
    }

     /**
     * [POST] /app/code <br>
     * 获取验证码
     */
    @PostMapping(value = "/code")
    public Result<?> code(String ke,String us,String na,String wd) {
        if(StringUtils.isBlank(us) || StringUtils.isBlank(na) || StringUtils.isBlank(ke)){
            return Result.error("请求有误");
        }
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            String username  = new String(decoder.decode(ke), "UTF-8");
            na  = new String(decoder.decode(na), "UTF-8");
            if(na.length() < 6){
                return Result.error("请求有误");
            }
            //us-前台先MD5后base64加密后token，na-时间戳（先倒序再第二位开始取5位）
            StringBuffer sb =  new StringBuffer(na);
            String timesignStart = sb.reverse().toString().substring(1,6);
            String timesignEnd = na.toString().substring(1,6);
            //加密对比
            String selfToken = timesignStart.concat(username).concat(timesignEnd);
            selfToken = yll.common.tools.StringUtils.getMD5(selfToken);
            selfToken = Base64.getEncoder().encodeToString(selfToken.getBytes("UTF-8"));
            if(selfToken.equals(us)){
                Customer condition = new Customer();
                condition.setUsername(username);
                condition.setPassword(wd);
                //会静默进行 手机注册
                String token = commonUtil.getCode(condition);
                return Result.ok(token);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Result.error("请求有误");
    }

    /**
     * [POST] /app/check-code <br>
     * 校验验证码
     * @param
     * @return
     */
    @PostMapping(value = "/check-code")
    public Result<?> checkCode(AppToken appToken) {
        try {
            commonUtil.checkCode(appToken);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
        return Result.ok();
    }

    /**
     * [POST] /app/bind <br>
     * 绑定手机号
     * @param
     * @return
     */
    @PostMapping(value = "/bind")
    public Result<?> bind(AppToken condition, String phone) throws Exception{
        if(StringUtils.isBlank(condition.getToken())){
            return Result.error("缺少token");
        }
        if(StringUtils.isBlank(condition.getPhone())){
            return Result.error("缺少手机号");
        }
        if(null == condition.getCode()){
            return Result.error("验证码不能为空");
        }
        condition.setType(5);   //手机号绑定标识
//        phone = condition.getUsername();
        try {
            //校验验证码
            commonUtil.checkCode(condition);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(e.getMessage());
        }
        //手机号唯一校验
        Customer entity = new Customer();
        entity.setPhone(phone);
        entity = customerService.getByCondition(entity);
        if(null != entity){
            return Result.error("该手机号已被绑定");
        }
        //绑定手机号
        String customerId = AppSecuritysUtil.getCustomerId();
        entity = customerService.getById(customerId);
        entity.setPhone(phone);
        entity.setPassword("");
        customerService.update(entity);
        //账号合并(手机账号合并至第三方登录账号)
        customerService.merge(entity);
        return Result.ok();
    }

    /**
     * [POST] /app/dic <br>
     * 获取字典
     * @param
     * @return
     */
    @PostMapping(value = "/dic")
    public Result<?> dic(String type) {
        if(StringUtils.isBlank(type)){
            //dic_type表的code
            return Result.error("缺少参数type");
        }
        List<Dic> list = dicService.getAppList(type);
        return Result.ok(list);
    }

    /**
         * [POST] /app/dic-module <br>
         * 获取模块字典
         * @param
         * @return
         */
        @PostMapping(value = "/dic-module")
        public Result<?> dicModule() {
            List<Dic> customerList = dicService.getAppList(YllConstants.TABLE_CUSTOMER);
            //组合
            Map result = new HashMap();
            result.put("customer", customerList);
            return Result.ok(result);
        }

    /**
     * [POST] /app/head-image <br>
     * 设置头像
     * @param
     * @return
     */
    @PostMapping(value = "/head-image")
    public Result<?> headImage(String headImage) {
        if(StringUtils.isBlank(headImage)){
            return Result.error("缺少参数headImage");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer entity = customerService.getById(customerId);
        entity.setHeadImg(headImage);
        customerService.update(entity);
        return Result.ok();
    }

    /**
     * [POST] /app/nickname <br>
     * 设置昵称
     * @param
     * @return
     */
    @PostMapping(value = "/nickname")
    public Result<?> nickname(String nickname) {
        if(StringUtils.isBlank(nickname)){
            return Result.error("缺少参数nickname");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer entity = customerService.getById(customerId);
        entity.setNickname(nickname);
        entity.setPassword("");
        customerService.update(entity);
        return Result.ok();
    }

    /**
     * 文件上传
     * [POST] /app/upload <br>
     *
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping(value = "/upload")
    public Result<?> upload(HttpServletRequest request, MultipartFile file) throws IOException {
        if(null == file){
            Result.error("缺少file");
        }
        String path = FileOperateUtil.upload(file, customProperties.getFileStoreDirectory()).getData().toString();
        String prefix = FileOperateUtil.serverUrlPrefix(request);
        return Result.ok(prefix.concat("/app/preview?path=").concat(path));
    }

    /**
     * 文件预览
     * [GET] /app/preview <br>
     *
     * @thrs ParseException
     * @throws IOException
     */
    @RequestMapping(value = "/preview")
    public void preview(HttpServletRequest request, HttpServletResponse response, String path) {
//        FileOperateUtil.preview(path, customProperties.getFileStoreDirectory(), request, response);
        FileOperateUtil.rang(path, customProperties.getFileStoreDirectory(), request, response);
    }

    /**
     * 安卓APP下载
     * [GET] /app/download <br>
     *
     * @thrs ParseException
     * @throws IOException
     */
    @RequestMapping(value = "/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {
        String path = "/download/fentiao.apk";
        FileOperateUtil.rang(path, customProperties.getFileStoreDirectory(), request, response);
    }

    /**
     * 创建桶位-永久密钥（暂不用）
     * [GET] /app/create-bucket <br>
     *
     * @param bucketName
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/create-bucket")
    public Result<?> createBucket(String bucketName) {
        try {
            return storeService.createBucket(bucketName);
        } catch (Exception e) {
            log.error("create bucket error ", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 上传文件对象-永久密钥（暂不用）
     *  [POST] /app/upload-object <br>
     *
     * @param storeRequest
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/upload-object")
    public Result<?> uploadObject(StoreRequest storeRequest, MultipartFile file) {
        try {
            if (file != null) {
                storeRequest.setPathKey(path);
                storeRequest.setInputStream(file.getInputStream());
                storeRequest.setStreamLength(file.getSize());
                storeRequest.setFileName(FileOperateUtil.formateFileName(file));
            }
            return storeService.uploadObject(storeRequest);
        } catch (Exception e) {
            log.error("upload object error", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 文件上传-临时密钥（暂不用）
     * [POST] /app/cos-upload <br>
     *
     * @throws ParseException
     * @throws IOException
     */
    @PostMapping(value = "/cos-upload")
    public Result<?> cosUpload(StoreRequest storeRequest, MultipartFile file) throws IOException {
        try {
            if (file != null) {
                storeRequest.setPathKey(path);
                storeRequest.setRequestType(storeRequest.getRequestTypePut());
                storeRequest.setInputStream(file.getInputStream());
                storeRequest.setStreamLength(file.getSize());
                storeRequest.setFileName(FileOperateUtil.formateFileName(file));
            }
            return fileService.upload(storeRequest);
        } catch (Exception e) {
            log.error("upload object error", e);
            return Result.error(e.getMessage());
        }
    }

    /**
     * 获取密钥给第三方
     * [GET] /app/cos-keys <br>
     *
     */
    @GetMapping(value = "/cos-keys")
    public Result<?> keys(StoreRequest storeRequest, MultipartFile file) {
        fileService.getCredentialKeys(storeRequest);
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> credentials = new HashMap<>();
        credentials.put("tmpSecretId", storeRequest.getTmpSecretId());
        credentials.put("tmpSecretKey", storeRequest.getTmpSecretKey());
        credentials.put("sessionToken", storeRequest.getSessionToken());
        credentials.put("startTime", Long.parseLong(storeRequest.getStartTime()));
        credentials.put("expiredTime", Long.parseLong(storeRequest.getExpiredTime()));

        result.put("credentials", credentials);
        result.put("path", path);
        return Result.ok(result);
    }

    /**
     * 获取随机数
     * [GET] /app/random <br>
     *
     */
    @GetMapping(value = "/random")
    public Result<?> random() {
        return Result.ok(IdHelper.nextId());
    }

    /**
     * apk更新
     * [GET] /app/update <br>
     *
     */
    @GetMapping(value = "/update")
    public Result<?> update() {
        AppVersion entity = appVersionService.findByApp(null);
        return Result.ok(entity);
    }


    /**
     * [POST] /app/testupdate <br>
     * 测试
     */
    @PostMapping(value = "/testupdate")
    public Result<?> testUpdate(Customer condition) {
        customerService.update(condition);
        return Result.ok();
    }


    @Autowired
    private ProcessService processService;

    /**
     * [POST] /app/test-process <br>
     * 测试
     */
    @PostMapping(value = "/test-process")
    public Result<?> testProcess(ProcessVo condition) {
        List<ProcessVo> list = processService.getList(condition);
        return Result.ok(list);
    }
}
