package yll.entity;

import lombok.Data;

/** 达人邀请 */
@SuppressWarnings("serial")
@Data
public class CelebrityInvite extends BaseEntity {

    /** 达人id */
    private String celebrityId;
    /** mcnid */
    private String mcnId;

    /** 备注 */
    private String remark;
    /** 用户状态（1-待处理，2-接受，3-拒绝） */
    private Integer state;

}
