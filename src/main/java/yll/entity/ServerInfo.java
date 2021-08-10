package yll.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import yll.common.standard.Auditable;
import yll.common.standard.Idable;

/** 服务器信息 */
@SuppressWarnings("serial")
@Data
public class ServerInfo implements Idable, Auditable, Serializable {

    /** 主键 */
    private String id;
    /** MAC地址 */
    private String mac;
    /** 主机IP地址 */
    private String host;
    /** 序号 */
    private Integer sequence;

    /** 创建者 */
    private String createdBy;
    /** 创建时间 */
    private Date createdAt;
    /** 修改者 */
    private String updatedBy;
    /** 修改时间 */
    private Date updatedAt;
}
