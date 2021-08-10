package yll.entity;

import lombok.Data;

/** 流程(暂时不用) */
@SuppressWarnings("serial")
@Data
public class Process extends BaseEntity {

    /** 上级id */
    private String pid;
    /** 编码 */
    private String code;
    /** 编码名称 */
    private String name;
     /** 类型 */
    private String type;
    /** 层级 */
    private String level;
    /** 下级流程(英文逗号分隔)  */
    private String nextProcess;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

}
