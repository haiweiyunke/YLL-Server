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
import yll.common.security.app.AppSecuritysUtil;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.CustomerPoints;
import yll.entity.CustomerPointsDetails;
import yll.entity.Dic;
import yll.mapper.CustomerPointsDetailsMapper;
import yll.mapper.CustomerPointsMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerPointsDetailsVo;

import java.math.BigDecimal;
import java.util.List;

/**
 *  积分明细
 */
@Transactional
@Service
public class CustomerPointsDetailsService {

    // ==============================Fields===========================================
    @Autowired
    private CustomerPointsDetailsMapper customerPointsDetailsMapper;
    @Autowired
    private CustomerPointsMapper customerPointsMapper;
    @Autowired
    private CustomerPointsService customerPointsService;
    @Autowired
    private DicService dicService;
    @Autowired
    private AppSecuritys securitys;
    @Autowired
    private Securitys adminSecuritys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(CustomerPointsDetails vo) {

        validate(vo);

        AppPrincipal principal = securitys.getAppPrincipal();

        CustomerPointsDetails entity = new CustomerPointsDetails();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(principal.getCustomerId());
        entity.setPoint(vo.getPoint());
        entity.setType(vo.getType());
        entity.setSigns(vo.getSigns());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        customerPointsDetailsMapper.insert(entity);

        //更改用户总积分
        changePoint(principal.getCustomerId(), entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        customerPointsDetailsMapper.deleteById(id);
//        voRoleMapper.deleteByCustomerPointsDetailsId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(CustomerPointsDetails vo) {
        validate(vo);

        Principal principal = adminSecuritys.getPrincipal();

        CustomerPointsDetails entity = customerPointsDetailsMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setPoint(vo.getPoint());
        entity.setType(vo.getType());
        entity.setSigns(vo.getSigns());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        customerPointsDetailsMapper.update(entity);

        //更改用户总积分
        changePoint(vo.getTargetId(), entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        AppPrincipal principal = securitys.getAppPrincipal();
        CustomerPointsDetails entity = new CustomerPointsDetails();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        customerPointsDetailsMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public CustomerPointsDetails getById(String id) {
        CustomerPointsDetails entity = customerPointsDetailsMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerPointsDetails> pagedQuery(Pagination pagination, CustomerPointsDetails condition) {
        return MybatisHelper.selectPage(pagination, () -> customerPointsDetailsMapper.findBy(condition));
    }

  /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerPointsDetails> pagedQueryWithName(Pagination pagination, CustomerPointsDetails condition) {
        return MybatisHelper.selectPage(pagination, () -> customerPointsDetailsMapper.findByWithName(condition));
    }

    /**
     * 分页查询
     * @param condition 查询条件
     * @return 分页结果
     */
    public Integer findSum(CustomerPointsDetails condition) {
        return customerPointsDetailsMapper.findSum(condition);
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<CustomerPointsDetailsVo> getAppList(Pagination pagination, CustomerPointsDetailsVo condition) {
        return MybatisHelper.selectPage(pagination, () -> customerPointsDetailsMapper.getAppList(condition));
    }

    /**
     * 完成次数
     * @param condition 查询条件
     * @return
     */
    public CustomerPointsDetailsVo getCompletions(CustomerPointsDetailsVo condition) {
        return customerPointsDetailsMapper.getCompletions(condition);
    }

    /**
     * 按比例转换成积分
     * @param num
     */
    public CustomerPointsDetails convertToPoint(Integer num, String type) {
        //活动积分转换
        Dic dicActivity = dicService.getByCode(type);
        Dic dicRate = dicService.getByCode(YllConstants.INTEGRAL_QUESTIONS_RATE);
        //换算积分比率
        Double integralRate = Double.parseDouble(dicRate.getCodename());
        Integer points = Integer.parseInt(new BigDecimal(num * integralRate).setScale(0, BigDecimal.ROUND_DOWN) + "" );
        //封装
        CustomerPointsDetails pointsDetails = new CustomerPointsDetails();
        pointsDetails.setType(YllConstants.INTEGRAL_ACTIVITY);
        pointsDetails.setSigns(YllConstants.ONE);
        pointsDetails.setPoint(points);
        pointsDetails.setRemark(dicActivity.getRemark().concat(points.toString()));
        pointsDetails.setTargetId(AppSecuritysUtil.getCustomerId());

        insert(pointsDetails);
        return pointsDetails;
    }

    // ==============================ToolMethods======================================
    /** 更改用户积分 */
    private void changePoint(String customerId, CustomerPointsDetails entity) {
        if(StringUtils.isBlank(customerId)){
            throw ExceptionHelper.prompt("缺少用户唯一标识");
        }
        CustomerPoints cp = new CustomerPoints();
        cp.setTargetId(customerId);
        List<CustomerPoints> list = customerPointsMapper.findBy(cp);
        if(null == list || list.size() == 0){
            cp.setPoint(YllConstants.ZERO);
            customerPointsService.insert(cp);
        } else{
            cp = list.get(0);
            customerPointsService.calculationPoint(cp, entity);
        }
    }

    /** 验证数据 */
    private void validate(CustomerPointsDetails vo) {
        String id = vo.getId();
        String type = vo.getType();
        Integer signs = vo.getSigns();
        String targetId = vo.getTargetId();
        Integer point = vo.getPoint();

        if (StringUtils.isEmpty(targetId)) {
            throw ExceptionHelper.prompt("归属用户不能为空");
        }
        if (StringUtils.isEmpty(type)) {
            throw ExceptionHelper.prompt("积分类型不能为空");
        }
        if (signs == null) {
            throw ExceptionHelper.prompt("加减不能为空");
        }
        if (point == null) {
            throw ExceptionHelper.prompt("积分不能为空");
        }
    }

}
