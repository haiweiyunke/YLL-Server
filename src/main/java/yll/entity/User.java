package yll.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import yll.common.standard.Auditable;
import yll.common.standard.Idable;

/** 系统用户 */
@SuppressWarnings("serial")
@Data
public class User implements Idable, Auditable, Serializable {

    /** 主键 */
    private String id;
    /** 登录名 */
    private String account;
    /** 登录密码 */
    private String password;

    /** 所属机构 */
    private String departmentId;
    /** 所属机构名称 */
    private String departmentName;
    /** 用户姓名 */
    private String name;
    /** 备注 */
    private String remark;

    /** 是否可用 */
    private Integer enabled;
    /** 是否删除的 */
    private Integer deleted;

    /** 创建者 */
    private String createdBy;
    /** 创建时间 */
    private Date createdAt;
    /** 修改者 */
    private String updatedBy;
    /** 修改时间 */
    private Date updatedAt;
}
