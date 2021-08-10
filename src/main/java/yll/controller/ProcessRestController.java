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
import yll.entity.Process;
import yll.service.ProcessService;
import yll.service.model.vo.ProcessVo;

import java.util.List;


/**
 * 流程管理     TODO 测试后台编辑
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/process")
public class ProcessRestController {

    // ==============================Fields===========================================
    @Autowired
    private ProcessService processService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/process/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Process entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            processService.insert(entity);
        } else {
            processService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/process/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        processService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/process/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        processService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/process/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Process entity = processService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/process/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Process condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Process());
        Page<Process> page = processService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/process/all" <br>
     * 查询数据列表
     */
    @PostMapping(value = "/all")
    public Result<?> all(@RequestBody ProcessVo condition) {
        List<Process> list = processService.findBy(condition);
        return Result.ok(list);
    }

}
