package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.model.Result;
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
import yll.entity.CustomerActivitiesAnswer;
import yll.entity.CustomerPointsDetails;
import yll.mapper.CustomerActivitiesAnswerMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerActivitiesAnswerVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 我的活动-知识竞赛结果
 */
@Transactional
@Service
public class CustomerActivitiesAnswerService {
    // ==============================Fields===========================================
    @Autowired
    private CustomerActivitiesAnswerMapper customerActivitiesAnswerMapper;
     @Autowired
    private CustomerPointsDetailsService customerPointsDetailsService;
    @Autowired
    private AppSecuritys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(CustomerActivitiesAnswer vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerActivitiesAnswer entity = new CustomerActivitiesAnswer();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getTargetId());
        entity.setTotal(vo.getTotal());
        entity.setCorrect(vo.getCorrect());
        entity.setRemark(vo.getRemark());

        //计算正确率
        String rate = new BigDecimal((double)vo.getCorrect()*100/vo.getTotal()).setScale(1,BigDecimal.ROUND_HALF_UP)+"%";
        entity.setRate(rate);

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        //积分处理
        if(entity.getTotal().equals(entity.getRemark())){
            CustomerPointsDetails pointsDetails = customerPointsDetailsService.convertToPoint(entity.getCorrect(), YllConstants.INTEGRAL_ACTIVITY);
            entity.setPoint(pointsDetails.getPoint());
        } else{
            entity.setPoint(YllConstants.ZERO);
        }
        customerActivitiesAnswerMapper.insert(entity);

    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("系统管理员不能被删除");
        }
        customerActivitiesAnswerMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerActivitiesAnswerId(id);   关联删除
    }

    /**
     * 条件查询
     * @param
     * @return 实体
     */
    public CustomerActivitiesAnswer getByCondition(CustomerActivitiesAnswer condition) {
        List<CustomerActivitiesAnswer> list = customerActivitiesAnswerMapper.findBy(condition);
        if (list != null && list.size() > 0) {
            CustomerActivitiesAnswer entity = list.get(0);
            return entity;
        }
        return null;
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(CustomerActivitiesAnswer vo) {
        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerActivitiesAnswer entity = customerActivitiesAnswerMapper.findBy(vo).get(0);

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTotal(vo.getTotal());
        entity.setCorrect(vo.getCorrect());
        entity.setRemark(vo.getRemark());

        //计算正确率
        String rate = new BigDecimal((double)vo.getCorrect()*100/vo.getTotal()).setScale(1,BigDecimal.ROUND_HALF_UP)+"%";
        entity.setRate(rate);

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);

        //积分处理(已答完题，并且之前积分未进行过更改)
        if(vo.getTotal().equals(vo.getRemark()) && YllConstants.ZERO.equals(entity.getPoint())){
            CustomerPointsDetails pointsDetails = customerPointsDetailsService.convertToPoint(entity.getCorrect(), YllConstants.INTEGRAL_ACTIVITY);
            entity.setPoint(pointsDetails.getPoint());
        }
        customerActivitiesAnswerMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerActivitiesAnswer entity = new CustomerActivitiesAnswer();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerActivitiesAnswerMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public CustomerActivitiesAnswer getById(String id) {
        CustomerActivitiesAnswer entity = customerActivitiesAnswerMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerActivitiesAnswer> pagedQuery(Pagination pagination, CustomerActivitiesAnswer condition) {
        return MybatisHelper.selectPage(pagination, () -> customerActivitiesAnswerMapper.findBy(condition));
    }

    /**
     * 分页查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public CustomerActivitiesAnswerVo getAppDetail(CustomerActivitiesAnswerVo condition) {
        return customerActivitiesAnswerMapper.getAppDetail(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(CustomerActivitiesAnswer vo) {
        String id = vo.getId();
        String targetId = vo.getTargetId();
        Integer total = vo.getTotal();
        Integer correct = vo.getCorrect();

        if (StringUtils.isEmpty(targetId)) {
            throw ExceptionHelper.prompt("所属活动不能为空");
        }
        if (null == total) {
            throw ExceptionHelper.prompt("总答题数不能为空");
        }
        if (null == correct) {
            throw ExceptionHelper.prompt("正确题数不能为空");
        }
    }
}
