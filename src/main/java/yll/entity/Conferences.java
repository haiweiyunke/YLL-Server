package yll.entity;

import lombok.Data;

import java.util.Date;

/** 发布会 */
@SuppressWarnings("serial")
@Data
public class Conferences extends BaseEntity {

    /** 发布日期(直播类型使用) */
    private Date issuedTime;
    /** 发布会主题 */
    private String theme;
    /** 发布素材 */
    private String material;
    /** 封面 */
    private String cover;
    /** 详情资源 */
    private String photos;
    /** 广告位图片 */
    private String image;
    /** 视频 */
    private String video;
    /** 申请理由(直播类型使用) */
    private String reason;

    /** 类型（字典表获取） */
    private String type;

    /** 是否为推广（0-否，1-是） */
    private Integer extension;
    /** 推广排序 */
    private Integer extensionOrdinal;

    /** 备注 */
    private String remark;
    /** 审核状态（1-待审核，2-已审核, 3-未通过）*/
    private Integer state;
    /** 是否为轮播图（0-否，1-是）*/
    private Integer slide;
    /** 状态（1-可见，2-不可见）*/
    private Integer visible;

}
