package yll.service.model.vo;

import lombok.Data;
import yll.entity.Task;

import java.util.List;

/**
 * DH
 * 任务
 */
@SuppressWarnings("serial")
@Data
public class TaskVo extends Task {

    /** 有效开始日期 */
    private String validStartTimeStr;

    /** 有效结束日期 */
    private String validEndTimeStr;

    /** 活动档期-开始时间 */
    private String marketingStartTimeStr;

    /** 活动档期-结束时间 */
    private String marketingEndTimeStr;


    /**  出场费小 */
    private String appearanceFeesOne;

    /**  出场费大 */
    private String appearanceFeesTwo;

    /**  出场费小 */
    private String commissionRatioOne;

    /**  出场费大 */
    private String commissionRatioTwo;

    /** 类别，1-正序，2-倒叙。 */
    private Integer expertiseOrder;

    /** 平台，1-正序，2-倒叙。 */
    private Integer platformOrder;

    /** 价格，1-正序，2-倒叙。 */
    private Integer retailPriceOrder;

    /** mybatis order by 使用 */
    private String orderBy;

    /**  带货方式-中文（字典表获取） 1*/
    private String typeStr;

    /**  主体要求-中文（字典表获取） */
    private String sponsorStr;

    /**  达人领域-中文（字典表获取）1 */
    private String expertiseStr;

    /**  带货平台-中文（字典表获取）1 */
    private String platformStr;

    /**  直播渠道-中文（字典表获取）1 */
    private String livePlatformStr;

    /**  所属类目-中文（字典表获取）1 */
    private String categoryStr;

    /**  优惠方式-中文（字典表获取）1 */
    private String discountStr;

    /**  保底出场费-中文（字典表获取） */
    private String appearanceFeesStr;

    /**  商品-接收（字串","隔开） */
    private String productsIn;
    /**  达人-接收（字串","隔开） */
    private String celebrityIn;

    /**  任务达人表使用-达人customerId */
    private String cid;

    /**  当前状态-入参集合 */
    private List<String> tabStateList;

    //================返回参数======================
    private String enterpriseName;

    /** 旧轮播id */
    private String oldSlideId;
    /** 旧推广id */
    private String oldExtensionId;

    /**  商品-返回 */
    private List<TaskProductsVo> productsOut;
    /**  达人-返回 */
    private List<TaskCelebrityVo> celebrityOut;
    /**  进程-返回 */
    private List<TaskProcessVo> processOut;

    /**  任务达人表使用-mcnid */
    private String mcnId;
    /**  任务达人表使用-任务达人表id */
    private String tcId;

    /** 任务场地-中文 */
    private String taskPlaceStr;

    /** 当前状态-中文 */
    private String currentStateStr;

    /** 用户昵称 */
    private String nickname;
    /** 用户头像 */
    private String headImg;

}
