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
import yll.common.identifier.IdHelper;
import yll.common.standard.CommonAttributeUtil;
import yll.component.util.CommonUtil;
import yll.entity.Customer;
import yll.entity.Mcn;
import yll.mapper.CustomerMapper;
import yll.mapper.McnMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.McnVo;

import java.util.List;


/**
 * 网红（达人）信息
 */
@Transactional
@Service
public class McnService {

    // ==============================Fields===========================================
    @Autowired
    private McnMapper mcnMapper;
    @Autowired
    private CustomerPointsService customerPointsService;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Mcn insert(Mcn vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Mcn entity = new Mcn();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setMcnName(vo.getMcnName());
        entity.setLogo(vo.getLogo());
        entity.setSuperintendent(vo.getSuperintendent());
        entity.setPhone(vo.getPhone());
        entity.setCelebrityNumber(vo.getCelebrityNumber());
        entity.setDescription(vo.getDescription());
        entity.setAuthenticateLink(vo.getAuthenticateLink());
        entity.setQueryLink(vo.getQueryLink());
        entity.setCorporateImage(vo.getCorporateImage());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setEstablishTime(vo.getEstablishTime());
        entity.setRegisteredCapital(vo.getRegisteredCapital());
        entity.setLegalPerson(vo.getLegalPerson());
        entity.setIndustry(vo.getIndustry());
        entity.setLocation(vo.getLocation());
        entity.setCreditCode(vo.getCreditCode());

        entity.setRemark(vo.getRemark());

        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
            //修改用户角色
            Customer customer = new Customer();
            customer.setId(vo.getCreator());
            customer.setRoleType("3");
            customerMapper.update(customer);
        }
        mcnMapper.insert(entity);

        return entity;
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        mcnMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public Mcn update(Mcn vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Mcn entity = mcnMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setMcnName(vo.getMcnName());
        entity.setLogo(vo.getLogo());
        entity.setSuperintendent(vo.getSuperintendent());
        entity.setPhone(vo.getPhone());
        entity.setCelebrityNumber(vo.getCelebrityNumber());
        entity.setDescription(vo.getDescription());
        entity.setAuthenticateLink(vo.getAuthenticateLink());
        entity.setQueryLink(vo.getQueryLink());
        entity.setCorporateImage(vo.getCorporateImage());
        entity.setBusinessLicense(vo.getBusinessLicense());
        entity.setEstablishTime(vo.getEstablishTime());
        entity.setRegisteredCapital(vo.getRegisteredCapital());
        entity.setLegalPerson(vo.getLegalPerson());
        entity.setIndustry(vo.getIndustry());
        entity.setLocation(vo.getLocation());
        entity.setCreditCode(vo.getCreditCode());

        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        mcnMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Mcn entity = new Mcn();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        mcnMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Mcn getById(String id) {
        Mcn entity = mcnMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public Mcn getByCondition(Mcn condition) {
        return mcnMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Mcn> pagedQuery(Pagination pagination, Mcn condition) {
        return MybatisHelper.selectPage(pagination, () -> mcnMapper.findBy(condition));
    }


    /**
     * 查询全部（根据条件）
     * @param
     * @return 实体
     */
    public List<Mcn> getAllByCondition(Mcn condition) {
        return mcnMapper.findBy(condition);
    }

    /**
     * 查询全部（根据条件）-app
     * @param
     * @return 实体
     */
    public List<Mcn> findAll4App(Mcn condition) {
        return mcnMapper.findAll4App(condition);
    }


    /**
     * 查询全部（根据条件）-app认证详情
     * @param
     * @return 实体
     */
    public McnVo getAppAuth(Mcn condition) {
        return mcnMapper.getAppAuth(condition);
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public McnVo getAppDetail(McnVo condition) {
        return mcnMapper.getAppDetail(condition);
    }

    /**
     * 分页查询-后台使用
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<McnVo> findByAdminList(Pagination pagination, McnVo condition) {
        return MybatisHelper.selectPage(pagination, () -> mcnMapper.findByAdminList(condition));
    }

    /**
     * 查询所有(shop编辑页使用)
     * @param condition 查询条件
     * @return 结果
     */
    public List<McnVo> find4Shop(Mcn condition) {
        return mcnMapper.find4Shop(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Mcn vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
