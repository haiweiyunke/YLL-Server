package yll.app.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.Activities;
import yll.entity.CustomerActivities;
import yll.service.*;
import yll.service.model.YllConstants;
import yll.service.model.vo.ActivitiesModulesVo;
import yll.service.model.vo.ActivitiesQuestionsVo;
import yll.service.model.vo.ActivitiesVo;
import yll.service.model.vo.CustomerActivitiesAnswerVo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * APP活动
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/activities")
public class ActivitiesAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommonService commonService;
    @Autowired
    private ActivitiesService activitiesService;
    @Autowired
    private ActivitiesQuestionsService activitiesQuestionsService;
    @Autowired
    private ActivitiesModulesService activitiesModulesService;
    @Autowired
    private CustomerActivitiesService customerActivitiesService;
    @Autowired
    private CustomerActivitiesAnswerService customerActivitiesAnswerService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/activities/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, ActivitiesVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new ActivitiesVo());
        condition.setEnabled(YllConstants.ONE);
        Page<ActivitiesVo> page = activitiesService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/activities/modules/{id} <br>
     * 查询数据详情
     *  @param id  活动id
     */
    @GetMapping(value = "/modules/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //活动详情
        ActivitiesModulesVo entity = new ActivitiesModulesVo();
        entity.setTargetId(id);
        List<ActivitiesModulesVo> list = activitiesModulesService.getAppList(entity);
        Activities activities = activitiesService.getById(id);
        CustomerActivities vo = customerActivitiesService.getCustomerActivities(id);
        Map<String, Object> result = new HashMap<>();

        DateFormat sdf = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        result.put("endTime", null == activities.getEndTime() ? "" : sdf.format(activities.getEndTime()));
        result.put("image", StringUtils.isBlank(activities.getImage()) ? "" : activities.getImage());
        result.put("isPartake", null == vo ? 0 : 1);    //0-未参与, 1-已参与
        result.put("name", activities.getName());
        result.put("list", list);
        return Result.ok(result);
    }

    /**
     * [GET] /app/activities/questions/{id} <br>
     * 获取活动竞赛题
     */
    @GetMapping(value = "/questions/{id}")
    public Result<?> getQuestions(@PathVariable("id") String id) {
        if(StringUtils.isBlank(id)){
            return Result.error("缺少活动id");
        }
        //题目
        ActivitiesQuestionsVo vo = new ActivitiesQuestionsVo();
        vo.setActivityId(id);
        List<ActivitiesQuestionsVo> list = activitiesQuestionsService.getAppList(vo);
        //用户答题情况
        CustomerActivities ca = customerActivitiesService.getCustomerActivities(id);
        CustomerActivitiesAnswerVo caa = new CustomerActivitiesAnswerVo();
        if(null != ca){
            caa.setTargetId(ca.getId());
            caa = customerActivitiesAnswerService.getAppDetail(caa);
            Integer progress = caa.getProgress();
            if(caa.getTotal().equals(progress)){
                return Result.error("您已完成本次答题");
            }
        } else{
            caa.setCorrect(0);
            caa.setTotal(0);
            caa.setProgress(0);
            caa.setRate("0.0%");
        }
        Map<String,Object> resut = new HashMap<>();
        resut.put("questions",list);
        resut.put("answer", caa);
        return Result.ok(resut);
    }

}
