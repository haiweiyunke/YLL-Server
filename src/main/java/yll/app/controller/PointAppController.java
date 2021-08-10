package yll.app.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.ObjectUtils;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.CustomerPoints;
import yll.entity.CustomerPointsDetails;
import yll.entity.Dic;
import yll.service.CustomerPointsDetailsService;
import yll.service.CustomerPointsService;
import yll.service.DicService;
import yll.service.model.vo.CustomerPointsDetailsVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * APP积分
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/point")
public class PointAppController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerPointsService customerPointsService;

    @Autowired
    private CustomerPointsDetailsService customerPointsDetailsService;

    @Autowired
    private DicService dicService;

    // ==============================Methods==========================================
    /**
     * [GET] /app/point/index <br>
     * 查询数据主页
     */
    @GetMapping(value = "/index")
    public Result<?> index(CustomerPointsDetailsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        CustomerPoints points = new CustomerPoints();
        points.setTargetId(customerId);
        //总积分
        points = customerPointsService.getAppDetail(points);
        //当日积分总和
        Integer today = customerPointsDetailsService.findSum(condition);
        //组合
        Map result = new HashMap();
        result.put("points", points.getPoint());
        result.put("today", today);
        return Result.ok(result);
    }

    /**
     * [POST] /app/point/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> list(Pagination pagination, CustomerPointsDetailsVo condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        //积分明细
        condition = ObjectUtils.defaultIfNull(condition, new CustomerPointsDetailsVo());
        condition.setTargetId(customerId);
        Page<CustomerPointsDetailsVo> page = customerPointsDetailsService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/point/save <br>
     *  新增积分明细
     */
    @PostMapping(value = "/save")
    public Result<?> collect(CustomerPointsDetails condition) {
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setTargetId(customerId);
        customerPointsDetailsService.insert(condition);
        return Result.ok();
    }

    /**
     * [GET] /app/point/explain <br>
     * 查询数据主页
     */
    @GetMapping(value = "/explain")
    public Result<?> explain(CustomerPointsDetailsVo condition) {
        //积分说明
        Dic dic = dicService.getByCode("integralExplain");
        //获取积分方法
        Dic dicCondition  = new Dic();
        dicCondition.setTargetId("integral");
        List<Dic> dicList = dicService.all(dicCondition);
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setTargetId(customerId);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Dic vo :
                dicList) {
            condition.setType(vo.getCode());
            CustomerPointsDetailsVo completions = customerPointsDetailsService.getCompletions(condition);
            Map<String, Object> map = new HashMap<>();
            map.put("name", vo.getCodename());
            map.put("num", completions.getNum());
            getPoint(vo, map);
            list.add(map);
        }
        //组合
        Map result = new HashMap();
        result.put("explain", dic.getRemark());
        result.put("list", list);
        return Result.ok(result);
    }

    /**
     * 获取增加积分
     * @param vo
     * @param map
     */
    public void getPoint(Dic vo, Map<String, Object> map) {
        if(StringUtils.isNotBlank(vo.getRemark())){
            String[] arges = vo.getRemark().split("\\+");
            if(arges.length > 1){
                map.put("point", "+" +arges[1]);
            } else{
                map.put("point", "");
            }
        } else{
            map.put("point", "");
        }
    }

}
