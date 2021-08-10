package yll.entity;

import lombok.Data;

import java.util.Date;

/** 网红（达人）信息表 */
@SuppressWarnings("serial")
@Data
public class InternetCelebrity extends BaseEntity {

    /** 真实姓名*/
    private String realName;
    /** 身份证号 */
    private String idCard;
    /**  身份证正面 */
    private String image;
    /** 身份证反面 */
    private String picture;
    /** 所属机构(MCN)，空值时为独立网红*/
    private String mcnId;
    /** 擅长领域（逗号分隔） */
    private String expertise;
    /** 合作类型（逗号分隔） */
    private String cooperation;
    /** 个人描述 */
    private String description;
    /** 形象照片 */
    private String personalPortraits;
    /** 身高 */
    private String height;
    /** 微博 */
    private String microblog;
    /** 样品地址编码 */
    private String address;
    /** 公开状态（1-不公开，2-公开） */
    private Integer disclosure;

    /** 星级 */
    private String star;
    /** 标签，字典表获取 */
    private String tab;

    /** 链接费用 */
    private String linkFee;
    /** 出场费 */
    private String attendanceFee;
    /** 专场费 */
    private String specialFee;
    /** 线下活动费用 */
    private String underFee;
    /** 带货佣金 */
    private String commission;
    /** 擅长领域-其他 */
    private String other;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-待审核，2-已审核）*/
    private Integer state;
    /** 是否为轮播图（0-否，1-是） */
    private Integer slide;
    /** 是否为热门（0-否，1-是） */
    private Integer hot;
    /** 热门排序 */
    private Integer hotOrdinal;

}
