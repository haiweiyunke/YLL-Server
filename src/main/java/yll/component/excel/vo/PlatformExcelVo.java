package yll.component.excel.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import yll.entity.BaseEntity;

/** 达人平台信息 */
@SuppressWarnings("serial")
@Data
public class PlatformExcelVo{

    /** 序号 */
    @ExcelProperty(value = "序号", index = 0)
    private String index;

    /** 用户名 */
    @ExcelProperty(value = "用户名", index = 2)
    private String nickname;

    /** 平台名 */
    @ExcelProperty(value = "平台名", index = 3)
    private String platformType;

    /** 平台id */
    @ExcelProperty(value = "平台id", index =  4)
    private String platformId;

    /** 网名 -特*/
    @ExcelProperty(value = "网名", index = 5)
    private String onlineName;
    /** 粉丝数（万）-特 */
    @ExcelProperty(value = "粉丝数", index = 6)
    private String fans;
    /** 平均每日时长 */
    @ExcelProperty(value = "平均每日时长", index = 7)
    private String duration;
    /** 开拨次数 */
    @ExcelProperty(value = "开拨次数", index = 8)
    private String sessions;
    /** 直播时间 */
    @ExcelProperty(value = "直播时间", index = 9)
    private String liveTime;
    /** 链接费用 */
    @ExcelProperty(value = "链接费用", index = 10)
    private String linkFee;
    /** 专场费 */
    @ExcelProperty(value = "专场费", index = 11)
    private String specialFee;
    /** 最高场观 */
    @ExcelProperty(value = "最高场观", index = 12)
    private String highestPopularity;
    /** 单场带货数  */
    @ExcelProperty(value = "单场带货数", index = 13)
    private String goodsNum;
    /** 单场带货金额  */
    @ExcelProperty(value = "单场带货金额", index = 14)
    private String moneyNum;

}
