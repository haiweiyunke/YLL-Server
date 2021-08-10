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
import yll.common.security.app.AppSecuritysUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.CustomerCollects;
import yll.mapper.CustomerCollectsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerCollectsVo;

import java.util.List;

/**
 *  我的收藏
 */
@Transactional
@Service
public class CustomerCollectsService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerCollectsMapper customerCollectsMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 操作
     * @param
     */
    public void operation(CustomerCollects vo) {
        vo.setCustomerId(AppSecuritysUtil.getCustomerId());
        List<CustomerCollects> result = customerCollectsMapper.findById(vo);
        if(null != result && result.size() > 0){
            for (CustomerCollects cc : result) {
                customerCollectsMapper.deleteById(cc.getId());
            }
            //计数
            commonService.count(vo.getTargetId(), vo.getDicId(), YllConstants.YLL_COLLECTS, YllConstants.SUBTRACT);
        } else{
            insert(vo);
            //计数
            commonService.count(vo.getTargetId(), vo.getDicId(), YllConstants.YLL_COLLECTS, YllConstants.ADD);
        }
    }

    /**
     * 新增
     * @param
     */
    public void insert(CustomerCollects vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerCollects entity = new CustomerCollects();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getTargetId());
        entity.setCustomerId(principal.getCustomerId());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setDicId(vo.getDicId());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        customerCollectsMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerCollectsMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerCollectsId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerCollects vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerCollects entity = customerCollectsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setCustomerId(principal.getCustomerId());
        entity.setRemark(vo.getRemark());
        entity.setType(vo.getType());
        entity.setDicId(vo.getId());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerCollectsMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerCollects entity = new CustomerCollects();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerCollectsMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerCollects getById(String id) {
        CustomerCollects entity = customerCollectsMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerCollects> pagedQuery(Pagination pagination, CustomerCollects condition) {
        return MybatisHelper.selectPage(pagination, () -> customerCollectsMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerCollects> pagedQueryWithType(Pagination pagination, CustomerCollects condition) {
        return MybatisHelper.selectPage(pagination, () -> customerCollectsMapper.findByWithType(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerCollectsVo> pagedQueryWithJoin(Pagination pagination, CustomerCollectsVo condition) {
        AppPrincipal principal = securitys.getAppPrincipal();
        condition.setCustomerId(principal.getCustomerId());
        return MybatisHelper.selectPage(pagination, () -> customerCollectsMapper.findByWithJoin(condition));
    }

    /**
     * 分页查询 -客户规定列表展示详情页
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerCollectsVo> findByWithJoinXG(Pagination pagination, CustomerCollectsVo condition) {
        AppPrincipal principal = securitys.getAppPrincipal();
        condition.setCustomerId(principal.getCustomerId());
        return MybatisHelper.selectPage(pagination, () -> customerCollectsMapper.findByWithJoinXG(condition));
    }


    /**
     * 删除
     * @param condition 条件
     */
    public void delete(CustomerCollectsVo condition) {
        AppPrincipal principal = securitys.getAppPrincipal();
        condition.setCustomerId(principal.getCustomerId());
        Integer delType = condition.getDelType();
        if(YllConstants.ONE == delType){
            //id数组删除
            String ids = condition.getIds();
            if(StringUtils.isBlank(ids)){
                throw ExceptionHelper.prompt("id数组不能为空");
            }
            String[] idArray = ids.split(",");
            for (String id :
                    idArray) {
                condition.setId(id);
                customerCollectsMapper.deleteByIdAndCustomer(condition);
            }
        } else if(YllConstants.TWO == delType){
            //用户id删除
            customerCollectsMapper.deleteByCustomer(condition);
        } else{
            throw ExceptionHelper.prompt("删除方式不能为空");
        }
    }

    /**
     * 删除(根据指定关注)
     * @param
     */
    public void deleteByTargetId(CustomerCollectsVo condition) {
        if (StringUtils.isNotBlank(condition.getTargetId())) {
            customerCollectsMapper.deleteByTargetId(condition);
        }
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerCollects vo) {
        String id = vo.getId();
        String targetId = vo.getTargetId();
        String type = vo.getType();

        if (StringUtils.isEmpty(targetId)) {
            throw ExceptionHelper.prompt("收藏对象不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw ExceptionHelper.prompt("类型不能为空");
        }
    }

}
