package yll.entity;

import lombok.Data;
import yll.common.standard.CommonAttribute;
import yll.common.standard.Idable;

import java.io.Serializable;
import java.util.Date;

/** 业务基础类 */
@SuppressWarnings("serial")
@Data
public class BaseEntity implements Idable, CommonAttribute, Serializable {

    /** 主键 */
    private String id;
    /** 排序 */
    private Integer ordinal;
    /** 是否可用 */
    private Integer enabled;
    /** 删除标记 */
    private Integer deleted;

    /** 创建者 */
    private String creator;
    /** 创建时间 */
    private Date createdTime;
    /** 修改者 */
    private String modifier;
    /** 修改时间 */
    private Date modifiedTime;
    /** 删除者 */
    private String deleter;
    /** 删除时间 */
    private Date deletedTime;

    //================非表结构参数参数======================
    /** 创建时间 */
    private String appCreatedTime;

}
