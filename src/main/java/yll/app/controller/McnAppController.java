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
import yll.entity.Mcn;
import yll.service.CustomerService;
import yll.service.McnService;

import java.util.List;

/**
 *  达人
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/mcn")
public class McnAppController {

    // ==============================Fields===========================================
    @Autowired
    private McnService mcnService;
    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/mcn/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> list(Mcn condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Mcn());
//        List<Mcn> list = mcnService.getAppList(condition);
        return Result.ok(null);
    }


    /**
     * [POST] /app/mcn/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(Mcn condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer customer = customerService.getById(customerId);
        String roleType = customer.getRoleType();
        Mcn vo = new Mcn();
        vo.setCreator(customerId);
        vo = mcnService.getByCondition(vo);
        if(StringUtils.isBlank(roleType) || "1".equals(roleType)){
            //当用户角色为普通用户或未指定时，可进行认证申请
            if (null == vo || StringUtils.isBlank(vo.getId())) {
                condition.setCreator(customerId);
                condition.setState(1);  //未审核
                mcnService.insert(condition);
            } else {
                condition.setId(vo.getId());
//                condition.setState(1);  //未审核
                mcnService.update(condition);
            }
        } else if("3".equals(roleType)){
            //修改
            condition.setId(vo.getId());
//            condition.setState(1);  //未审核
            mcnService.update(condition);
        } else{
            return Result.error("您已完成相关认证");
        }

        return Result.ok();
    }

    /**
     * [GET] /app/mcn/auth <br>
     * 达人认证回显
     */
    @GetMapping(value = "/auth")
    public Result<?> auth() {
        String customerId = AppSecuritysUtil.getCustomerId();
//        String customerId = "2020040311451580900001";
        Mcn mcn = new Mcn();
        mcn.setCreator(customerId);
        mcn = mcnService.getAppAuth(mcn);
        if(mcn == null){
            return Result.error("暂无数据");
        }
        return Result.ok(mcn);
    }

    /**
     * [GET] /app/mcn/all <br>
     * 查询数据列表
     */
    @GetMapping(value = "/all")
    public Result<?> all(Mcn condition) {
        condition.setState(2);
        condition = ObjectUtils.defaultIfNull(condition, new Mcn());
        List<Mcn> list = mcnService.findAll4App(condition);
        return Result.ok(list);
    }

}
