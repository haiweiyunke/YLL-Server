package yll.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import yll.common.standard.Auditable;
import yll.common.standard.Idable;

/** 角色权限关联 */
@SuppressWarnings("serial")
@Data
public class RolePermission implements Idable, Auditable, Serializable {

    /** 主键 */
    private String id;
    /** 角色组ID */
    private String roleId;
    /** 功能ID */
    private String permissionId;

    /** 创建者 */
    private String createdBy;
    /** 创建时间 */
    private Date createdAt;
    /** 修改者 */
    private String updatedBy;
    /** 修改时间 */
    private Date updatedAt;
}
