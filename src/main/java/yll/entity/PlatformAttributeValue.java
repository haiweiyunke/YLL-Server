package yll.entity;

import lombok.Data;

import java.util.Date;

/** 平台自定义属性-值表 */
@SuppressWarnings("serial")
@Data
public class PlatformAttributeValue extends BaseEntity {

    /** 值 */
    private String name;

    /** 平台类型 */
    private String type;

    /** 键id */
    private String kid;

    /** 平台id */
    private String pid;

    /** 用户id */
    private String cid;

    /** 组id */
    private String gid;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

    /** 键值 */
    private String kname;

    /** 直播日期 */
    private Date liveTime;
}
