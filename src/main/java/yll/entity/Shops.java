package yll.entity;

import lombok.Data;

/** 店铺 */
@SuppressWarnings("serial")
@Data
public class Shops extends BaseEntity {

    /** 名称 */
    private String name;
    /**  所属企业主 */
    private String eid;
    /** 封面 */
    private String cover;
    /** 店铺简介*/
    private String profile;
    /** 店铺详情（文字）*/
    private String content;
    /** 列表预览图片(","分割)'*/
    private String image;
    /** 详情页图片(","分割)'*/
    private String picture;
    /** 视频(","分割)'*/
    private String video;
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

}
