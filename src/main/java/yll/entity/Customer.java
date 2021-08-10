package yll.entity;

import lombok.Data;

import java.util.Date;

/** app用户表 */
@SuppressWarnings("serial")
@Data
public class Customer extends BaseEntity {

    /** 登录名(手机号) */
    private String username;
    /** 密码*/
    private String password;
    /** 支付宝登录标识open_id */
    private String aliId;
    /** 微信登录标识open_id */
    private String wechatId;
    /** 用户名 */
    private String nickname;
    /** 绑定的手机号 */
    private String phone;
    /** 头像 */
    private String headImg;
    /** 年龄 */
    private String age;

    /** 出生日期 */
    private Date birthday;
    /** 所在地（省市县英文逗号分隔） */
    private String location;
    /** 用户角色类型，注册后默认为普通用户，在认证通过后修改
     * 1-普通用户，2-达人，3-MCN，4-企业主
     * */
    private String roleType;
    /** 性别（1-女，2-男） */
    private Integer gender;

    /** 粉条号 */
    private String fenNum;
    /** 邮箱 */
    private String email;

    /** 备注 */
    private String remark;
    /** 状态（0-删除，1-正常，2-隐藏） */
    private Integer state;
    /** 支付密码 */
    private String payPassword;

    /** 分享次数 */
    private Integer share;
    /** 收藏次数 */
    private Integer collects;
    /** 点赞次数 */
    private Integer likes;
    /** 是否为轮播图（1-否，2-是） */
    private Integer slide;

    //======================非表字段========================
    /** base64明文密码（用于商城） */
    private String mp;

}
