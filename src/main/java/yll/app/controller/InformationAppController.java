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
import yll.component.util.CommonUtil;
import yll.service.CommonService;
import yll.service.InformationService;
import yll.service.model.YllConstants;
import yll.service.model.vo.InformationVo;

import java.io.UnsupportedEncodingException;

/**
 * APP资讯
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/information")
public class InformationAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommonService commonService;
    @Autowired
    private InformationService informationService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/information/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, InformationVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InformationVo());
        condition.setEnabled(YllConstants.ONE);
        Page<InformationVo> page = informationService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/information/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //封装
        String customerId = AppSecuritysUtil.getCustomerId();
        InformationVo entity = new InformationVo();
        entity.setId(id);
        entity.setCustomerId(customerId);
        //获取详情
        entity = informationService.getAppDetail(entity);
        if (entity != null) {
            //历史记录
            if(StringUtils.isNotBlank(customerId)){
                commonService.history(id, entity.getType());
            }
            try {
                //分享地址处理
                String url = CommonUtil.shareUrl(entity.getId(), "information");
                entity.setShareUrl(url);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return Result.error("分享地址处理失败");
            }
        } else{
            return Result.error("未找到对应数据");
        }
        return Result.ok(entity);
    }

    /**
     * [POST] /app/information/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(Pagination pagination, InformationVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InformationVo());
        pagination.setOffset(0);
        pagination.setLimit(4);
        condition.setSlide(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        Page<InformationVo> page = informationService.getAppList(pagination, condition);
        return Result.ok(page.getRecords());
    }

}
