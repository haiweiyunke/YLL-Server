package yll.controller;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;

import yll.entity.OpLog;
import yll.service.OpLogService;

/**
 * 操作日志
 */
@RestController
@RequestMapping(value = "/rest/op-log")
public class OpLogRestController {

    // ==============================Fields===========================================
    @Autowired
    private OpLogService opLogService;

    // ==============================Methods==========================================

    /**
     * [GET] /rest/op-log/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, OpLog condition) {
        condition = ObjectUtils.defaultIfNull(condition, new OpLog());
        Page<OpLog> page = opLogService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }
}
