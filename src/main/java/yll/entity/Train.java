package yll.entity;

import lombok.Data;

import java.util.Date;

/** 培训 */
@SuppressWarnings("serial")
@Data
public class Train extends BaseEntity {

    /** 标题 */
    private String name;
    /** 封面 */
    private String cover;
    /** 列表预览图片(","分割) */
    private String image;
    /** 视频 */
    private String video;
     /** 文件 */
    private String files;
    /** 内容 */
    private String content;
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

}
