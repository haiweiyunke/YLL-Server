package yll.service.model.vo;

import lombok.Data;
import yll.entity.InternetCelebrity;

import java.util.Date;
import java.util.List;

/**
 * DH
 * 网红（达人）信息处理类
 */
@SuppressWarnings("serial")
@Data
public class InternetCelebrityVo extends InternetCelebrity {

    //================查询参数======================
    /** 平台，英文逗号分隔  */
    private String platform;
    /** 平台code，英文逗号分隔  */
    private String platformCode;
    /** 平台code，英文逗号分隔  */
    private String platformImg;

    /**  性别  */
    private String gender;

    /**  地区 */
    private String location;

    /**  地区 */
    private String locationStr;

    /**  粉丝量  */
    private String fans;

    /**  年龄 */
    private String age;

    /**  链接费用小 */
    private String linkFeeOne;

    /**  链接费用大 */
    private String linkFeeTwo;

    /**  出场费小 */
    private String attendanceFeeOne;

    /**  出场费大 */
    private String attendanceFeeTwo;

    /**  专场费小 */
    private String specialFeeOne;

    /**  专场费大 */
    private String specialFeeTwo;

    /**  线下活动费用小 */
    private String underFeeOne;

    /**  线下活动费用大 */
    private String underFeeTwo;

    /**  粉丝量小 */
    private String fansOne;

    /**  粉丝量大 */
    private String fansTwo;

    /**  带货佣金小 */
    private String commissionOne;

    /**  带货佣金大 */
    private String commissionTwo;

    /**  年龄小 */
    private String ageOne;

    /**  年龄大 */
    private String ageTwo;

     /**  生日 */
    private String birthday;

     /**  mcn名称 */
    private String mcnName;

     /**  身高 */
    private String height;

    /**  达人平台信息 */
    private String platformJson;

    /**  生日，接口提交时使用 */
    private Date custBirthday;

    /**  粉条号 */
    private String fenNum;

    /**  邮箱 */
    private String email;

    /** 编码 */
    private String type;

    /** 编码名称(父类编码) */
    private String code;

    /** 非空为热门 */
    private String isHot;
    /** 非空为最爱 */
    private String isLike;

    /** 点赞 0-已点，1-未点 */
    private Integer clickLikes;

    /** 收藏 0-已收，1-未收 */
    private Integer clickCollects;

    /** 出场费用，1-正序，2-倒叙。 */
    private Integer attendanceFeeOrder;

    /** 粉丝数量，1-正序，2-倒叙。 */
    private Integer fansQuantityOrder;

    /** 点赞数（口碑），1-正序，2-倒叙。 */
    private Integer likeNumOrder;

    /** mybatis order by 使用 */
    private String orderBy;

    /** 样品地址 */
    private String addressStr;

    //================返回参数======================
    /** 用户id */
    private String customerId;
    /**  关注 */
    private String follows;
     /**  点赞 */
    private String likes;
    /**  昵称 */
    private String nickname;
    /**  头像 */
    private String headImg;
    /** 旧轮播id */
    private String oldSlideId;
    /** 旧热门id */
    private String oldHotId;
    /** 用户名 */
    private String username;

    /** 平台信息 */
    private String platformListStr;
    /** 申请时间-中文 */
    private String createdTimeStr;
}
