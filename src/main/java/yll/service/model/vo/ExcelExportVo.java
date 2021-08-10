package yll.service.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Excel导出处理类
 */
@SuppressWarnings("serial")
@Data
public class ExcelExportVo {

    //================返回参数======================
    @ExcelProperty(value = "名称", index = 0)
    private String nickname;

    @ExcelProperty(value = "头像", index = 1)
    private String headImg;

    @ExcelProperty(value = "城市", index = 2)
    private String city;

    @ExcelProperty(value = "抽奖结果", index = 3)
    private String draw;

    @ExcelProperty(value = "签到时间", index = 4)
    private String signTime;

}
