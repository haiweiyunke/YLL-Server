package yll.entity;

import lombok.Data;

/** 我的消息 */
@SuppressWarnings("serial")
@Data
public class CustomerMessages extends BaseEntity {

    /** 名称 */
    private String name;
    /** 简介*/
    private String profile;
    /** 内容 */
    private String content;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
