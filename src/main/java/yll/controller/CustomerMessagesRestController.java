package yll.controller;

import com.github.relucent.base.util.collect.Mapx;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.CustomerMessages;
import yll.service.CustomerMessagesService;
import yll.service.model.YllConstants;
import yll.service.model.vo.CustomerMessagesVo;

/**
 * 我的消息管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/messages")
public class CustomerMessagesRestController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerMessagesService customerMessagesService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/messages/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody CustomerMessages entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            customerMessagesService.insert(entity);
        } else {
            customerMessagesService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/messages/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        customerMessagesService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/messages/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        customerMessagesService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/messages/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        CustomerMessages entity = customerMessagesService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/messages/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, CustomerMessages condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerMessages());
        condition.setEnabled(YllConstants.ONE);
        Page<CustomerMessages> page = customerMessagesService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

}
