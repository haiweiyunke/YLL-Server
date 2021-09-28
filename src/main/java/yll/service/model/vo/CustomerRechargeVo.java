package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerRecharge;

/**
 * 用户充值处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerRechargeVo extends CustomerRecharge {

    //================返回参数======================
    /** 用户名 */
    private String username;

    /** 金额和 */
    private String total;

    /** 完成次数 */
    private Integer num;

    /** 下单日期与当前时间差天数 */
    private String dayNum;


}
