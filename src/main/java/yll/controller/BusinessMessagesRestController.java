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
import yll.entity.BusinessMessages;
import yll.service.BusinessMessagesService;
import yll.service.model.YllConstants;
import yll.service.model.vo.BusinessMessagesVo;

/**
 * 商务洽谈管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/business")
public class BusinessMessagesRestController {

    // ==============================Fields===========================================
    @Autowired
    private BusinessMessagesService businessMessagesService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/business/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody BusinessMessages entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            businessMessagesService.insert(entity);
        } else {
            businessMessagesService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/business/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        businessMessagesService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/business/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        businessMessagesService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/business/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        BusinessMessages entity = businessMessagesService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [POST] /rest/business/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, BusinessMessagesVo condition){
        condition = ObjectUtils.defaultIfNull(condition, new BusinessMessagesVo());
        condition.setEnabled(YllConstants.ONE);
        Page<BusinessMessagesVo> page = businessMessagesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

}
