package yll.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import yll.common.standard.Auditable;
import yll.common.standard.Idable;

/** 系统角色 */
@SuppressWarnings("serial")
@Data
public class Role implements Idable, Auditable, Serializable {

    /** 主键 */
    private String id;
    /** 名称 */
    private String name;
    /** 备注 */
    private String remark;


    /** 创建者 */
    private String createdBy;
    /** 创建时间 */
    private Date createdAt;
    /** 修改者 */
    private String updatedBy;
    /** 修改时间 */
    private Date updatedAt;
}
