package yll.service.model.vo;

import lombok.Data;
import yll.entity.Shops;

/**
 * 课件处理类
 */
@SuppressWarnings("serial")
@Data
public class ShopsVo extends Shops {

    //================返回参数======================
    /** 编码名称 */
    private String codename;

    /** 编码 */
    private String code;

    //================查询条件======================
   /** 是否为热门 recommend */
    private Integer  hot;
    /** 旧轮播id */
    private String oldSlideId;


}
