package yll.service.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * Excel导出处理类
 */
@SuppressWarnings("serial")
@Data
public class ExcelExportChartVo {

    //================返回参数======================
    @ExcelProperty(value = "主播", index = 0)
    private String column01;

    @ExcelProperty(value = "日期", index = 1)
    private String column02;

    @ExcelProperty(value = "星期", index = 2)
    private String column03;

    @ExcelProperty(value = "开始时间", index = 3)
    private String column04;

    @ExcelProperty(value = "结束时间", index = 4)
    private String column05;

    @ExcelProperty(value = "直播分钟", index = 5)
    private String column06;

    @ExcelProperty(value = "累计观看人", index = 6)
    private String column07;

    @ExcelProperty(value = "累计观看（人次）", index = 7)
    private String column08;

    @ExcelProperty(value = "同时最高在线人数", index = 8)
    private String column09;

    @ExcelProperty(value = "粉丝总数", index = 9)
    private String column10;

    @ExcelProperty(value = "新增粉丝人数", index = 10)
    private String column11;

    @ExcelProperty(value = "评论（条）", index = 11)
    private String column12;

    @ExcelProperty(value = "钻石数（个）", index = 12)
    private String column13;

    @ExcelProperty(value = "用户支付订单（笔）", index = 13)
    private String column14;

    @ExcelProperty(value = "支付用户数（人）", index = 14)
    private String column15;

    @ExcelProperty(value = "用户支付金额（元）", index = 15)
    private String column16;

    @ExcelProperty(value = "预估佣金", index = 16)
    private String column17;

    @ExcelProperty(value = "观看用户下单率", index = 17)
    private String column18;

    @ExcelProperty(value = "观看次数下单率", index = 18)
    private String column19;

    @ExcelProperty(value = "单个用户支付订单数量", index = 19)
    private String column20;

    @ExcelProperty(value = "客单价", index = 20)
    private String column21;

    @ExcelProperty(value = "分产", index = 21)
    private String column22;

    @ExcelProperty(value = "分钟增粉", index = 22)
    private String column23;

    @ExcelProperty(value = "红包金额", index = 23)
    private String column24;

    @ExcelProperty(value = "转化成本", index = 24)
    private String column25;

    @ExcelProperty(value = "备注红包", index = 25)
    private String column26;

}
