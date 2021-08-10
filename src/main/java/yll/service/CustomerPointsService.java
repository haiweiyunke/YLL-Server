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
import yll.common.security.app.AppSecuritysUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.Customer;
import yll.entity.CustomerPoints;
import yll.entity.CustomerPointsDetails;
import yll.mapper.CustomerPointsMapper;
import yll.service.model.YllConstants;

import java.util.List;

/**
 *  积分
 */
@Transactional
@Service
public class CustomerPointsService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerPointsMapper customerPointsMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增-外部调用
     * @param
     */
    public void init(Customer entity) {
        String customerId = entity.getId();
        CustomerPoints vo = new CustomerPoints();
        vo.setTargetId(customerId);
        vo.setPoint(YllConstants.ZERO);
        insert(vo);
    }

    /**
     * 新增
     * @param
     */
    public void insert(CustomerPoints vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        CustomerPoints entity = new CustomerPoints();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getTargetId());
        entity.setPoint(vo.getPoint());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setCreated(entity, principal);
        customerPointsMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerPointsMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerPointsId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerPoints vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        CustomerPoints entity = customerPointsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setPoint(vo.getPoint());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerPointsMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        CustomerPoints entity = new CustomerPoints();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerPointsMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerPoints getById(String id) {
        CustomerPoints entity = customerPointsMapper.getById(id);
        return entity;
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerPoints getByTargetId(CustomerPoints condition) {
        List<CustomerPoints> list = customerPointsMapper.findBy(condition);
        if(null == list || list.size() == 0){
            String customerId = AppSecuritysUtil.getCustomerId();
            CustomerPoints entity = new CustomerPoints();
            entity.setTargetId(customerId);
            entity.setPoint(YllConstants.ZERO);
            this.insert(entity);
            return entity;
        }
        return list.get(0);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerPoints> pagedQuery(Pagination pagination, CustomerPoints condition) {
        return MybatisHelper.selectPage(pagination, () -> customerPointsMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerPoints> pagedQueryWithName(Pagination pagination, CustomerPoints condition) {
        return MybatisHelper.selectPage(pagination, () -> customerPointsMapper.findByWithName(condition));
    }

    /**
     * 计算总积分
     */
    public void calculationPoint(CustomerPoints cp, CustomerPointsDetails cpd){
        Integer point = cp.getPoint();
        if(YllConstants.ZERO.equals(cpd.getSigns())){
            //0-减，1-加
            point = point - cpd.getPoint();
        } else {
            point = point + cpd.getPoint();
        }
        cp.setPoint(point);
        customerPointsMapper.update(cp);
    }

    /**
     * 查询(App使用)
     * @param
     * @return 实体
     */
    public CustomerPoints getAppDetail(CustomerPoints condition) {
        condition = customerPointsMapper.getAppDetail(condition);
        return condition;
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerPoints vo) {
        String id = vo.getId();
        String targetId = vo.getTargetId();
        Integer point = vo.getPoint();

        if (StringUtils.isEmpty(targetId)) {
            throw ExceptionHelper.prompt("归属用户不能为空");
        }
        if (point == null) {
            throw ExceptionHelper.prompt("积分不能为空");
        }
    }

}
