package yll.app.controller;

import com.alibaba.excel.EasyExcel;
import com.github.relucent.base.util.model.Result;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.Customer;
import yll.entity.Customer;
import yll.entity.Mcn;
import yll.service.CustomerService;
import yll.service.CustomerService;
import yll.service.InternetCelebrityService;
import yll.service.McnService;
import yll.service.model.vo.ExcelExportVo;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.McnVo;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  用户
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/customer")
public class CustomerAppController {

    // ==============================Fields===========================================
    @Autowired
    private CustomerService customerService;
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private McnService mcnService;

    // ==============================Methods==========================================
    /**
     * [GET] /app/customer/detail <br>
     * 用户信息
     */
    @GetMapping(value = "/detail")
    public Result<?> detail() {
        String customerId = AppSecuritysUtil.getCustomerId();
//        String customerId = "2020040312000216800001";
        Customer customer = new Customer();
        customer.setId(customerId);
        customer = customerService.getAppDetail(customer);
        if(customer == null){
            return Result.error("暂无数据");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("basic", customer);
        InternetCelebrityVo celebrity = new InternetCelebrityVo();
        McnVo mcn = new McnVo();
        if("2".equals(customer.getRoleType())){
            //达人详情
            celebrity.setCreator(customerId);   //达人id
            celebrity.setCustomerId(customerId);    //当前操作人
            celebrity = internetCelebrityService.getAppDetail(celebrity);
            result.put("celebrity", celebrity);
        } else if("3".equals(customer.getRoleType())){
            //MCN详情
            mcn.setCreator(customerId);   //MCN的id
            mcn = mcnService.getAppDetail(mcn);
            result.put("mcn", mcn);
        }
        return Result.ok(result);
    }

    /**
     * [GET] /rest/customer/export <br>        //TODO 导出达人平台Excel，根据平台类型，渲染不同的excel表
     * excel导出数据(新增|更新)
     */
    @GetMapping(value = "/export")
    public void exportExcel(HttpServletResponse response, InternetCelebrityVo condition) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //文件名称
            String name = System.currentTimeMillis() + ".xlsx";
            // 防止中文乱码
            String fileName = URLEncoder.encode(name, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //处理数据
            List<ExcelExportVo> data = internetCelebrityService.excelexportData2(condition);
            EasyExcel.write(response.getOutputStream(), ExcelExportVo.class).sheet("与会人员").doWrite(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**
         * Excel导出步骤
         * 1、根据平台类型获取字典表平台信息
         * 2、根据平台类型，区分excel的sheet页，并获取模板，渲染表头。有固定表头一并渲染
         * 3、根据平台类型，获取不同平台的达人数据
         * 4、渲染达人数据
         */
    }

}
