package yll.entity;

import java.io.Serializable;
import java.util.Date;

import com.github.relucent.base.util.tree.IdPathable;

import lombok.Data;
import yll.common.standard.Auditable;
import yll.common.standard.Idable;
import yll.common.standard.Ordinal;

/**
 * 组织机构
 * @author _yyl
 */
@SuppressWarnings("serial")
@Data
public class Department implements Idable, Auditable, IdPathable, Ordinal, Serializable {

    /** 主键 */
    private String id;
    /** 上级功能权限ID */
    private String parentId;

    /** 名称 */
    private String name;
    /** 备注 */
    private String remark;

    /** 排序 */
    private String ordinal;
    /** 是否删除的 */
    private Integer deleted;

    /** ID路径 */
    private String idPath;

    /** 创建者 */
    private String createdBy;
    /** 创建时间 */
    private Date createdAt;
    /** 修改者 */
    private String updatedBy;
    /** 修改时间 */
    private Date updatedAt;

    /** ~上级功能权限名称 */
    private transient String parentName;
}
