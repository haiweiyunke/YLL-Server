package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
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
import yll.entity.CustomerAddresses;
import yll.mapper.CustomerAddressesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerAddressesVo;

import java.util.List;

/**
 * 用户地址
 */
@Transactional
@Service
public class CustomerAddressesService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerAddressesMapper customerAddressesMapper;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(CustomerAddresses vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerAddresses entity = new CustomerAddresses();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(principal.getCustomerId());
        entity.setName(vo.getName());
        entity.setPhone(vo.getPhone());
        entity.setProvince(vo.getProvince());
        entity.setCity(vo.getCity());
        entity.setDistrict(vo.getDistrict());
        entity.setDetailed(vo.getDetailed());
        entity.setRemark(vo.getRemark());

        if(YllConstants.TWO.equals(vo.getState())){
            //当设置地址为默认时，需要对其它地址进行处理
            customerAddressesMapper.updateInit(entity);
            entity.setState(vo.getState());
        } else{
            //处理用户地址
            checkAddresses(principal, entity);
        }

        if(StringUtils.isNotBlank(vo.getType())){
            entity.setType(vo.getType());
        } else{
            //默认为其它
            entity.setType(YllConstants.DIC_ADDRESSES_TAG_OTHER);
        }
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        customerAddressesMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        customerAddressesMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerAddressesId(id);   关联删除
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(CustomerAddresses vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerAddresses entity = customerAddressesMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setName(vo.getName());
        entity.setPhone(vo.getPhone());
        entity.setProvince(vo.getProvince());
        entity.setCity(vo.getCity());
        entity.setDistrict(vo.getDistrict());
        entity.setDetailed(vo.getDetailed());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            if(YllConstants.TWO.equals(vo.getState())){
                //当设置地址为默认时，需要对其它地址进行处理
                customerAddressesMapper.updateInit(entity);
            }
            entity.setState(vo.getState());
        }
        if(null != vo.getType()){
            entity.setType(vo.getType());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerAddressesMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public CustomerAddresses getById(String id) {
        CustomerAddresses entity = customerAddressesMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerAddresses> pagedQuery(Pagination pagination, CustomerAddresses condition) {
        return MybatisHelper.selectPage(pagination, () -> customerAddressesMapper.findBy(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerAddressesVo> getAppList(Pagination pagination, CustomerAddressesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerAddressesMapper.getAppList(condition));
    }

    /**
     * 详情查询-App
     * @param condition 查询条件
     * @return 详情结果
     */
    public CustomerAddressesVo getAppDetail(CustomerAddressesVo condition) {
        return customerAddressesMapper.getAppDetail(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerAddresses vo) {
        String id = vo.getId();
        String name = vo.getName();
        String phone = vo.getPhone();
        String province = vo.getProvince();
        String city = vo.getCity();
        String district = vo.getDistrict();
        String detailed = vo.getDetailed();

        if (StringUtils.isBlank(name)) {
            throw ExceptionHelper.prompt("姓名不能为空");
        }
        if (StringUtils.isBlank(phone)) {
            throw ExceptionHelper.prompt("手机号码不能为空");
        }
         if (StringUtils.isBlank(province) || StringUtils.isBlank(city) || StringUtils.isBlank(district) || StringUtils.isBlank(detailed)) {
            throw ExceptionHelper.prompt("请完整填写地址信息");
        }
    }

    /**
     * 处理用户地址状态
     * @param principal
     * @param entity
     */
    public void checkAddresses(AppPrincipal principal, CustomerAddresses entity) {
        //无地址记录时，当前记录为默认地址
        CustomerAddresses ca = new CustomerAddresses();
        ca.setTargetId(principal.getCustomerId());
        List<CustomerAddresses> list = customerAddressesMapper.findBy(ca);
        if (null == list || list.size() < 1){
            entity.setState(YllConstants.TWO);
        } else {
            entity.setState(YllConstants.ONE);
        }
    }

}
