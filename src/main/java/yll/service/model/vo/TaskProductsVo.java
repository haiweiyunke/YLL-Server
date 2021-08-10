package yll.service.model.vo;

import lombok.Data;
import yll.entity.TaskCelebrity;
import yll.entity.TaskProducts;

/**
 * 任务商品处理类
 */
@SuppressWarnings("serial")
@Data
public class TaskProductsVo extends TaskProducts {

    //================返回参数======================
    /** 产品名称 */
    private String name;
    /** 封面 */
    private String cover;
    /** 优惠价 */
    private String discountPrice;
    /** 佣金 */
    private String commission;
    /** 押金 */
    private String deposit;
    /** 带货参数-佣金比 */
    private String depositRatio;
}
