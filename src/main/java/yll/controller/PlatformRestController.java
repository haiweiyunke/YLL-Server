package yll.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.Platform;
import yll.service.PlatformService;
import yll.service.model.vo.PlatformVo;


/**
 * 达人平台信息
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/platform")
public class PlatformRestController {

    // ==============================Fields===========================================
    @Autowired
    private PlatformService platformService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/platform/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Platform entity) {
         String id = entity.getId();
         if (StringUtils.isBlank(id)) {
             //平台是否存在
             Platform condition = new Platform();
             condition.setCreator(entity.getCreator());
             condition.setPlatformType(entity.getPlatformType());
             condition = platformService.getByCondition(condition);
             if(null != condition){
                 return Result.error("该平台已存在");
             }
            platformService.insert(entity);
        } else {
            platformService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/platform/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        platformService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/platform/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Platform entity = platformService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/platform/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, PlatformVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new PlatformVo());
        Page<PlatformVo> page = platformService.pagedQueryWithDic(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/platform/creator/{id} <br>
     * 获取数据(新增|更新)
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String id) {
        Platform condition = new Platform();
        condition.setId(id);
        Platform entity = platformService.getByCondition(condition);
        return Result.ok(entity);
    }

}
