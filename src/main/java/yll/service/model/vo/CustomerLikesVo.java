package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerLikes;

/**
 * 点赞处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerLikesVo extends CustomerLikes {

    //================返回参数======================
    /** 编码名称 */
    private String codename;
    /** 编码 */
    private String code;
    /** 编码备注 */
    private String dicmark;

    /** 头像 */
    private String headImg;
    /** 擅长领域 */
    private String expertise;
    /** 用户名 */
    private String nickname;
    /** 平台 */
    private String platform;
    /** 关注数 */
    private String collects;
    /** 点赞数 */
    private String likes;
    /** 记录所属表id */
    private String tid;

    //==============动态使用==============
    /** 图片 */
    private String image;
    /** 视频 */
    private String video;
    /** 文字 */
    private String words;

}
