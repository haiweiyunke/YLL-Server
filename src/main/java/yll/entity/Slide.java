package yll.entity;

import lombok.Data;

/**  混合轮播图 */
@SuppressWarnings("serial")
@Data
public class Slide extends BaseEntity {

  /**  键 */
  private String targetId;
  /** 值 */
  private String name;
  /** 类型 */
  private String img;
  /** 排序 */
  private String type;
  /** 备注 */
  private String remark;
  /** 数据状态（0-删除，1-正常，2-禁用） */
  private Integer state;

}
