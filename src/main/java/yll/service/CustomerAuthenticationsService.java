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
import yll.entity.CustomerAuthentications;
import yll.mapper.CustomerAuthenticationsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerAuthenticationsVo;
import yll.service.model.vo.InformationVo;

import java.util.List;

/**
 *  企业认证
 */
@Transactional
@Service
public class CustomerAuthenticationsService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerAuthenticationsMapper customerAuthenticationsMapper;
    @Autowired
    private AppSecuritys securitys;
    @Autowired
    private Securitys adminSecuritys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(CustomerAuthentications vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerAuthentications entity = new CustomerAuthentications();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(principal.getCustomerId());
        entity.setLicences(vo.getLicences());
        entity.setLicencesImg(vo.getLicencesImg());
        entity.setIdName(vo.getIdName());
        entity.setIdCard(vo.getIdCard());
        entity.setIdImg(vo.getIdImg());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ZERO);     //待审核
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        customerAuthenticationsMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerAuthenticationsMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerAuthenticationsId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerAuthentications vo) {
        CustomerAuthentications entity = new CustomerAuthentications();
        Object principal = adminSecuritys.getPrincipal();
        Principal adminPrincipal = (Principal)principal;
        if(null == principal || StringUtils.isBlank(adminPrincipal.getUserId())){
            //App
            validate(vo);
            principal = securitys.getAppPrincipal();
            List<CustomerAuthentications> list = customerAuthenticationsMapper.findBy(vo);
            if(null == list || list.size() == 0){
                throw ExceptionHelper.prompt("数据不存在或者已经失效");
            }
            entity = list.get(0);
        } else{
            //后台管理
            entity = customerAuthenticationsMapper.getById(vo.getId());
            if (null == entity) {
                throw ExceptionHelper.prompt("数据不存在或者已经失效");
            }
        }

        if(StringUtils.isNotBlank(vo.getLicences())){
            entity.setLicences(vo.getLicences());
        }
        if(StringUtils.isNotBlank(vo.getLicencesImg())){
            entity.setLicencesImg(vo.getLicencesImg());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        if(StringUtils.isNotBlank(adminPrincipal.getUserId())){
            //后台修改
            entity.setRemark(vo.getRemark());
            CommonAttributeUtil.setUpdated(entity, (Principal)principal);
        } else{
            //用户修改
            entity.setLicences(vo.getLicences());
            entity.setLicencesImg(vo.getLicencesImg());
            entity.setIdName(vo.getIdName());
            entity.setIdCard(vo.getIdCard());
            entity.setIdImg(vo.getIdImg());
            entity.setState(YllConstants.ONE);     //待审核
            CommonAttributeUtil.setUpdated(entity, (AppPrincipal)principal);
        }
        customerAuthenticationsMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerAuthentications entity = new CustomerAuthentications();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerAuthenticationsMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerAuthentications getById(String id) {
        CustomerAuthentications entity = customerAuthenticationsMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerAuthentications> pagedQuery(Pagination pagination, CustomerAuthentications condition) {
        return MybatisHelper.selectPage(pagination, () -> customerAuthenticationsMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerAuthentications> pagedQueryWithType(Pagination pagination, CustomerAuthentications condition) {
        return MybatisHelper.selectPage(pagination, () -> customerAuthenticationsMapper.findByWithName(condition));
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public CustomerAuthenticationsVo getAppDetail(CustomerAuthenticationsVo condition) {
        return customerAuthenticationsMapper.getAppDetail(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerAuthentications vo) {
        String id = vo.getId();
        String targetId = vo.getTargetId();
        String licences = vo.getLicences();
        String licencesImg = vo.getLicencesImg();

        if (StringUtils.isBlank(targetId)) {
            throw ExceptionHelper.prompt("归属用户不能为空");
        }
        if (StringUtils.isBlank(licences)) {
            throw ExceptionHelper.prompt("许可证号码不能为空");
        }
        if (StringUtils.isBlank(licencesImg)) {
            throw ExceptionHelper.prompt("许可证照片不能为空");
        }
    }

}
