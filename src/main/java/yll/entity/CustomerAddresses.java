package yll.entity;

import lombok.Data;

/** 用户地址 */
@SuppressWarnings("serial")
@Data
public class CustomerAddresses extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 姓名 */
    private String name;
    /** 电话 */
    private String phone;
    /** 省 */
    private String province;
    /** 市 */
    private String city;
    /** 区域 */
    private String district;
    /** 详细地址 */
    private String detailed;
    /** 标签(字典表获取) */
    private String type;
    /** 状态（0-删除，1-正常, 2-默认） */
    private Integer state;
    /** 备注 */
    private String remark;

}
