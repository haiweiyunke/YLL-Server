package yll.entity;

import lombok.Data;

/** 评论 */
@SuppressWarnings("serial")
@Data
public class Comment extends BaseEntity {

    /** 动态id*/
    private String dynamicId;
    /** 上级评论id，第一层为空 */
    private String commentId;


    /** 动态文字 */
    private String remark;
    /** 评论状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
