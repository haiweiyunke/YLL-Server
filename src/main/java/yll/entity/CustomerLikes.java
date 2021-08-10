package yll.entity;

import lombok.Data;

/** 点赞 */
@SuppressWarnings("serial")
@Data
public class CustomerLikes extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 用户id */
    private String customerId;
    /** 数据字典表获取 */
    private String dicId;
    /** 类型(字典表获取。资讯、课件等) */
    private String type;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
