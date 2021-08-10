package yll.app.controller;

import com.github.relucent.base.util.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.CustomerAuthentications;
import yll.service.CustomerAuthenticationsService;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerAuthenticationsVo;

/**
 * APP积分
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/auth")
public class AuthenticationsAppController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerAuthenticationsService customerAuthenticationsService;

    // ==============================Methods==========================================
    /**
     * [GET] /rest/auth/get <br>
     * 查询数据
     */
    @GetMapping(value = "/get")
    public Result<?> get() {
        String customerId = AppSecuritysUtil.getCustomerId();
        CustomerAuthenticationsVo condition = new CustomerAuthenticationsVo();
        condition.setTargetId(customerId);
        CustomerAuthentications entity = customerAuthenticationsService.getAppDetail(condition);
        return Result.ok(entity);
    }

    /**
         * [POST] /app/auth/save <br>
     *  新增
     */
    @PostMapping(value = "/save")
    public Result<?> save(CustomerAuthenticationsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setTargetId(customerId);
        CustomerAuthentications entity = customerAuthenticationsService.getAppDetail(condition);
        if (null == entity) {
            customerAuthenticationsService.insert(condition);
        } else {
            if(!YllConstants.ZERO.equals(entity.getState())){
               return Result.error("您已提交过认证，请勿重复提交");
            }
            //修改提交审核
            entity.setState(YllConstants.ONE);
            entity.setTargetId(customerId);
            entity.setLicences(condition.getLicences());
            entity.setLicencesImg(condition.getLicencesImg());
            customerAuthenticationsService.update(entity);
        }
        return Result.ok();
    }

}
