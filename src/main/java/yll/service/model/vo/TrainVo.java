package yll.service.model.vo;

import lombok.Data;
import yll.entity.Train;

/**
 * 培训处理类
 */
@SuppressWarnings("serial")
@Data
public class TrainVo extends Train {

    //================返回参数======================
    /** 编码名称 */
    private String codename;

    /** 编码名称(父类编码) */
    private String code;

    /** 点赞 0-已点，1-未点 */
    private Integer clickLikes;

    /** 收藏 0-已收，1-未收 */
    private Integer clickCollects;

    /** 分享地址 */
    private String shareUrl;


    //================查询条件======================
    /** 用户id */
    private String customerId;
    /** 旧轮播id */
    private String oldTrainId;
}
