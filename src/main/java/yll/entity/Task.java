package yll.entity;

import lombok.Data;

import java.util.Date;

/** 任务 */
@SuppressWarnings("serial")
@Data
public class Task extends BaseEntity {

    /** 任务名称 */
    private String taskName;
    /** 有效开始日期 */
    private Date validStartTime;
    /** 有效结束日期 */
    private Date validEndTime;
    /** 推广开始日期 */
    private Date marketingStartTime;
    /** 推广结束日期 */
    private Date marketingEndTime;
    /** 带货方式（字典表获取） */
    private String type;
     /** 主体要求(字典表获取，mcn机构、自由主播）-不用*/
    private String sponsor;
     /** 达人领域（字典表获取，逗号分隔） */
    private String expertise;
     /** 带货平台（字典表获取，逗号分隔）  */
    private String platform;
     /** 任务介绍 */
    private String description;
     /** 商品图片/任务列表 */
    private String productImages;
     /** 广告 */
    private String advertisement;
     /** 卖点 */
    private String sellingPoint;
     /** 任务封面 */
    private String cover;
     /** 经营许可 */
    private String businessLicense;
     /** 所属类目（字典表获取） */
    private String category;
     /** 宝贝链接 */
    private String productLink;
     /** 零售价 */
    private String retailPrice;
     /** 优惠方式（字典表获取） */
    private String discount;
     /** 优惠值（配合优惠方式使用） */
    private String discountNum;
     /** 供货数量 */
    private String quantity;
     /** 达人佣金比例 */
    private String commissionRatio;
     /** 是否退样（1-否，2-是） */
    private Integer giveBack;
     /** 产品发布会（1-不需要，2-需要） */
    private Integer conferences;
     /** 保底出场费（字典表获取） */
    private String appearanceFees;
     /** 产品相关许可证 */
    private String productLicense;
     /** 合格证 */
    private String certificate;
     /** 质检证书 */
    private String qualityCertificate;

    /** 是否为轮播图（0-否，1-是）*/
    private Integer slide;
    /** 状态（1-可见，2-不可见）*/
    private Integer visible;
    /** 是否为推广（0-否，1-是） */
    private Integer extension;
    /** 推广排序 */
    private Integer extensionOrdinal;

    /** 备注 */
    private String remark;
    /** 状态（1-待审核，2-已通过，3-未通过，4-已完成） 查询已完成时，连带状态3一并查询*/
    private Integer state;

     /** 联系方式 */
    private String phone;
     /** 服务价格 */
    private String servicePrice;
     /** 任务场地 */
    private String taskPlace;
     /** 任务订单数。申请单或邀请单处理时要及时更新此项数据 */
    private Integer orderNum;
     /** 直播渠道 */
    private String livePlatform;
     /** 样品押金 */
    private String deposit;
    /** 当前任务状态 */
    private String currentState;
    /** 商品集合中文 */
    private String productList;

}
