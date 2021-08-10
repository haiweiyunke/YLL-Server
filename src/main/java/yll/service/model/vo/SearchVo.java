package yll.service.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 搜索处理类
 */
@SuppressWarnings("serial")
@Data
public class SearchVo implements Serializable {

    //================返回参数======================
    /** id */
    private String id;
    /** 名称|标题 */
    private String name;
    /** 简介*/
    private String profile;
    /** 封面 */
    private String cover;
    /** 图片 */
    private String image;
    /** 视频*/
    private String video;
    /** 零售价-产品使用 */
    private Double price;
    /** 编码名称 */
    private String codename;
    /** 父类编码 */
    private String code;
    /** 子类编码 */
    private String type;
    /** 名称 */
    private String author;
    /** 地区 */
    private String area;
    /** 创建时间 */
    private String appCreatedTime;
    /** 文件路径 */
    private String files;

}
