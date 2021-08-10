package yll.app.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.CustomerAddresses;
import yll.service.CustomerAddressesService;
import yll.service.model.vo.CustomerAddressesVo;

/**
 * 用户地址
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/addresses")
public class CustomerAddressesAppController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerAddressesService customerAddressesService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/addresses/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(CustomerAddresses condition) {
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            customerAddressesService.insert(condition);
        } else {
            customerAddressesService.update(condition);
        }
        return Result.ok();
    }

    /**
     * [POST] /app/addresses/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, CustomerAddressesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerAddressesVo());
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setTargetId(customerId);
        Page<CustomerAddressesVo> page = customerAddressesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/addresses/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //封装
        String customerId = AppSecuritysUtil.getCustomerId();
        CustomerAddressesVo entity = new CustomerAddressesVo();
        entity.setId(id);
        entity.setTargetId(customerId);
        //获取详情
        entity = customerAddressesService.getAppDetail(entity);
        return Result.ok(entity);
    }

    /**
     * [POST] /app/addresses/cancel <br>
     * 根据编号删除
     */
    @PostMapping(value = "/cancel")
    public Result<?> delete(CustomerAddressesVo condition) {
        String id = condition.getId();
        if(StringUtils.isBlank(id)){
            return Result.error("id不能为空");
        }
        customerAddressesService.deleteById(id);
        return Result.ok();
    }

    /**
     * [POST] /app/addresses/default <br>
     * 地址默认
     */
    @PostMapping(value = "/default")
    public Result<?> defaultAddresses(CustomerAddresses condition) {
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            return Result.error("缺少id");
        } else {
            condition = customerAddressesService.getById(id);
            condition.setState(2);  //默认状态
            customerAddressesService.update(condition);
        }
        return Result.ok();
    }

}
