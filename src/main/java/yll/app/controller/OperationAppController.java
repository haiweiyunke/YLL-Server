package yll.app.controller;

import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.ObjectUtils;
import yll.component.util.RelativeNumberFormatTool;
import yll.entity.*;
import yll.service.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * APP用户操作
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/operation")
public class OperationAppController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerCollectsService customerCollectsService;

     @Autowired
    private CustomerLikesService customerLikesService;

    @Autowired
    private CustomerHistoriesService customerHistoriesService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private CustomerMessagesService customerMessagesService;

    @Autowired
    private CustomerFeedbackService customerFeedbackService;

    @Autowired
    private ActivitiesService activitiesService;

    @Autowired
    private PlatformService platformService;

    @Autowired
    private SearchService searchService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/operation/like <br>
     * 点赞
     */
    @PostMapping(value = "/like")
    public Result<?> like(CustomerLikes condition) {
        //连续点赞
        customerLikesService.operationAdd(condition);
        return Result.ok();
    }

    /**
     * [POST] /app/operation/like/list <br>
     * 点赞列表
     * pagination  :start 起始页（0开始）， :limit每页条数
     */
    @PostMapping(value = "/like/list")
    public Result<?> likeList(Pagination pagination, CustomerLikesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerLikesVo());
        Page<CustomerLikesVo> page = customerLikesService.pagedQueryWithJoin(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/operation/like/get <br>
     * 点赞详情
     */
    @PostMapping(value = "/like/get")
    public Result<?> getLike(CustomerLikes condition) {
        Object entity = getData(condition.getTargetId(), condition.getType());
        return Result.ok(entity);
    }

    /**
     * [POST] /app/operation/collect <br>
     * 关注
     */
    @PostMapping(value = "/collect")
    public Result<?> collect(CustomerCollects condition) {
        customerCollectsService.operation(condition);
        return Result.ok();
    }

    /**
     * [POST] /app/operation/collect/list <br>
     * 关注列表
     * 同时返回平台信息
     */
    @PostMapping(value = "/collect/list")
    public Result<?> collectList(Pagination pagination, CustomerCollectsVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerCollectsVo());
        Page<CustomerCollectsVo> page = customerCollectsService.findByWithJoinXG(pagination, condition);
        //处理达人平台信息
        List<CustomerCollectsVo> records = page.getRecords();
        for (CustomerCollectsVo cc :
                records) {
            String platformCode = cc.getPlatformCode();
            List<PlatformVo> platformList = new ArrayList<>();
            if(StringUtils.isNotBlank(platformCode)){
                String[] pcodeArray = platformCode.split(",");
                for (String code:
                pcodeArray) {
                    PlatformVo pv = new PlatformVo();
                    pv.setCreator(cc.getTid());    //用customerId查询
                    pv.setPlatformType(code);
                    PlatformVo presult = platformService.getAppDetail(pv);
                    //数字格式转换
                    String fansStr = RelativeNumberFormatTool.relativeNumberFormat(presult.getFans(), RelativeNumberFormatTool.PY);
                    presult.setFansStr(fansStr);
                    platformList.add(presult);
                }
            }
            cc.setPlatformList(platformList);
            //数字格式转换
            String fans = RelativeNumberFormatTool.relativeNumberFormat(cc.getFans(), RelativeNumberFormatTool.PY);
            cc.setFans(fans);
            String follows = RelativeNumberFormatTool.relativeNumberFormat(cc.getFollows(), RelativeNumberFormatTool.PY);
            cc.setFollows(follows);
            String likes = RelativeNumberFormatTool.relativeNumberFormat(cc.getLikes(), RelativeNumberFormatTool.PY);
            cc.setLikes(likes);
        }
        return PageResult.of(page);
    }

    /**
     * [POST] /app/operation/collect/get <br>
     * 关注详情
     */
    @PostMapping(value = "/collect/get")
    public Result<?> getCollect(CustomerCollects condition) {
        Object entity = getData(condition.getTargetId(), condition.getType());
        return Result.ok(entity);
    }

    /**
     * [POST] /app/operation/collect/cancel <br>
     * 关注-取消
     */
    @PostMapping(value = "/collect/cancel")
    public Result<?> cancelCollect(CustomerCollectsVo condition) {
        if(YllConstants.ONE != condition.getDelType() && YllConstants.TWO != condition.getDelType()){
            return Result.error("缺少正确删除方式");
        } else if(YllConstants.ONE == condition.getDelType() && StringUtils.isBlank(condition.getIds())){
            return Result.error("请选择要删除的数据");
        }
        customerCollectsService.delete(condition);
        return Result.ok();
    }

    /**
     * [POST] /app/operation/history/list <br>
     *  历史记录
     */
    @PostMapping(value = "/history/list")
    public Result<?> historyList(Pagination pagination, CustomerHistoriesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerHistoriesVo());
        Page<CustomerHistoriesVo> page = customerHistoriesService.pagedQueryWithJoin(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/operation/feedback <br>
     * 新增意见反馈
     */
    @PostMapping(value = "/feedback")
    public Result<?> feedback(CustomerFeedback condition) {
        customerFeedbackService.insert(condition);
        return Result.ok();
    }

    /**
     * [POST] /app/operation/message/list <br>
     * 消息列表
     */
    @PostMapping(value = "/message/list")
    public Result<?> messageList(Pagination pagination, CustomerMessagesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerMessagesVo());
        Page<CustomerMessagesVo> page = customerMessagesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/operation/message/{id} <br>
     * 消息详情
     */
    @GetMapping(value = "/message/{id}")
    public Result<?> getMessage(@PathVariable("id") String id) {
        CustomerMessagesVo entity = new CustomerMessagesVo();
        entity.setId(id);
        entity = customerMessagesService.getAppDetail(entity);
        return Result.ok(entity);
    }


    /**
     * [GET] /app/operation/search/tab <br>
     * 统一查询tab
     */
    @GetMapping(value = "/search/tab")
    public Result<?> searchTab() {
        List<String> list = new ArrayList<>();
        list.add("information");
        list.add("study");
        list.add("activities");
        list.add("zone");
        return Result.ok(list);
    }

    /**
     * [POST] /app/operation/search/list <br>
     * 统一查询入口
     */
    @PostMapping(value = "/search/list")
    public Result<?> searchList(Pagination pagination, String keyword, String type) {
        if(YllConstants.TABLE_CUSTOMER.equals(type)){
            //资讯
            InformationVo condition = new InformationVo();
            condition.setName(keyword);
            Page<InformationVo> result = informationService.getAppList(pagination, condition);
            return PageResult.of(result);
        } else if("study".equals(type)){
            //学习
            SearchVo condition = new SearchVo();
            condition.setName(keyword);
            Page<SearchVo> result = searchService.getStudyAppList(pagination, condition);
            return PageResult.of(result);
        } else if("zone".equals(type)){
            //专区
            SearchVo condition = new SearchVo();
            condition.setName(keyword);
            Page<SearchVo> result = searchService.getZoneAppList(pagination, condition);
            return PageResult.of(result);
        } else if("activities".equals(type)){
            //活动
            ActivitiesVo condition = new ActivitiesVo();
            condition.setName(keyword);
            Page<ActivitiesVo> result = activitiesService.getAppList(pagination, condition);
            return PageResult.of(result);
        } else{
            throw ExceptionHelper.prompt("参数type错误");
        }
    }




    // ==============================ToolMethods======================================
    /** 获取（点赞、关注）详情 */
    private Object getData(String targetId, String type) {
        Object entity = new Object();
        if(YllConstants.TABLE_CUSTOMER.equals(type)){
            entity = informationService.getById(targetId);
        } else{
            throw ExceptionHelper.prompt("参数错误");
        }
        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在");
        }
        return entity;
    }

}
