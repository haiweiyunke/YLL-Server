package yll.app.controller;

import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yll.service.SlideService;
import yll.service.model.vo.SlideVo;

import java.util.List;

/**
 *  轮播
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/slide")
public class SlideAppController {

    // ==============================Fields===========================================
    @Autowired
    private SlideService slideService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/slide/list <br>
     * 查询数据详情
     */
    @PostMapping(value = "/list")
    public Result<?> list(SlideVo condition) {
        List<SlideVo> list = slideService.getAppList(condition);
        return Result.ok(list);
    }

}
