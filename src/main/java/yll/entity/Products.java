package yll.entity;

import lombok.Data;

/** 产品 */
@SuppressWarnings("serial")
@Data
public class Products extends BaseEntity {

    /** 名称 */
    private String name;
    /** 所属店铺 */
    private String sid;
    /** 封面 */
    private String cover;
    /** 商品简介*/
    private String profile;
    /** 商品详情（文字）*/
    private String content;
    /** 列表预览图片(","分割)'*/
    private String image;
    /** 详情页图片(","分割)'*/
    private String picture;
    /** 视频(","分割)'*/
    private String video;
    /** 零售价*/
    private Double price;
    /** 优惠价*/
    private Double discountPrice;
    /** 备注 */
    private String remark;
    /** 类型(字典表获取) */
    private String type;
    /** 状态（0-删除，1-正常，2-隐藏） */
    private Integer state;
    /** 分享次数 */
    private Integer share;
    /** 收藏次数 */
    private Integer collects;
    /** 点赞次数 */
    private Integer likes;
    /** 是否为轮播图（0-否，1-是） */
    private Integer slide;

    /** 类型-其他 */
    private String other;
    /** 月销量 */
    private String sales;
    /** 带货参数-带货平台(","分割)' */
    private String platform;
    /** 带货参数-带货方式（字典表获取，","分割）*/
    private String bringType;
    /** 带货参数-佣金 */
    private String commission;
    /** 带货参数-是否退样（1-否，2-是） */
    private Integer giveBack;
    /** 带货参数-样品押金 */
    private String deposit;
    /** 带货参数-佣金比 */
    private String depositRatio;

    /**  链接/ID */
    private String link;
    /**  供货数量 */
    private String quantity;
    /**  经营许可证 */
    private String businessLicense;
    /**  产品相关许可证 */
    private String productLicense;
    /**  合格证 */
    private String certificate;
    /**  质检证书 */
    private String qualityCertificate;
    /**  商品广告 */
    private String advertisement;
    /**  地区 */
    private String region;

}
