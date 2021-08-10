package yll.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.CustomerFeedback;
import yll.service.CustomerFeedbackService;
import yll.service.model.vo.CustomerFeedbackVo;

/**
 * 意见反馈管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/feedback")
public class CustomerFeedbackRestController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerFeedbackService customerFeedbackService;

    // ==============================Methods==========================================
    /**
     * [DELETE] /rest/feedback/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        customerFeedbackService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/feedback/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        CustomerFeedback entity = customerFeedbackService.getDetailByCustomer(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/feedback/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, CustomerFeedbackVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerFeedbackVo());
        Page<CustomerFeedbackVo> page = customerFeedbackService.findByCustomer(pagination, condition);
        return PageResult.of(page);
    }

}
