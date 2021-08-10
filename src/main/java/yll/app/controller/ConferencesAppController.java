package yll.app.controller;

import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.service.CommonService;
import yll.service.ConferencesService;
import yll.service.model.YllConstants;
import yll.service.model.vo.ConferencesVo;


/**
 * APP发布会
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/conferences")
public class ConferencesAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommonService commonService;
    @Autowired
    private ConferencesService conferencesService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/conferences/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ConferencesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ConferencesVo());
        condition.setEnabled(YllConstants.ONE);
        condition.setState(YllConstants.TWO);   //2-已审核
        condition.setVisible(YllConstants.ONE);   //1-可见
        Page<ConferencesVo> page = conferencesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/conferences/myself/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/myself/list")
    public Result<?> myselfList(Pagination pagination, ConferencesVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition = ObjectUtils.defaultIfNull(condition, new ConferencesVo());
        condition.setEnabled(YllConstants.ONE);
        condition.setCreator(customerId);
        Page<ConferencesVo> page = conferencesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/conferences/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //封装
        ConferencesVo entity = new ConferencesVo();
        entity.setId(id);
        //获取详情
        entity = conferencesService.getAppDetail(entity);
        return Result.ok(entity);
    }

    /**
     * [POST] /app/conferences/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(Pagination pagination, ConferencesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ConferencesVo());
        pagination.setOffset(0);
        pagination.setLimit(4);
        condition.setSlide(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        Page<ConferencesVo> page = conferencesService.getAppList(pagination, condition);
        return Result.ok(page.getRecords());
    }

    /**
     * [POST] /app/conferences/extension/list <br>
     * 查询推广数据
     */
    @PostMapping(value = "/extension/list")
    public Result<?> extensionList(Pagination pagination, ConferencesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ConferencesVo());
        pagination.setOffset(0);
        pagination.setLimit(4);
        condition.setExtension(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        Page<ConferencesVo> page = conferencesService.getAppList(pagination, condition);
        return Result.ok(page.getRecords());
    }


    /**
     * [POST] /app/conferences/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(ConferencesVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            condition.setCreator(customerId);
            condition.setCreatedTime(DateUtil.now());
            conferencesService.insert(condition);
        } else {
            condition.setModifier(customerId);
            condition.setModifiedTime(DateUtil.now());
            conferencesService.update(condition);
        }
        return Result.ok();
    }

}
