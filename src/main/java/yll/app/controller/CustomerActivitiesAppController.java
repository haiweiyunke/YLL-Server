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
import yll.entity.Activities;
import yll.entity.CustomerActivities;
import yll.entity.CustomerActivitiesAnswer;
import yll.service.ActivitiesService;
import yll.service.CustomerActivitiesAnswerService;
import yll.service.CustomerActivitiesService;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesVo;
import yll.service.model.vo.CustomerActivitiesAnswerVo;
import yll.service.model.vo.CustomerActivitiesVo;

/**
 * 我的活动
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/customer-activities")
public class CustomerActivitiesAppController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerActivitiesService customerActivitiesService;
    @Autowired
    private CustomerActivitiesAnswerService customerActivitiesAnswerService;
    @Autowired
    private ActivitiesService activitiesService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/customer-activities/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(CustomerActivities condition, Integer total, Integer correct, Integer progress) {
        String activitiesId = condition.getActivitiesId();
        if (StringUtils.isBlank(activitiesId)) {
            return Result.error("活动id不能为空");
        }
        CustomerActivities vo = customerActivitiesService.getCustomerActivities(condition.getActivitiesId());
        Activities activities = activitiesService.getById(activitiesId);
        //校验
        Result<?> validate = validate(vo, activities, total, correct, progress);
        if(!validate.getCode().equals(YllConstants.ZERO)){
            return validate;
        }
        //我的活动
        if (null == vo) {
            vo = customerActivitiesService.insert(condition);
            Integer num = customerActivitiesService.getCountByCondition(condition);
            activities.setRealNum(num);
            activitiesService.update(activities);
        } else if(!YllConstants.ACTIVITIES_QUIZ.equals(activities.getType())){
            return Result.error("您已参与过此活动");
        }
        //竞赛活动答题结果
        if(YllConstants.ACTIVITIES_QUIZ.equals(activities.getType())){
            answer(total, correct, progress, vo);
        }
        return Result.ok();
    }

    /**
     * [POST] /app/customer-activities/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, CustomerActivitiesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CustomerActivitiesVo());
        condition.setEnabled(YllConstants.ONE);
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setTargetId(customerId);
        Page<ActivitiesVo> page = customerActivitiesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/customer-activities/answer/{id} <br>
     * 查询数据详情-活动竞赛结果
     * @param id “我的活动” 中间表id
     */
    @GetMapping(value = "/answer/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //活动竞赛
        CustomerActivitiesAnswerVo entity = new CustomerActivitiesAnswerVo();
        entity.setTargetId(id);
        //获取详情
        entity = customerActivitiesAnswerService.getAppDetail(entity);
        return Result.ok(entity);
    }

    // ==============================Methods======================================
    /**
     * 处理活动竞赛结果
     * @param total
     * @param correct
     * @param vo
     */
    private void answer(Integer total, Integer correct, Integer progress, CustomerActivities vo) {
        if (total != null && correct != null && progress != null) {
            //活动竞赛需存储竞赛结果
            CustomerActivitiesAnswer caa = new CustomerActivitiesAnswer();
            caa.setTargetId(vo.getId());
            caa = customerActivitiesAnswerService.getByCondition(caa);
            if(null == caa){
                caa = new CustomerActivitiesAnswer();
                caa.setCorrect(correct);
                caa.setTotal(total);
                caa.setTargetId(vo.getId());
                caa.setRemark(progress.toString());
                customerActivitiesAnswerService.insert(caa);
            } else{
                caa.setTotal(total);
                caa.setCorrect(correct);
                caa.setTargetId(vo.getId());
                caa.setRemark(progress.toString());
                customerActivitiesAnswerService.update(caa);
            }
        }
    }

    /** 验证数据 */
    private Result<?> validate(CustomerActivities vo, Activities activities, Integer total, Integer correct, Integer progress) {
        String type = activities.getType();
        if(YllConstants.ACTIVITIES_QUIZ.equals(type)){
            if(null == total && null == correct && null == progress){
                return Result.error("答题数、正确题数不能为空");
            }
            if(null != vo){
                //活动竞赛校验
                CustomerActivitiesAnswer caa = new CustomerActivitiesAnswer();
                caa.setTargetId(vo.getId());
                caa = customerActivitiesAnswerService.getByCondition(caa);
                if(null != caa){
                    Integer remark = Integer.parseInt(caa.getRemark());
                    if(caa.getTotal().equals(remark)){
                        return Result.error("您已完成本次答题");
                    }
                }
            }
        }
        return Result.ok();
    }

}
