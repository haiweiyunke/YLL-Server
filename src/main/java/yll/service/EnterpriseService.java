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
import yll.entity.Customer;
import yll.entity.Enterprise;
import yll.mapper.CustomerMapper;
import yll.mapper.EnterpriseMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.EnterpriseVo;

import java.util.List;


/**
 * 企业主信息
 */
@Transactional
@Service
public class EnterpriseService {

    // ==============================Fields===========================================
    @Autowired
    private EnterpriseMapper enterpriseMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public Enterprise insert(Enterprise vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Enterprise entity = new Enterprise();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setEnterpriseName(vo.getEnterpriseName());
        entity.setCreditCode(vo.getCreditCode());
        entity.setSuperintendent(vo.getSuperintendent());
        entity.setPhone(vo.getPhone());
        entity.setDescription(vo.getDescription());
        entity.setBusinessLicense(vo.getBusinessLicense());

        entity.setEstablishTime(vo.getEstablishTime());
        entity.setRegisteredCapital(vo.getRegisteredCapital());
        entity.setLegalPerson(vo.getLegalPerson());
        entity.setIndustry(vo.getIndustry());
        entity.setStaff(vo.getStaff());
        entity.setLogo(vo.getLogo());

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
            customer.setRoleType("4");
            customerMapper.update(customer);
        }
        enterpriseMapper.insert(entity);

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
        enterpriseMapper.deleteById(id);
    }

    /**
     * 更新
     * @param
     */
    public Enterprise update(Enterprise vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Enterprise entity = enterpriseMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setEnterpriseName(vo.getEnterpriseName());
        entity.setCreditCode(vo.getCreditCode());
        entity.setSuperintendent(vo.getSuperintendent());
        entity.setPhone(vo.getPhone());
        entity.setDescription(vo.getDescription());
        entity.setBusinessLicense(vo.getBusinessLicense());

        entity.setEstablishTime(vo.getEstablishTime());
        entity.setRegisteredCapital(vo.getRegisteredCapital());
        entity.setLegalPerson(vo.getLegalPerson());
        entity.setIndustry(vo.getIndustry());
        entity.setStaff(vo.getStaff());
        entity.setLogo(vo.getLogo());

        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));
        if(StringUtils.isBlank(vo.getModifier())){
            CommonAttributeUtil.setUpdated(entity, principal);
        } else {
            //后台直接添加、用户自己注册
            entity.setModifier(vo.getModifier());
            entity.setModifiedTime(vo.getModifiedTime());
        }
        CommonAttributeUtil.setUpdated(entity, principal);
        enterpriseMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Enterprise entity = new Enterprise();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        enterpriseMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Enterprise getById(String id) {
        Enterprise entity = enterpriseMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public Enterprise getByCondition(Enterprise condition) {
        return enterpriseMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Enterprise> pagedQuery(Pagination pagination, Enterprise condition) {
        return MybatisHelper.selectPage(pagination, () -> enterpriseMapper.findBy(condition));
    }

    /**
     * 查询所有
     * @param condition 查询条件
     * @return 结果
     */
    public List<Enterprise> all(Enterprise condition) {
        return enterpriseMapper.findBy(condition);
    }

    /**
     * 查询所有(shop编辑页使用)
     * @param condition 查询条件
     * @return 结果
     */
    public List<EnterpriseVo> find4Shop(Enterprise condition) {
        return enterpriseMapper.find4Shop(condition);
    }

    /**
     * 查询全部（根据条件）-app认证详情
     * @param
     * @return 实体
     */
    public EnterpriseVo getAppAuth(Enterprise condition) {
        return enterpriseMapper.getAppAuth(condition);
    }

    /**
     * 分页查询-后台使用
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<EnterpriseVo> findByAdminList(Pagination pagination, EnterpriseVo condition) {
        return MybatisHelper.selectPage(pagination, () -> enterpriseMapper.findByAdminList(condition));
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Enterprise vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
