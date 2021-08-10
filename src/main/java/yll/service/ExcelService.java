package yll.service;

import com.alibaba.excel.EasyExcel;
import com.github.relucent.base.plug.security.Securitys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yll.component.excel.InternetCelebrityListener;
import yll.component.excel.PlatformListener;
import yll.component.excel.vo.InternetCelebrityExcelVo;
import yll.component.excel.vo.PlatformExcelVo;
import yll.entity.Customer;
import yll.entity.InternetCelebrity;
import yll.service.model.vo.ExcelExportChartVo;
import yll.service.model.vo.PlatformAttributeValueVo;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel处理
 */
@Transactional
@Service
public class ExcelService {

    // ==============================Fields===========================================
    @Autowired
    private Securitys securitys;
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private PlatformService platformService;
    @Autowired
    private PlatformAttributeValueService platformAttributeValueService;

    // ==============================Methods==========================================

    /**
     * 导入excel-达人
     * @param file
     * @return
     */
    public void excelRead(MultipartFile file) throws IOException {
        for (int i = 1; i < 6; i++) {
            InputStream inputStream = file.getInputStream();
            if(i == 1){
                //达人总表
                excelReadForInternetCelebrity(file, internetCelebrityService);
            } else{
                excelReadForPlatform(file, platformService, i);
            }
        }

    }


    /**
     * 导入excel-达人
     * @param internetCelebrityService 用于解决Listenerservice无法注入的问题
     * @return
     */
    public void excelReadForInternetCelebrity(MultipartFile file, InternetCelebrityService internetCelebrityService) throws IOException {
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();   //根据文件名，获取上传图片存储文件夹
        String[] arrays = fileName.split("\\.");
        fileName = arrays[0];
        // 这里 需要指定读用哪个class去读，然后读取指定sheet 文件流会自动关闭
        EasyExcel.read(inputStream, InternetCelebrityExcelVo.class, new InternetCelebrityListener(internetCelebrityService, fileName)).sheet(1).doRead();
    }


    /**
     * 导入excel-平台
     * @param platformService 用于解决Listenerservice无法注入的问题
     * @param sheetNum 2-抖音，3-快手，4-淘宝，5-拼多多
     * @return
     */
    public void excelReadForPlatform(MultipartFile file, PlatformService platformService, Integer sheetNum) throws IOException {
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename();   //根据文件名，获取上传图片存储文件夹
        String[] arrays = fileName.split("\\.");
        fileName = arrays[0];
        Map<String, String> params = new HashMap<>();
        params.put("fileName", fileName);
        params.put("num", sheetNum.toString());    //2-抖音，3-快手，4-淘宝，5-拼多多
        // 这里 需要指定读用哪个class去读，然后读取指定sheet 文件流会自动关闭
        EasyExcel.read(inputStream, PlatformExcelVo.class, new PlatformListener(platformService, params)).sheet(sheetNum).doRead();
    }


    /**
     * 导出excel-达人
     * @param condition 列表
     * @return
     */
    public List<ExcelExportChartVo> exportExcelForPinduoduo(PlatformAttributeValueVo condition) throws IOException {
        List<ExcelExportChartVo> list = platformAttributeValueService.exportExcelForPinduoduo(condition);
        return list;
    }

    // ==============================ToolMethods======================================
}
