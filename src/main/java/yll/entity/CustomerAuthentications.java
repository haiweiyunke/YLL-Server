package yll.entity;

import lombok.Data;

/** 认证 */
@SuppressWarnings("serial")
@Data
public class CustomerAuthentications extends BaseEntity {

    /** 关联id */
    private String targetId;
    /** 许可证号码 */
    private String licences;
    /** 许可证照片 */
    private String licencesImg;
    /** 身份证姓名 */
    private String idName;
    /** 身份证号 */
    private String idCard;
    /** 身份证照片 */
    private String idImg;
    /** 许可证手机号，即用户的绑定手机号 */
    private String phone;
    /** 状态（0-未通过，1-待审核， 2-已通过） */
    private Integer state;
    /** 备注 */
    private String remark;

}
