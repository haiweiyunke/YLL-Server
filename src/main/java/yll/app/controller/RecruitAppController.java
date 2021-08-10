package yll.app.controller;

import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.*;
import yll.service.*;
import yll.service.model.vo.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  招聘
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/recruit")     //TODO 测试各修改项
public class RecruitAppController {

    // ==============================Fields===========================================
    @Autowired
    private RecruitCelebrityService recruitCelebrityService;
    @Autowired
    private RecruitCelebrityApplyService recruitCelebrityApplyService;
    @Autowired
    private RecruitEnterpriseService recruitEnterpriseService;
    @Autowired
    private RecruitEnterpriseDetailsService recruitEnterpriseDetailsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private McnService mcnService;
    @Autowired
    private DicService dicService;
    @Autowired
    private AreaService areaService;

    // ==============================招聘达人简历==========================================
    /**
     * [POST] /app/recruit/celebrity/list <br>
     * 查询数据列表-招聘达人简历
     */
    @PostMapping(value = "/celebrity/list")
    public Result<?> celebrityList(Pagination pagination, RecruitCelebrityVo condition) {
        Page<RecruitCelebrityVo> page = recruitCelebrityService.pagedQueryAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/recruit/celebrity/details <br>
     * 2、查询数据详情-招聘达人简历
     */
    @PostMapping(value = "/celebrity/details")
    public Result<?> celebrityDetails(RecruitCelebrityVo condition) {
        String id = condition.getId();
         if(StringUtils.isBlank(id)){
            //简历id为空，表示当前调取接口用户为达人本人
            String customerId = AppSecuritysUtil.getCustomerId();
            condition.setCid(customerId);
        }
        RecruitCelebrityVo result = recruitCelebrityService.getAppDetails(condition);
        return Result.ok(result);
    }

    /**
     * [POST] /app/recruit/celebrity/save <br>
     * 7、查询数据新增|修改-招聘达人简历
     */
    @PostMapping(value = "/celebrity/save")
    public Result<?> celebritySave(RecruitCelebrityVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            condition.setCid(customerId);
            condition.setCreator(customerId);
            condition.setCreatedTime(DateUtil.now());
            recruitCelebrityService.insert(condition);
        } else {
            condition.setModifier(customerId);
            condition.setModifiedTime(DateUtil.now());
            recruitCelebrityService.update(condition);
        }
        return Result.ok();
    }


    // ==============================招聘公司==========================================

    /**
     * [POST] /app/recruit/enterprise/list <br>
     * 查询数据列表-招聘公司
     */
    @PostMapping(value = "/enterprise/list")
    public Result<?> enterpriseList(Pagination pagination, RecruitEnterpriseVo condition) {
        Page<RecruitEnterpriseVo> page = recruitEnterpriseService.pagedQueryAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/recruit/enterprise/details <br>
     * 查询数据详情-招聘公司
     */
    @PostMapping(value = "/enterprise/details")
    public Result<?> enterpriseDetails(RecruitEnterpriseVo condition) {
        RecruitEnterpriseVo result = recruitEnterpriseService.getAppDetails(condition);
        return Result.ok(result);
    }

    /**
     * [POST] /app/recruit/enterprise/save <br>
     * 查询数据新增|修改-招聘公司
     */
    @PostMapping(value = "/enterprise/save")
    public Result<?> enterpriseSave(RecruitEnterpriseVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            condition.setCreator(customerId);
            condition.setCreatedTime(DateUtil.now());
            recruitEnterpriseService.insert(condition);
        } else {
            condition.setModifier(customerId);
            condition.setModifiedTime(DateUtil.now());
            recruitEnterpriseService.update(condition);
        }
        return Result.ok();
    }


    // ==============================招聘信息==========================================

