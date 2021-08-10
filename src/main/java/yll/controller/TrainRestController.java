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
import yll.entity.Train;
import yll.service.TrainService;
import yll.service.model.YllConstants;
import yll.service.model.vo.TrainVo;

import java.util.List;

/**
 * 培训管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/train")
public class TrainRestController {

    // ==============================Fields===========================================
    @Autowired
    private TrainService trainService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/train/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Train entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            trainService.insert(entity);
        } else {
            trainService.update(entity);
        }
        return Result.ok();
    }

    /**
     * [POST] /rest/train/enable <br>
     * [BODY] {id:?,enabled:?}<br>
     * 启用禁用
     */
    @PostMapping(value = "/enable")
    public Result<?> enable(@RequestBody Mapx params) {
        String id = params.getString("id");
        Integer enabled = params.getInteger("enabled");
        trainService.enable(id, enabled);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/train/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        trainService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/train/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Train entity = trainService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/train/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Train condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Train());
        Page<Train> page = trainService.pagedQueryWithType(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /rest/train/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(@RequestBody TrainVo entity) {
        List<Train> list = trainService.findBySlide(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/train/slide/save <br>
     * 保存数据-轮播(新增|更新)
     */
    @PostMapping(value = "/slide/save")
    public Result<?> slideSave(@RequestBody TrainVo entity) {
        trainService.slide(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/train/slide/{id} <br>
     * 根据编号删除-轮播
     */
    @DeleteMapping(value = "/slide/{id}")
    public Result<?> slideDelete(@PathVariable("id") String id) {
        Train entity = trainService.getById(id);
        entity.setSlide(YllConstants.ZERO);
        entity.setOrdinal(null);
        trainService.update(entity);
        return Result.ok();
    }

}
