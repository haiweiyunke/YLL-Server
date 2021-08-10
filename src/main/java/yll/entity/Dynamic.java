package yll.entity;

import lombok.Data;

/** 动态 */
@SuppressWarnings("serial")
@Data
public class Dynamic extends BaseEntity {

    /** 图片*/
    private String image;
    /** 视频*/
    private String video;
     /** 文字*/
    private String words;
    /** 可见范围（1-所有可见，2-自己可见） */
    private String visibleScope;
    /** 分享次数 */
    private Integer share;
    /** 收藏次数 */
    private Integer collects;
    /** 点赞次数 */
    private Integer likes;
    /** 评论次数 */
    private Integer comments;


    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
