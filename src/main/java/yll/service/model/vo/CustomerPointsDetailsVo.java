package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerPointsDetails;

/**
 * 积分明细处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerPointsDetailsVo extends CustomerPointsDetails {

    //================返回参数======================
    /** 用户名 */
    private String username;

    /** 积分和 */
    private String total;

    /** 完成次数 */
    private Integer num;

}
