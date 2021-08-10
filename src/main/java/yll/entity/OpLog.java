package yll.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import yll.common.standard.Auditable;
import yll.common.standard.Idable;

/**
 * 操作日志
 */
@SuppressWarnings("serial")
@Data
public class OpLog implements Idable, Auditable, Serializable {

    /** 主键 */
    private String id;
    /** 访问地址 */
    private String url;
    /** 描述 */
    private String comment;

    /** 用户ID */
    private String userId;
    /** 用户姓名 */
    private String userName;
    /** 访问IP */
    private String userIp;

    /** 创建者 */
    private String createdBy;
    /** 创建时间 */
    private Date createdAt;
    /** 修改者 */
    private String updatedBy;
    /** 修改时间 */
    private Date updatedAt;

    // #Extend(1)
    // #Extend(2）
    /** 条件:创建时间 */
    private Date createdAtBegin;
    /** 条件:创建时间 */
    private Date createdAtEnd;
}
