package yll.component.excel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;
import yll.entity.BaseEntity;

/** 网红（达人）信息-Excel  */
@SuppressWarnings("serial")
@Data
public class InternetCelebrityExcelVo {

    /** 序号 */
    @NumberFormat("#")
    @ExcelProperty(value = "序号", index = 0)
    private String index;


    /** 用户名 */
    @ExcelProperty(value = "用户名", index = 2)
    private String nickname;
    /** 性别（1-女，2-男） */
    @ExcelProperty(value = "性别", index = 3)
    private String gender;

    /** 真实姓名*/
    @ExcelProperty(value = "真实姓名", index = 4)
    private String realName;
    /** 擅长领域（逗号分隔） */
    @ExcelProperty(value = "擅长领域", index = 5)
    private String expertise;
    /** 身高 */
    @ExcelProperty(value = "身高", index = 6)
    private String height;
    /** 出场费 */
    @ExcelProperty(value = "出场费", index = 14)
    private String attendanceFee;
    /** 带货佣金 */
    @ExcelProperty(value = "带货佣金", index = 15)
    private String commission;
    /** 个人描述 */
    @ExcelProperty(value = "个人描述", index = 16)
    private String description;

    /** MCN机构 -特*/
    @ExcelProperty(value = "MCN机构", index = 20)
    private String mcn;
    /** 公开状态（1-不公开，2-公开）-特 */
    @ExcelProperty(value = "公开状态", index = 23)
    private String disclosure;


}
