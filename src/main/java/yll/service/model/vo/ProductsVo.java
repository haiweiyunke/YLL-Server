package yll.service.model.vo;

import lombok.Data;
import yll.entity.Products;

/**
 * 课件处理类
 */
@SuppressWarnings("serial")
@Data
public class ProductsVo extends Products {

    //================返回参数======================
    /** 编码名称 */
    private String codename;

    /** 编码 */
    private String code;

    /** 编码 */
    private String regionStr;

    /** 任务达人商品表—签收状态 */
    private String receive;

    /** 任务达人商品表id */
    private String tcpId;

    //================查询条件======================
   /** 是否为热门 recommend */
    private Integer  hot;
    /** 旧轮播id */
    private String oldSlideId;


}
