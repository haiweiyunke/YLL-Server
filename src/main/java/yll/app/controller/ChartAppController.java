package yll.app.controller;

import com.alibaba.excel.EasyExcel;
import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.Customer;
import yll.service.CustomerService;
import yll.service.ExcelService;
import yll.service.PlatformAttributeKeyService;
import yll.service.PlatformAttributeValueService;
import yll.service.model.vo.CustomerVo;
import yll.service.model.vo.ExcelExportChartVo;
import yll.service.model.vo.ExcelExportVo;
import yll.service.model.vo.PlatformAttributeValueVo;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 图表
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/chart")
public class ChartAppController {

    // ==============================Fields===========================================
    @Autowired
    private PlatformAttributeValueService platformAttributeValueService;
    @Autowired
    private PlatformAttributeKeyService platformAttributekeyService;
    @Autowired
    private ExcelService excelService;
    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================

    /**
     * [POST] /app/chart/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> list(PlatformAttributeValueVo condition) {
        String type = condition.getType();
        String kid = condition.getKid();
        if(StringUtils.isBlank(type) || StringUtils.isBlank(kid)){
            return Result.error("缺少参数");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        condition.setCid(customerId);
        List<PlatformAttributeValueVo> list = new ArrayList<>();

        String[] pinduoduoKidArray = {"2020070117115413300002","2020070117141250700002","2020070117142697600002","2020070117150810100002"};
        if(Arrays.asList(pinduoduoKidArray).contains(kid)){
            list = platformAttributeValueService.chartCalculationPinduoduo(condition);
        } else{
            list = platformAttributeValueService.chart(condition);
        }
        //处理周平均、月平均
        List<PlatformAttributeValueVo> result = getWeekOrMonthAvg(condition, list);
        if(result.size() > 0){
            list = result;
        }
        return Result.ok(list);
    }


    /**
     * [POST] /app/chart/export <br>
     * excel导出数据(新增|更新)
     */
    @PostMapping(value = "/export")
    public void exportExcel(HttpServletResponse response, PlatformAttributeValueVo condition) {
        String type = condition.getType();
        String cid = condition.getCid();
        if(StringUtils.isNotBlank(cid)){
            Customer cust = customerService.getById(cid);
            condition.setUsername(cust.getUsername());
        }
        try {

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //文件名称
            String name = System.currentTimeMillis() + ".xlsx";
            // 防止中文乱码
            String fileName = URLEncoder.encode(name, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //处理数据
            List<ExcelExportChartVo> data = new ArrayList<>();
            if("p-pinduoduo".equals(type)){
                data = excelService.exportExcelForPinduoduo(condition);
            }
            if(data.size() > 0){
                EasyExcel.write(response.getOutputStream(), ExcelExportChartVo.class).sheet("星精选(达人）").doWrite(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // ==============================ToolMethods======================================

    /**
     * 处理周平均、月平均
     * @param condition
     * @param list
     * @return
     */
    private List<PlatformAttributeValueVo> getWeekOrMonthAvg(PlatformAttributeValueVo condition, List<PlatformAttributeValueVo> list) {
        List<PlatformAttributeValueVo> result = new ArrayList<>();
        String timeType = condition.getAppCreatedTime();
        String flag = condition.getFlag();
        //星期
        if("2".equals(flag) && "week".equals(timeType)){
            if(null == list || list.size() < 1){
                //无数据拼接
                for (int i = 1; i < 8; i++) {
                    PlatformAttributeValueVo p = new PlatformAttributeValueVo();
                    String time = getWeekString(i);
                    p.setAppCreatedTime(time);
                    p.setName("0");
                    result.add(p);
                }
            } else{
                //有数据拼接
                for (int i = 1; i < 8; i++) {
                    PlatformAttributeValueVo p = new PlatformAttributeValueVo();
                    String time = getWeekString(i);
                    String name = "0";
                    for (PlatformAttributeValueVo vo :
                            list) {
                        String t = vo.getAppCreatedTime();
                        if(t.equals(i+"")){
                            name = vo.getName();
                            break;
                        }
                    }
                    p.setAppCreatedTime(time);
                    p.setName(name);
                    result.add(p);
                }
            }
        }

        //月份
        if("2".equals(flag) && "month".equals(timeType)){
            if(null == list || list.size() < 1){
                //无数据拼接
                for (int i = 1; i < 32; i++) {
                    PlatformAttributeValueVo p = new PlatformAttributeValueVo();
                    p.setAppCreatedTime(i + "");
                    p.setName("0");
                    result.add(p);
                }
            } else{
                //有数据拼接
                for (int i = 1; i < 32; i++) {
                    PlatformAttributeValueVo p = new PlatformAttributeValueVo();
                    String name = "0";
                    for (PlatformAttributeValueVo vo :
                            list) {
                        String t = vo.getAppCreatedTime();
                        String monthString = getMonthString(t);
                        if(StringUtils.isBlank(monthString)){
                            monthString = t;
                        }
                        if(monthString.equals(i+"")){
                            name = vo.getName();
                            break;
                        }
                    }
                    p.setAppCreatedTime(i + "");
                    p.setName(name);
                    result.add(p);
                }
            }
        }
        return result;
    }

    /**
     * 获取周
     * @param i
     * @return
     */
    private String getWeekString(int i) {
        String name;
        switch(i){
            case 1 :
                name = "周一";
                break;
            case 2 :
                name = "周二";
                break;
            case 3 :
                name = "周三";
                break;
            case 4 :
                name = "周四";
                break;
            case 5 :
                name = "周五";
                break;
            case 6 :
                name = "周六";
                break;
            case 7 :
                name = "周日";
                break;
            default :
                name = "未知";
        }
        return name;
    }

    /**
     * 获取周
     * @param
     * @return
     */
    private String getMonthString(String str) {
        String name;
        switch(str){
            case "01" :
                name = "1";
                break;
            case "02" :
                name = "2";
                break;
            case "03" :
                name = "3";
                break;
            case "04" :
                name = "4";
                break;
            case "05" :
                name = "5";
                break;
            case "06" :
                name = "6";
                break;
            case "07" :
                name = "7";
                break;
            case "08" :
                name = "8";
                break;
            case "09" :
                name = "9";
                break;
            default :
                name = "";
        }
        return name;
    }


}
