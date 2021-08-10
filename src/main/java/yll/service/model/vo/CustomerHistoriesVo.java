package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerHistories;

/**
 * 历史记录处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerHistoriesVo extends CustomerHistories {

    //================返回参数======================
    /** 编码名称 */
    private String codename;
    /** 编码 */
    private String code;
    /** 编码备注 */
    private String dicmark;
    /** 封面 */
    private String cover;
    /** 视频 */
    private String video;
    /** 标题 */
    private String name;
    /** 简介 */
    private String profile;
    /** 记录所属表id */
    private String tid;

    //================传参条件======================
    /** 删除方式，1-id数组删除，2-用户id删除 */
    private Integer delType;
    /** 收藏id数组 */
    private String ids;

}
