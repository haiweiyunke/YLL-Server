package yll.service.model.vo;

import lombok.Data;
import yll.entity.Customer;

import java.util.Date;

/**
 * 用户处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerVo extends Customer {

    //================传递参数======================
    private Date startTime;
    private Date endTime;

    //================返回参数======================
    /** 用户积分 */
    private Integer point;
    /** 用户积分 */
    private String custBirthday;
    /** 创建时间-中文 */
    private String createdTimeStr;
}
