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
import yll.entity.CustomerLikes;
import yll.mapper.CustomerLikesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerLikesVo;

import java.util.List;

/**
 *  我的点赞
 */
@Transactional
@Service
public class CustomerLikesService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerLikesMapper customerLikesMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 操作
     * @param
     */
    public void operation(CustomerLikes vo) {
        String customerId = AppSecuritysUtil.getCustomerId();
        vo.setCustomerId(customerId);
        List<CustomerLikes> result = customerLikesMapper.findById(vo);
        if(null != result && result.size() > 0){
            for (CustomerLikes cc : result) {
                customerLikesMapper.deleteById(cc.getId());
            }
            //计数
            commonService.count(vo.getTargetId(), vo.getDicId(), YllConstants.YLL_LIKES, YllConstants.SUBTRACT);
        } else{
            insert(vo);
            //计数
            commonService.count(vo.getTargetId(), vo.getDicId(), YllConstants.YLL_LIKES, YllConstants.ADD);
        }
    }

    /**
     * 连续点赞，无取消
     * @param
     */
    public void operationAdd(CustomerLikes vo) {
        insert(vo);
        //计数
        commonService.count(vo.getTargetId(), vo.getDicId(), YllConstants.YLL_LIKES, YllConstants.ADD);
    }

    /**
     * 新增
     * @param
     */
    public void insert(CustomerLikes vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerLikes entity = new CustomerLikes();
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
        customerLikesMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerLikesMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerLikesId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerLikes vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerLikes entity = customerLikesMapper.getById(vo.getId());

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
        customerLikesMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerLikes entity = new CustomerLikes();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerLikesMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerLikes getById(String id) {
        CustomerLikes entity = customerLikesMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerLikes> pagedQuery(Pagination pagination, CustomerLikes condition) {
        return MybatisHelper.selectPage(pagination, () -> customerLikesMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerLikes> pagedQueryWithType(Pagination pagination, CustomerLikes condition) {
        return MybatisHelper.selectPage(pagination, () -> customerLikesMapper.findByWithType(condition));
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerLikesVo> pagedQueryWithJoin(Pagination pagination, CustomerLikesVo condition) {
        AppPrincipal principal = securitys.getAppPrincipal();
        condition.setCustomerId(principal.getCustomerId());
        return MybatisHelper.selectPage(pagination, () -> customerLikesMapper.findByWithJoin(condition));
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerLikes vo) {
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
