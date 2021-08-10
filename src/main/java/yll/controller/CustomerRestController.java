package yll.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yll.entity.Customer;
import yll.service.CustomerService;
import yll.service.ExcelService;
import yll.service.model.vo.CustomerVo;

import java.io.IOException;
import java.util.List;

/**
 * 用户管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/customer")
public class CustomerRestController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ExcelService excelService ;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/customer/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Customer entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            entity.setPassword("111111");
            customerService.insert(entity);
        } else {
            customerService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/customer/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        customerService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/customer/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Customer entity = customerService.getById(id);
        return Result.ok(entity);
    }


    /**
     * [GET] /rest/customer/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, CustomerVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerVo());
        Page<CustomerVo> page = customerService.pagedQueryWithPointsVo(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/customer/reset/{id} <br>
     * 重置密码
     */
    @GetMapping(value = "/reset/{id}")
    public Result<?> reset(@PathVariable("id") String id) {
        customerService.reset(id);
        return Result.ok();
    }

    /**
     * [POST] /rest/customer/import <br>
     * excel导入数据(新增|更新)
     */
    @PostMapping(value = "/import")
    public Result<?> importExcel(MultipartFile file) {
        try {
            excelService.excelRead(file);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("操作失败");
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/customer/all <br>
     * 查询数据列表
     */
    @PostMapping(value = "/all")
    public Result<?> all(@RequestBody Customer condition) {
        List<Customer> list = customerService.findBy(condition);
        return Result.ok(list);
    }

}
