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
import yll.entity.CustomerHistories;
import yll.mapper.CustomerHistoriesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerHistoriesVo;

import java.util.List;

/**
 *  历史记录
 */
@Transactional
@Service
public class CustomerHistoriesService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerHistoriesMapper customerHistoriesMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 操作
     * @param
     */
    public void operation(String id, String type, String dicId) {
        CustomerHistories ch = new CustomerHistories();
        ch.setTargetId(id);
        ch.setCustomerId(AppSecuritysUtil.getCustomerId());
        List<CustomerHistories> list = customerHistoriesMapper.findBy(ch);
        if (list != null && list.size() > 0) {
            CustomerHistories vo = list.get(0);
            AppPrincipal principal = securitys.getAppPrincipal();
            CommonAttributeUtil.setCreated(vo, principal);
            update(vo);
        } else{
            ch.setDicId(dicId);
            ch.setType(type);
            insert(ch);
        }
    }

    /**
     * 新增
     * @param
     */
    public void insert(CustomerHistories vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerHistories entity = new CustomerHistories();
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
        customerHistoriesMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerHistoriesMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerHistoriesId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerHistories vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerHistories entity = customerHistoriesMapper.getById(vo.getId());

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

        CommonAttributeUtil.setCreated(entity, principal);
        CommonAttributeUtil.setUpdated(entity, principal);
        customerHistoriesMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerHistories entity = new CustomerHistories();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerHistoriesMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerHistories getById(String id) {
        CustomerHistories entity = customerHistoriesMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerHistories> pagedQuery(Pagination pagination, CustomerHistories condition) {
        return MybatisHelper.selectPage(pagination, () -> customerHistoriesMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerHistories> pagedQueryWithType(Pagination pagination, CustomerHistories condition) {
        return MybatisHelper.selectPage(pagination, () -> customerHistoriesMapper.findByWithType(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerHistoriesVo> pagedQueryWithJoin(Pagination pagination, CustomerHistoriesVo condition) {
        AppPrincipal principal = securitys.getAppPrincipal();
        condition.setCustomerId(principal.getCustomerId());
        return MybatisHelper.selectPage(pagination, () -> customerHistoriesMapper.findByWithJoin(condition));
    }

    /**
     * 删除
     * @param condition 条件
     */
    public void delete(CustomerHistoriesVo condition) {
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
                customerHistoriesMapper.deleteByIdAndCustomer(condition);
            }
        } else if(YllConstants.TWO == delType){
            //用户id删除
            customerHistoriesMapper.deleteByCustomer(condition);
        } else{
            throw ExceptionHelper.prompt("删除方式不能为空");
        }
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerHistories vo) {
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
