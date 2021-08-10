package yll.entity;

import lombok.Data;

import java.util.Date;

/** 活动 */
@SuppressWarnings("serial")
@Data
public class Activities extends BaseEntity {

    /** 标题 */
    private String name;
    /** 封面 */
    private String cover;
    /** 列表预览图片(","分割) */
    private String image;
    /** 视频 */
    private String video;
    /** 备注 */
    private String remark;
    /** 类型(字典表获取) */
    private String type;
    /** 状态（0-删除，1-正常） */
    private Integer state;
    /** 起始时间 */
    private Date startTime;
    /** 截止时间 */
    private Date endTime;
    /** 真实参与人数 */
    private Integer realNum;
    /** 修改参与人数 */
    private Integer operateNum;
}
