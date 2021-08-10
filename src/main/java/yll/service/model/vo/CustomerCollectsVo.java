package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerCollects;

import java.util.List;

/**
 * 收藏处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerCollectsVo extends CustomerCollects {

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
    /** 关注数 */
    private String follows;
    /** 记录所属表id */
    private String tid;

    //================传参条件======================
    /** 删除方式，1-id数组删除，2-用户id删除 */
    private Integer delType;
    /** 收藏id数组 */
    private String ids;

    //================达人返回参数======================
    private String platformCode;
    private String platformImg;
    private String birthday;
    private String mcnName;
    private String height;
    private String location;
    private String fans;
    private String type;

    private String fenNum;

    private List<PlatformVo>  platformList;

}
