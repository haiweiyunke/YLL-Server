package yll.entity;

import lombok.Data;

import java.util.List;

/** 达人平台信息 */
@SuppressWarnings("serial")
@Data
public class Platform extends BaseEntity {

    /** 第三方平台标识（字典表获取）*/
    private String platformType;
    /** 平台id */
    private String platformId;
    /** 网名 */
    private String onlineName;
    /** 平台头像 */
    private String headImg;
    /** 粉丝数 */
    private Integer fans;
    /** 每日时长 */
    private String duration;
    /** 专场数 */
    private String sessions;
    /** 直播时间 */
    private String liveTime;
    /** 粉丝增长率 */
    private String fansGrowthRate;
    /** 最高人气 */
    private String highestPopularity;
    /** 出场费  */
    private String appearanceFee;
    /** 单场带货数  */
    private String goodsNum;
    /** 单场带货金额  */
    private String moneyNum;
    /** 链接费用（低） */
    private String linkFeeOne;
    /** 链接费用（高） */
    private String linkFeeTwo;
    /** 专场费（低） */
    private String specialFeeOne;
    /** 专场费 （高） */
    private String specialFeeTwo;

    /** 上月粉丝数，定时任务每月30日mark计算  */
    private String lastFans;
    /** 备注 */
    private String remark;
    /** mcn状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

     /** 自定义字段-json字符串 */
    private String platformJson;

    /**
     *
     * 格式为   [{"key" : "姓名", "value" : "李四"}]
     * 后继续轮播图混合编辑  dh_slide 表
     */

}
