package yll.entity;

import lombok.Data;

/** 商务洽谈 */
@SuppressWarnings("serial")
@Data
public class BusinessMessages extends BaseEntity {

    /** 洽谈达人 */
    private String cid;
    /** 洽谈人*/
    private String negotiator;
    /** 内容 */
    private String content;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
