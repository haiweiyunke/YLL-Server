package yll.app.controller;

import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.service.AreaService;
import yll.entity.Area;

import java.util.List;

/**
 * 行政区划
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/area")
public class AreaAppController {

    // ==============================Fields===========================================
    @Autowired
    private AreaService areaService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/area/list1 <br>
     * 查询数据列表-无层级版
     */
    @PostMapping(value = "/list1")
    public Result<?> list1(Area condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Area());
        List<Area> list = areaService.getAppList(condition);
        return Result.ok(list);
    }

    /**
     * [POST] /app/area/list <br>
     * 查询数据列表-层级版
     */
    @PostMapping(value = "/list")
    public Result<?> list(Area condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Area());
        List<Area> list = areaService.getAppList2(condition);
        return Result.ok(list);
    }

}
