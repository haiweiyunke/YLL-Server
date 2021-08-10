package yll.entity;

import lombok.Data;

import java.util.Date;

/** 任务达人直播结案 */
@SuppressWarnings("serial")
@Data
public class TaskCelebrityLive extends BaseEntity {

    /** 任务表id */
    private String tid;
    /** 任务达人表id */
    private String tcId;
    /** 商品id集合(","分隔) */
    private String pids;
    /** 商品集合中文 */
    private String productList;
    /** 提交截图(","分割) */
    private String image;
    /** 完成时间 */
    private Date completeTime;
     /** 完成场次 */
    private String sessions;

    /** 备注 */
    private String remark;
    /** 状态（1-阶段性提交，2-任务完成）*/
    private Integer state;

}
