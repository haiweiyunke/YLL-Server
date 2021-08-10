package yll.entity;

import lombok.Data;

/** 活动详情模块 */
@SuppressWarnings("serial")
@Data
public class ActivitiesModules extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 内容*/
    private String content;
    /** 类型(字典表获取。资讯、课件等) */
    private String type;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 备注 */
    private String remark;

}
