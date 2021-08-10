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
import yll.entity.BusinessMessages;
import yll.service.BusinessMessagesService;
import yll.service.model.vo.BusinessMessagesVo;

/**
 * 商业洽谈
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/business")
public class BusinessMessagesAppController {

    // ==============================Fields===========================================
    @Autowired
    private BusinessMessagesService businessMessagesService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/business/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(BusinessMessages condition) {
        String content = condition.getContent();
        String cid = condition.getCid();
        if(StringUtils.isBlank(content) || StringUtils.isBlank(cid)){
            return Result.error("缺少参数");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setNegotiator(customerId);
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            businessMessagesService.insert(condition);
        } else {
            businessMessagesService.update(condition);
        }
        return Result.ok();
    }


    /**
     * [POST] /app/business/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, BusinessMessagesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new BusinessMessagesVo());
        Page<BusinessMessagesVo> page = businessMessagesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/business/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        BusinessMessagesVo condition = new BusinessMessagesVo();
        condition.setId(id);
        BusinessMessagesVo result = businessMessagesService.getAppDetail(condition);
        return Result.ok(result);
    }

}
