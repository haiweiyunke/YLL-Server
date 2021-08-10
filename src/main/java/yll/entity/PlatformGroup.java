package yll.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/** 平台自定义属性-中间表 */
@SuppressWarnings("serial")
@Data
public class PlatformGroup extends BaseEntity {

    /** 平台id */
    private String pid;
    /** 识别图片路径 */
    private String image;

    /** 直播日期 */
    private Date liveTime;

    /** 备注 */
    private String remark;
    /** 审核状态（0-删除，1-正常，2-禁用）*/
    private Integer state;

    /** 属性值集合 */
    private List<PlatformAttributeValue> attributes;
}