    /**
     * [POST] /app/recruit/enterprise/information/list <br>
     * 4、查询数据列表-招聘信息
     */
    @PostMapping(value = "/enterprise/information/list")
    public Result<?> enterpriseInformationList(Pagination pagination, RecruitEnterpriseDetailsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCid(customerId);
        condition.setStateFlag(2);
        Page<RecruitEnterpriseDetailsVo> page = recruitEnterpriseDetailsService.pagedQueryAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/recruit/enterprise/information/details <br>
     * 查询数据详情-招聘信息
     */
    @PostMapping(value = "/enterprise/information/details")
    public Result<?> enterpriseInformationDetails(RecruitEnterpriseDetailsVo condition) {
        RecruitEnterpriseDetailsVo result = recruitEnterpriseDetailsService.getAppDetails(condition);
        return Result.ok(result);
    }

    /**
     * [POST] /app/recruit/enterprise/information/save <br>
     * 1、查询数据新增|修改-招聘信息
     */
    @PostMapping(value = "/enterprise/information/save")
    public Result<?> enterpriseInformationSave(RecruitEnterpriseDetailsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        String id = condition.getId();

        if (StringUtils.isBlank(id)) {
            condition.setCid(customerId);
            condition.setCreator(customerId);
            condition.setCreatedTime(DateUtil.now());
            recruitEnterpriseDetailsService.insert(condition);
        } else {
            condition.setModifier(customerId);
            condition.setModifiedTime(DateUtil.now());
            recruitEnterpriseDetailsService.update(condition);
        }
        return Result.ok();
    }


    /**
     * [POST] /app/recruit/enterprise/information/details/state <br>
     * 用工需求-上下架
     */
    @PostMapping(value = "/enterprise/information/details/state")
    public Result<?> enterpriseInformationDetailsState(RecruitEnterpriseDetailsVo condition) {
        String id = condition.getId();
        Integer state = condition.getState();
        if(StringUtils.isBlank(id)){
            return Result.error("缺少招聘信息标识");
        }
        if(null == state){
            return Result.error("缺少操作标识");
        }
        RecruitEnterpriseDetails red = recruitEnterpriseDetailsService.getById(id);
        red.setState(state);
        recruitEnterpriseDetailsService.update(red);
        return Result.ok();
    }

    // ==============================其他方法==========================================
    /**
     * [POST] /app/recruit/celebrity/apply/save <br>
     * 查询数据新增|修改-达人招聘申请
     */
    @PostMapping(value = "/celebrity/apply/save")
    public Result<?> celebrityApplySave(RecruitCelebrityApplyVo condition) {
        String redid = condition.getRedid();
        if(StringUtils.isBlank(redid)){
            return Result.error("缺少企业招聘信息标识");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCid(customerId);
        recruitCelebrityApplyService.insert(condition);
        return Result.ok();
    }


    /**
     * [POST] /app/recruit/celebrity/apply/list <br>
     * 8、用工需求-达人用户
     */
    @PostMapping(value = "/celebrity/apply/list")
    public Result<?> celebrityApplyList(Pagination pagination, RecruitEnterpriseDetailsVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new RecruitEnterpriseDetailsVo());
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCid(customerId);
        Page<RecruitEnterpriseDetailsVo> page = recruitEnterpriseDetailsService.pagedQueryGetAppCelebrityApplyList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [POST] /app/recruit/details/celebrity  <br>
     * 10、招聘详情-用户查看
     */
    @PostMapping(value = "/details/celebrity")
    public Result<?> celebrityApplyList(RecruitEnterpriseDetailsVo condition) {
        String redid = condition.getId();
        if(StringUtils.isBlank(redid)){
            return Result.error("缺少招聘信息标识");
        }
        Map<String,Object> result = new HashMap();
        //招聘信息
        RecruitEnterpriseDetailsVo details = recruitEnterpriseDetailsService.getAppDetails(condition);
        result.put("details", details);
        //公司信息
        String cid = details.getCid();
        Customer cust = customerService.getById(cid);
        String roleType = cust.getRoleType();
        if("3".equals(roleType)){
            Mcn m = new Mcn();
            m.setCreator(cid);
            McnVo mv = mcnService.getAppAuth(m);
            McnVo eResult = new McnVo();
            eResult.setLogo(mv.getLogo());
            eResult.setEnterpriseName(mv.getEnterpriseName());
            eResult.setStaffStr(mv.getStaffStr());
            result.put("enterprise", eResult);
        } else if("4".equals(roleType)){
            Enterprise e = new Enterprise();
            e.setCreator(cid);
            EnterpriseVo ev = enterpriseService.getAppAuth(e);
            EnterpriseVo eResult = new EnterpriseVo();
            eResult.setLogo(ev.getLogo());
            eResult.setEnterpriseName(ev.getEnterpriseName());
            eResult.setStaffStr(ev.getStaffStr());
            result.put("enterprise", eResult);
        }
        result.put("rt", roleType); //3-MCN，4-企业主
        return Result.ok(result);
    }


    /**
     * [POST] /app/recruit/details/enterprise  <br>
     * 9、招聘详情-企业
     */
    @PostMapping(value = "/details/enterprise")
    public Result<?> enterpriseApplyList(RecruitEnterpriseDetailsVo condition) {
        String redid = condition.getId();
        if(StringUtils.isBlank(redid)){
            return Result.error("缺少招聘信息标识");
        }
        //招聘信息
        RecruitEnterpriseDetailsVo details = recruitEnterpriseDetailsService.getAppDetails(condition);
        //申请人
        RecruitCelebrityApplyVo rca = new RecruitCelebrityApplyVo();
        rca.setRedid(redid);
        List<RecruitCelebrityApplyVo> applyList = recruitCelebrityApplyService.getCelebrityApplyList(rca);

        //招聘方角色类型
        Customer cust = customerService.getById(details.getCid());
        Map<String,Object> result = new HashMap();
        result.put("details", details);
        result.put("applyList", applyList);
        result.put("rt", cust.getRoleType()); //3-MCN，4-企业主
        return Result.ok(result);
    }


   /**
     * [POST] /app/recruit/enterprise/details/get <br>
     * 6、企业资料
     */
    @PostMapping(value = "/enterprise/details/get")
    public Result<?> enterpriseDetailsGet(RecruitEnterpriseDetailsVo condition) {
        String cid = condition.getCid();
        if(StringUtils.isBlank(cid)){
            return Result.error("缺少企业或MCN标识");
        }
        Map<String,Object> result = new HashMap();
        Customer cust = customerService.getById(cid);
        String roleType = cust.getRoleType();
        if("3".equals(roleType)){
            Mcn m = new Mcn();
            m.setCreator(cid);
            McnVo mv = mcnService.getAppAuth(m);
            McnVo enterpriseResult = new McnVo();
            enterpriseResult.setEnterpriseName(mv.getEnterpriseName());
            enterpriseResult.setEstablishTimeStr(mv.getEstablishTimeStr());
            enterpriseResult.setRegisteredCapital(mv.getRegisteredCapital());
            enterpriseResult.setLegalPerson(mv.getLegalPerson());
            enterpriseResult.setCreditCode(mv.getCreditCode());
            enterpriseResult.setLogo(mv.getLogo());
            result.put("enterpriseResult", enterpriseResult);
        } else if("4".equals(roleType)){
            //企业信息
            EnterpriseVo enterprise = new EnterpriseVo();
            enterprise.setCreator(cid);
            enterprise = enterpriseService.getAppAuth(enterprise);
            if(null == enterprise){
                return Result.error("未找到该企业或MCN信息");
            }
            EnterpriseVo enterpriseResult = new EnterpriseVo();
            enterpriseResult.setEnterpriseName(enterprise.getEnterpriseName());
            enterpriseResult.setEstablishTimeStr(enterprise.getEstablishTimeStr());
            enterpriseResult.setRegisteredCapital(enterprise.getRegisteredCapital());
            enterpriseResult.setLegalPerson(enterprise.getLegalPerson());
            enterpriseResult.setCreditCode(enterprise.getCreditCode());
            enterpriseResult.setLogo(enterprise.getLogo());
            result.put("enterpriseResult", enterpriseResult);
        }
        //招聘职位
        condition.setStateFlag(1);
        List<RecruitEnterpriseDetailsVo> redList = recruitEnterpriseDetailsService.getAppList(condition);
        result.put("redList", redList);
        return Result.ok(result);
    }

    /**
     * [POST] /app/recruit/index  <br>
     * 招聘广场
     */
    @PostMapping(value = "/index")
    public Result<?> recruitIndex(Pagination pagination, RecruitEnterpriseDetailsVo condition) {
        condition.setStateFlag(1);
        Page<RecruitEnterpriseDetailsVo> page = recruitEnterpriseDetailsService.pagedQueryGetIndexAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/recruit/condition  <br>
     * 招聘广场-筛选条件
     */
    @PostMapping(value = "/condition")
    public Result<?> recruitCondition() {
        Area area = new Area();
        //市
        area.setLevel(2);
        List<Area> province = areaService.getAppList(area);
        //行业
        List<Dic> industry = dicService.getAppList("industry");
        //职位
        List<Dic> jobRecruitment = dicService.getAppList("jobRecruitment");
        //薪酬范围
        List<Dic> salary = dicService.getAppList("salary");
        //学历
        List<Dic> education = dicService.getAppList("education");
        //工作经验
        List<Dic> experience = dicService.getAppList("experience");
        //工作性质
        List<Dic> nature = dicService.getAppList("nature");

        Map<String,Object> result = new HashMap();
        result.put("city", province); //市
        result.put("industry", industry); //行业
        result.put("jobRecruitment", jobRecruitment);   //职位
        result.put("salary", salary);     //薪酬范围
        result.put("education", education);  //学历
        result.put("experience", experience); //工作经验
        result.put("nature", nature); //工作性质
        return Result.ok(result);
    }

    /**
     * [POST] /app/recruit/condition/area  <br>
     * 招聘广场-筛选条件-区域
     */
    @PostMapping(value = "/condition/area")
    public Result<?> recruitConditionArea(Area area) {
        Integer pid = area.getPid();
        if(null == pid){
            return Result.error("缺少省级id");
        }
        //市
        area.setLevel(2);
        List<Area> city = areaService.getAppList(area);
        Map<String,Object> result = new HashMap();
        result.put("city", city); //市
        return Result.ok(result);
    }

}
