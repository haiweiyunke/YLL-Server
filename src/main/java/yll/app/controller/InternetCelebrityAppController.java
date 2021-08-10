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
import yll.component.util.RelativeNumberFormatTool;
import yll.entity.Customer;
import yll.entity.Dic;
import yll.entity.InternetCelebrity;
import yll.entity.Platform;
import yll.service.CustomerService;
import yll.service.DicService;
import yll.service.InternetCelebrityService;
import yll.service.PlatformService;
import yll.service.model.YllConstants;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.PlatformVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  达人
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/celebrity")
public class InternetCelebrityAppController<main> {

    // ==============================Fields===========================================
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private DicService dicService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/celebrity/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        condition.setState(YllConstants.TWO);
        condition.setEnabled(YllConstants.ONE);
        condition.setDisclosure(YllConstants.TWO);
        Page<InternetCelebrityVo> page = internetCelebrityService.getAppList(pagination, condition);
        List<InternetCelebrityVo> records = page.getRecords();
        //数字格式转换
        numFormat(records);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/celebrity/hot/list <br>
     * 查询数据列表-热门
     */
    @PostMapping(value = "/hot/list")
    public Result<?> pagedQueryHot(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        pagination.setOffset(0);
        pagination.setLimit(20);
        condition.setIsHot("1");
        condition.setHot(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        condition.setDisclosure(YllConstants.TWO);
        Page<InternetCelebrityVo> page = internetCelebrityService.getAppList(pagination, condition);
        List<InternetCelebrityVo> records = page.getRecords();
        //数字格式转换
        numFormat(records);
        return PageResult.of(page);
    }

    /**
     * 达人数字展示格式化
     * @param records
     */
    private void numFormat(List<InternetCelebrityVo> records) {
        for (InternetCelebrityVo vo :
                records) {
            String fans = RelativeNumberFormatTool.relativeNumberFormat(vo.getFans(), RelativeNumberFormatTool.PY);
            vo.setFans(fans);
            String follows = RelativeNumberFormatTool.relativeNumberFormat(vo.getFollows(), RelativeNumberFormatTool.PY);
            vo.setFollows(follows);
            String likes = RelativeNumberFormatTool.relativeNumberFormat(vo.getLikes(), RelativeNumberFormatTool.PY);
            vo.setLikes(likes);
        }
    }


    /**
     * [POST] /app/celebrity/like/list <br>
     * 查询数据列表-最爱
     */
    @PostMapping(value = "/like/list")
    public Result<?> pagedQueryLike(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        condition.setIsLike("1");
        condition.setEnabled(YllConstants.ONE);
        condition.setDisclosure(YllConstants.TWO);
        Page<InternetCelebrityVo> page = internetCelebrityService.getAppList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [GET] /app/celebrity/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        if(StringUtils.isBlank(id)){
            return Result.error("缺少达人标识");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        //达人详情
        InternetCelebrityVo condition = new InternetCelebrityVo();
        condition.setCreator(id);   //达人id
        condition.setCustomerId(customerId);    //当前操作人
        InternetCelebrityVo entity = internetCelebrityService.getAppDetail(condition);
        //数字格式化
       /*String follows = RelativeNumberFormatTool.relativeNumberFormat(entity.getFollows(), RelativeNumberFormatTool.PY);
        entity.setFollows(follows);
        String likes = RelativeNumberFormatTool.relativeNumberFormat(entity.getLikes(), RelativeNumberFormatTool.PY);
        entity.setLikes(likes);*/
        String platformCode = entity.getPlatformCode();
        List<Platform> platformList = new ArrayList();
        //达人平台
        if(StringUtils.isNotBlank(platformCode)){
            PlatformVo pv = new PlatformVo();
            pv.setCreator(id);    //用customerId查询
            String[] codeArray = platformCode.split(",");
           for (String code :
                    codeArray) {
                pv.setPlatformType(code);
                PlatformVo p = platformService.getAppDetail(pv);
               if(null != p){
                    //数字格式转换
                   /* String fansStr = RelativeNumberFormatTool.relativeNumberFormat(p.getFans(), RelativeNumberFormatTool.PY);
                    p.setFansStr(fansStr);
                    String moneyNum = RelativeNumberFormatTool.relativeNumberFormat(StringUtils.isBlank(p.getMoneyNum()) ? 0 : p.getMoneyNum(), RelativeNumberFormatTool.PY);
                    p.setMoneyNum(moneyNum);
                    */
                    platformList.add(p);
                }
            }
        }
        //封装
        Map<String, Object> result   = new HashMap<>();
        result.put("celebrity", entity);
        result.put("platform", platformList);
        return Result.ok(result);
    }

    /**
     * [GET] /app/celebrity/auth <br>
     * 达人认证回显
     */
    @GetMapping(value = "/auth")
    public Result<?> auth() {
        String customerId = AppSecuritysUtil.getCustomerId();
//        String customerId = "2020040220542702400001";
        InternetCelebrityVo ic = new InternetCelebrityVo();
        ic.setCreator(customerId);
        ic = internetCelebrityService.getAppAuth(ic);
        PlatformVo pla = new PlatformVo();
        pla.setCreator(customerId);
        List<PlatformVo> plaList = platformService.getAppAuth(pla);
        //处理数字返回格式
        /*for (PlatformVo vo :
                plaList) {
            String moneyNum = RelativeNumberFormatTool.relativeNumberFormat(StringUtils.isBlank(vo.getMoneyNum()) ? 0 : vo.getMoneyNum(), RelativeNumberFormatTool.PY);
            vo.setMoneyNum(moneyNum);
        }*/
        Map<String, Object> result = new HashMap<>();
        result.put("celebrity", ic);
        result.put("platform", plaList);
        return Result.ok(result);
    }

    /**
     * [POST] /app/celebrity/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(InternetCelebrityVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer customer = customerService.getById(customerId);
        String roleType = customer.getRoleType();
        InternetCelebrity vo = new InternetCelebrity();
        vo.setCreator(customerId);
        vo = internetCelebrityService.getByCondition(vo);
        condition.setCreator(customerId);
        if(StringUtils.isBlank(roleType) || "1".equals(roleType)){
            //当用户角色为普通用户或未指定时，可进行认证申请
            if (null == vo || StringUtils.isBlank(vo.getId())) {
                condition.setState(1);  //未审核
                internetCelebrityService.insert(condition);
            } else {
                condition.setId(vo.getId());
//                condition.setState(1);  //未审核
                internetCelebrityService.update(condition);
            }

        } else if("2".equals(roleType)){
                //修改
            condition.setId(vo.getId());
//            condition.setState(1);  //未审核
            internetCelebrityService.update(condition);
        } else{
            return Result.error("您已完成相关认证");
        }
        return Result.ok();
    }


    /**
     * [POST] /app/celebrity/platform <br>
     * 查询达人平台
     */
    @PostMapping(value = "/platform")
    public Result<?> get(String platformCode, String id) {
        String customerId = AppSecuritysUtil.getCustomerId();
        //平台信息
        Platform platform = new Platform();
        platform.setCreator(id);
        platform.setPlatformType(platformCode);
        platform = platformService.getByCondition(platform);
        //封装
        Map<String,Object>  result = new HashMap<>();
        result.put("fans",platform.getFans());
        result.put("fansGrowthRate",platform.getFansGrowthRate());
        result.put("sessions",platform.getSessions());
        result.put("highestPopularity",platform.getHighestPopularity());
        return Result.ok(result);
    }


    /**
     * [GET] /app/celebrity/dic <br>
     * 达人筛选
     */
    @GetMapping(value = "/dic")
    public Result<?> dic() {
        List<Dic> linkFee = dicService.getAppList("linkFee");
        List<Dic> attendanceFee = dicService.getAppList("attendanceFee");
        List<Dic> specialFee = dicService.getAppList("specialFee");
//        List<Dic> underFee = dicService.getAppList("underFee");
        List<Dic> fansQuantity = dicService.getAppList("fansQuantity");
        List<Dic> commission = dicService.getAppList("commission");
        List<Dic> gender = dicService.getAppList("gender");
        List<Dic> age = dicService.getAppList("age");
        List<Dic> platform = dicService.getAppList("platform");
        List<Dic> expertise = dicService.getAppList("expertise");
        List<Dic> cooperation = dicService.getAppList("cooperation");

        Map<String,Object> result = new HashMap<>();
        result.put("linkFee", linkFee);
        result.put("attendanceFee", attendanceFee);
        result.put("specialFee", specialFee);
//        result.put("underFee", underFee);
        result.put("fansQuantity", fansQuantity);
        result.put("commission", commission);
        result.put("gender", gender);
        result.put("age", age);
        result.put("platform", platform);
        result.put("expertise", expertise);
        result.put("cooperation", cooperation);

        return Result.ok(result);
    }

    /**
     * [GET] /app/celebrity/out-dic <br>
     * 达人筛选
     */
    @GetMapping(value = "/out-dic")
    public Result<?> outDic() {
        List<Dic> attendanceFee = dicService.getAppList("attendanceFee");
        List<Dic> fansQuantity = dicService.getAppList("fansQuantity");
        List<Dic> likeNum = dicService.getAppList("likeNum");

        Map<String,Object> result = new HashMap<>();
        result.put("attendanceFee", attendanceFee);
        result.put("fansQuantity", fansQuantity);
        result.put("likeNum", likeNum);

        return Result.ok(result);
    }

    /**
     * [POST] /app/celebrity/invite/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/invite/list")
    public Result<?> invite(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        condition.setEnabled(YllConstants.ONE);
        Page<InternetCelebrityVo> page = internetCelebrityService.getAppInviteSearch(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [POST] /app/celebrity/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        pagination.setOffset(0);
        pagination.setLimit(4);
        condition.setSlide(YllConstants.ONE);
        condition.setEnabled(YllConstants.ONE);
        condition.setDisclosure(YllConstants.TWO);
        Page<InternetCelebrityVo> page = internetCelebrityService.getAppList(pagination, condition);
        return Result.ok(page.getRecords());
    }

}
