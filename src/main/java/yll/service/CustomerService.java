package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.configuration.CustomProperties;
import yll.common.identifier.IdHelper;
import yll.common.security.app.AppToken;
import yll.common.standard.AuditableUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.component.util.CommonUtil;
import yll.entity.*;
import yll.mapper.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerVo;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

/**
 * app用户
 */
@Transactional
@Service
public class CustomerService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerPointsService customerPointsService;
    @Autowired
    private CustomerCollectsMapper customerCollectsMapper;
    @Autowired
    private CustomerLikesMapper customerLikesMapper;
    @Autowired
    private CustomerActivitiesMapper customerActivitiesMapper;
    @Autowired
    private CustomerActivitiesAnswerMapper customerActivitiesAnswerMapper;
    @Autowired
    private CustomerAddressesMapper customerAddressesMapper;
    @Autowired
    private CustomerAuthenticationsMapper customerAuthenticationsMapper;
    @Autowired
    private CustomerFeedbackMapper customerFeedbackMapper;
    @Autowired
    private CustomerPointsDetailsMapper customerPointsDetailsMapper;
    @Autowired
    private InternetCelebrityMapper internetCelebrityDetailsMapper;
    @Autowired
    private McnMapper mcnMapper;
    @Autowired
    private EnterpriseMapper enterpriseMapper;


    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomProperties customProperties;
    @Autowired
    private Securitys securitys;

    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private McnService mcnService;
    @Autowired
    private EnterpriseService enterpriseService;


    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Customer insert(Customer vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Customer entity = new Customer();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setUsername(vo.getUsername());
        entity.setRemark(vo.getRemark());
        entity.setPhone(vo.getPhone());
        entity.setFenNum(entity.getId().substring(2, 21));  //截取部分id作为头条号，如200402205427027。截到21位

        entity.setNickname(vo.getNickname());
        entity.setHeadImg(vo.getHeadImg());
        entity.setBirthday(vo.getBirthday());
        entity.setLocation(vo.getLocation());
        entity.setRoleType(vo.getRoleType());
        entity.setGender(vo.getGender());
        entity.setAge(vo.getAge());
        entity.setEmail(vo.getEmail());

        String password = vo.getPassword();
        if(StringUtils.isNotBlank(vo.getPassword())){
            try {
                //先MD5再base64，commonUtil.encoder方法会先进行base解密
                password = yll.common.tools.StringUtils.getMD5("111111");
                password = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
                password = commonUtil.encoder(password, entity.getId());
                entity.setPassword(password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw ExceptionHelper.prompt("密码设置失败");
            }
        }

        entity.setSlide(YllConstants.ZERO);
        entity.setShare(YllConstants.ZERO);
        entity.setLikes(YllConstants.ZERO);
        entity.setCollects(YllConstants.ZERO);

        entity.setState(YllConstants.ONE);
        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        CommonAttributeUtil.setCreated(entity, principal);
        customerMapper.insert(entity);


        //积分表新增数据
        CustomerPoints customerPoints = new CustomerPoints();
        customerPoints.setTargetId(entity.getId());
        customerPoints.setPoint(YllConstants.ZERO);
        customerPointsService.insert(customerPoints);
        //根据角色类型，新增不同表的数据（1-普通，2-达人，3-mcn，4-企业主）
        String roleType = vo.getRoleType();
        addByRoleType(entity, roleType);
        return entity;
    }

    /**
     * 新增
     * @param
     */
    public Customer insertForExcel(Customer vo) {

        Customer entity = new Customer();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        String fenNum = entity.getId().substring(2, 21);
        if(StringUtils.isBlank(vo.getUsername())){
            vo.setUsername(fenNum);
        }

        validate(vo);
        vo.setId(entity.getId());

        Principal principal = securitys.getPrincipal();

        entity.setUsername(vo.getUsername());
        entity.setRemark(vo.getRemark());
        entity.setPhone(vo.getPhone());
        entity.setFenNum(fenNum);  //截取部分id作为头条号，如20040220542702

        entity.setNickname(vo.getNickname());
        entity.setHeadImg(vo.getHeadImg());
        entity.setBirthday(vo.getBirthday());
        entity.setLocation(vo.getLocation());
        entity.setRoleType(vo.getRoleType());
        entity.setGender(vo.getGender());
        entity.setAge(vo.getAge());
        entity.setEmail(vo.getEmail());

        String password = vo.getPassword();
        if(StringUtils.isNotBlank(vo.getPassword())){
            try {
                //先MD5再base64，commonUtil.encoder方法会先进行base解密
                password = yll.common.tools.StringUtils.getMD5("111111");
                password = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
                password = commonUtil.encoder(password, entity.getId());
                entity.setPassword(password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw ExceptionHelper.prompt("密码设置失败");
            }
        }

        entity.setSlide(YllConstants.ZERO);
        entity.setShare(YllConstants.ZERO);
        entity.setLikes(YllConstants.ZERO);
        entity.setCollects(YllConstants.ZERO);

        entity.setState(YllConstants.ONE);
        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        CommonAttributeUtil.setCreated(entity, principal);
        customerMapper.insert(entity);

        //积分表新增数据
        CustomerPoints customerPoints = new CustomerPoints();
        customerPoints.setTargetId(entity.getId());
        customerPoints.setPoint(YllConstants.ZERO);
        customerPointsService.insert(customerPoints);
        return entity;
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        Customer entity = customerMapper.getById(id);
        String roleType = entity.getRoleType();
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerMapper.deleteById(id);
        //删除角色资料
        if("2".equals(roleType)){
            internetCelebrityDetailsMapper.deleteByCreator(id);
        } else if("3".equals(roleType)){
            mcnMapper.deleteByCreator(id);
        } else if("4".equals(roleType)){
            enterpriseMapper.deleteByCreator(id);
        }
        //删除地址及积分表（暂时不删，防止误删后，能找回数据）
    }

    /**
     * 更新
     * @param
     */
    public Customer update(Customer vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Customer entity = customerMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        //若密码为空，设为默认密码111111
        /*if(StringUtils.isBlank(entity.getPassword())){
            try {
                String password = commonUtil.encoder("111111", entity.getId());
                entity.setPassword(password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }*/

        entity.setRemark(vo.getRemark());
        entity.setPhone(vo.getPhone());

        if(StringUtils.isNotBlank(vo.getUsername())){
            entity.setUsername(vo.getUsername());
        }
        if(StringUtils.isNotBlank(vo.getHeadImg())){
            entity.setHeadImg(vo.getHeadImg());
        }
        if(StringUtils.isBlank(entity.getFenNum())){
            entity.setFenNum(entity.getId().substring(2, 16));  //截取部分id作为头条号
        }
        if(null != vo.getNickname()){
            entity.setNickname(vo.getNickname());
        }

        entity.setBirthday(vo.getBirthday());
        entity.setLocation(vo.getLocation());
        entity.setRoleType(vo.getRoleType());
        entity.setGender(vo.getGender());
        entity.setAge(vo.getAge());
        entity.setEmail(vo.getEmail());

        String password = vo.getPassword();
        if(StringUtils.isNotBlank(vo.getPassword())){
            try {
                password = commonUtil.encoder(vo.getPassword(), entity.getId());
                entity.setPassword(password);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw ExceptionHelper.prompt("密码设置失败");
            }
        }

        if(null != vo.getLikes()){
            entity.setLikes(vo.getLikes());
        }
        if(null != vo.getCollects()){
            entity.setCollects(vo.getCollects());
        }
        if(null != vo.getShare()){
            entity.setShare(vo.getShare());
        }
        if(null != vo.getSlide()){
            entity.setSlide(vo.getSlide());
        }

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerMapper.update(entity);
        //根据角色类型，新增不同表的数据（1-普通，2-达人，3-mcn，4-企业主）
        String roleType = vo.getRoleType();
        addByRoleType(entity, roleType);
        return entity;
    }


    /**
     * 新增角色类型
     * @param entity
     * @param roleType
     */
    private void addByRoleType(Customer entity, String roleType) {
        if("2".equals(roleType)){
            //达人
            InternetCelebrity ic = new InternetCelebrity();
//            ic.setState(YllConstants.TWO);
            ic.setEnabled(YllConstants.ONE);
            ic.setDeleted(YllConstants.ZERO);
            CommonAttributeUtil.setCreated(ic, entity.getId());
            InternetCelebrity intervo = internetCelebrityService.getByCondition(ic);
            if(null == intervo){
                ic.setDisclosure(YllConstants.ONE);
                internetCelebrityService.insert(ic);
            }
        } else if("3".equals(roleType)){
            //mcn
            Mcn mcn = new Mcn();
//            mcn.setState(YllConstants.TWO);
            mcn.setEnabled(YllConstants.ONE);
            mcn.setDeleted(YllConstants.ZERO);
            CommonAttributeUtil.setCreated(mcn, entity.getId());
            Mcn mcnvo = mcnService.getByCondition(mcn);
            if(null == mcnvo){
                mcnService.insert(mcn);
            }
        }else if("4".equals(roleType)){
            //企业主
            Enterprise enterprise = new Enterprise();
//            enterprise.setState(YllConstants.TWO);
            enterprise.setEnabled(YllConstants.ONE);
            enterprise.setDeleted(YllConstants.ZERO);
            CommonAttributeUtil.setCreated(enterprise, entity.getId());
            Enterprise entervo = enterpriseService.getByCondition(enterprise);
            if(null == entervo){
                enterpriseService.insert(enterprise);
            }
        }
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Customer entity = new Customer();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerMapper.update(entity);
    }

    /**
     * 重置用户密码
     * @param id
     */
    public void reset(String id) {
        Principal principal = securitys.getPrincipal();
        Customer entity = customerMapper.getById(id);
        if (entity == null) {
            throw ExceptionHelper.prompt("用户不存在或者已经失效");
        }
        String password = customProperties.getDefaultCustomerPassword();
        try {
            password =  yll.common.tools.StringUtils.getMD5(password);
            password = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
            password = commonUtil.encoder(password, entity.getId());
            entity.setPassword(password);
            customerMapper.update(entity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Customer getById(String id) {
        Customer entity = customerMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public Customer getByCondition(Customer condition) {
        return customerMapper.findByCondition(condition);
    }

    /**
     * 分页列表查询（附带积分）
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Customer> pagedQueryWithPoints(Pagination pagination, Customer condition) {
        return MybatisHelper.selectPage(pagination, () -> customerMapper.pagedQueryWithPoints(condition));
    }

    /**
     * 分页列表查询（附带积分）
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerVo> pagedQueryWithPointsVo(Pagination pagination, CustomerVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerMapper.pagedQueryWithPoints(condition));
    }

    /**
     * 查询集合
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Customer> findBy(Customer condition) {
        return customerMapper.findBy(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Customer> pagedQuery(Pagination pagination, Customer condition) {
        return MybatisHelper.selectPage(pagination, () -> customerMapper.findBy(condition));
    }

    /**
     * 查询用户-app
     * @param condition 查询条件
     * @return 分页结果
     */
    public Customer getAppDetail(Customer condition) {
        return customerMapper.getAppDetail(condition);
    }

    /**
     * 根据条件查询所有昵称-app
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Customer> findAllForTask(Customer condition) {
        return customerMapper.findAllForTask(condition);
    }



    /**
     * 登录（密码登录）
     *  加密验证过程： app ：明文 -> MD5 -> Base64    后台：Base64解 -> 加盐（用户主键id）-> MD5加密  -> 与数据库中密码比对
     * @param
     * @return 实体
     */
    public Customer login(AppToken token) {
        String username = token.getUsername();
        //app请求密码(先MD5，再base64的密码)
        String requestPassword = token.getPassword();
        try {
            Customer entity = new Customer();
            entity.setUsername(username);
            entity = customerMapper.findByCondition(entity);
            if(null  != entity){
                if(StringUtils.isBlank(entity.getPassword())){
                    throw ExceptionHelper.prompt("未设置登录密码，请尝试其它登陆方式");
                }
                //密码加密
                requestPassword = commonUtil.encoder(requestPassword, entity.getId());
                //数据库的加密密码
                String dbPassword = entity.getPassword();
                //密码比对
                if(!dbPassword.equals(requestPassword)){
                    throw ExceptionHelper.prompt("用户名或密码错误");
                }
                //登录成功
                return entity;
            }
            return entity;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw ExceptionHelper.prompt("登录失败");
        }
    }

    /**
     * 账号合并
     * @param vo 查询条件
     */
    public void merge(Customer vo) {
        //判断表中有无使用此手机号的账号
        Customer old = new Customer();
        old.setUsername(vo.getPhone());
        old = customerMapper.findByCondition(old);
        if(StringUtils.isBlank(vo.getUsername())){
            //手机号同步至username
            vo.setUsername(vo.getPhone());
        }
        if(null != old && !old.getId().equals(vo.getId())){
            //两个不同账号，将手机账号数据同步到当前账号
            mergeData(vo, old);
            customerMapper.deleteById(old.getId());
        }
        customerMapper.update(vo);
    }

    /**
     * 合并数据
     * @param vo   新数据
     * @param old   旧数据
     */
    public void mergeData(Customer vo, Customer old) {
        //收藏
        customerCollectsMapper.updateMerge(old.getId(), vo.getId());
        //点赞
        customerLikesMapper.updateMerge(old.getId(), vo.getId());
        //活动
        customerActivitiesMapper.updateMerge(old.getId(), vo.getId());
        //竞赛结果
        customerActivitiesAnswerMapper.updateMerge(old.getId(), vo.getId());
        //地址
        customerAddressesMapper.updateMerge(old.getId(), vo.getId());
        //认证信息
        CustomerAuthentications cAuth = new CustomerAuthentications();
        cAuth.setTargetId(vo.getId());
        List<CustomerAuthentications> authList = customerAuthenticationsMapper.findBy(cAuth);
        if(null == authList || authList.size() == 0){
            //若当前账号认证信息为空，则将原账号认证信息进行同步
            customerAuthenticationsMapper.updateMerge(old.getId(), vo.getId());
        } else{
            customerAuthenticationsMapper.deleteById(old.getId());
        }
        //意见反馈
        customerFeedbackMapper.updateMerge(old.getId(), vo.getId());
        //积分详情
        customerPointsDetailsMapper.updateMerge(old.getId(), vo.getId());
        //积分合并
        CustomerPoints newCp = new CustomerPoints();
        newCp.setTargetId(vo.getId());
        newCp = customerPointsService.getByTargetId(newCp);
        CustomerPoints oldCp = new CustomerPoints();
        oldCp.setTargetId(old.getId());
        oldCp = customerPointsService.getByTargetId(oldCp);
        newCp.setPoint(newCp.getPoint() + oldCp.getPoint());
        customerPointsService.update(newCp);
        customerPointsService.deleteById(oldCp.getId());
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Customer vo) {
        String id = vo.getId();
        String username = vo.getUsername();
        String wechatId = vo.getWechatId();
        String aliId = vo.getAliId();
        //String password = vo.getPassword();

        if (StringUtils.isEmpty(username) && StringUtils.isEmpty(wechatId) && StringUtils.isEmpty(aliId)) {
            throw ExceptionHelper.prompt("登录名或第三方标识不能为空");
        }
       /* if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
        Customer entity = new Customer();
        entity.setUsername(username);
        entity = customerMapper.findByCondition(entity);
        if(null != entity && !id.equals(entity.getId()) && StringUtils.isBlank(wechatId) && StringUtils.isBlank(aliId)){
            throw ExceptionHelper.prompt("该用户已存在");
        }
    }

}
