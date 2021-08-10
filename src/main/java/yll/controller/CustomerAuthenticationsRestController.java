package yll.controller;

import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.CustomerAuthentications;
import yll.service.CustomerAuthenticationsService;
import yll.service.model.vo.CustomerAuthenticationsVo;

/**
 * 企业认证管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/auth")
public class CustomerAuthenticationsRestController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerAuthenticationsService customerAuthenticationsService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/auth/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody CustomerAuthentications entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            throw ExceptionHelper.prompt("缺少参数id");
        } else {
            customerAuthenticationsService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [DELETE] /rest/auth/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        customerAuthenticationsService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/auth/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        CustomerAuthentications entity = customerAuthenticationsService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/auth/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, CustomerAuthenticationsVo condition) {
        condition = ObjectUtils.defaultIfNull(condition,  new CustomerAuthenticationsVo());
        Page<CustomerAuthentications> page = customerAuthenticationsService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

}
