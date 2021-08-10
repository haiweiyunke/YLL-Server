package yll.app.controller;

import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.Customer;
import yll.entity.Enterprise;
import yll.entity.Mcn;
import yll.service.CustomerService;
import yll.service.EnterpriseService;
import yll.service.model.vo.EnterpriseVo;

/**
 *  企业主
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/enterprise")
public class EnterpriseAppController {

    // ==============================Fields===========================================
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/enterprise/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> list(Enterprise condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Enterprise());
//        List<Enterprise> list = enterpriseService.getAppList(condition);
        return Result.ok(null);
    }


    /**
     * [POST] /app/enterprise/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(Enterprise condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer customer = customerService.getById(customerId);
        String roleType = customer.getRoleType();
        Enterprise vo = new Enterprise();
        vo.setCreator(customerId);
        vo = enterpriseService.getByCondition(vo);
        if(StringUtils.isBlank(roleType) || "1".equals(roleType)){
            //当用户角色为普通用户或未指定时，可进行认证申请
            if (null == vo || StringUtils.isBlank(vo.getId())) {
                condition.setCreator(customerId);
                condition.setState(1);  //未审核
                enterpriseService.insert(condition);
            } else {
                condition.setId(vo.getId());
//                condition.setState(1);  //未审核
                enterpriseService.update(condition);
            }
        } else if("4".equals(roleType)){
            //修改
            condition.setId(vo.getId());
//            condition.setState(1);  //未审核
            enterpriseService.update(condition);
        } else{
            return Result.error("您已完成相关认证");
        }
        return Result.ok();
    }

    /**
     * [GET] /app/enterprise/auth <br>
     * 达人认证回显
     */
    @GetMapping(value = "/auth")
    public Result<?> auth() {
        String customerId = AppSecuritysUtil.getCustomerId();
//        String customerId = "2020040312000216800001";
        EnterpriseVo result = new EnterpriseVo();
        result.setCreator(customerId);
        result = enterpriseService.getAppAuth(result);
        if(result == null){
            return Result.error("暂无数据");
        }
        return Result.ok(result);
    }
}
