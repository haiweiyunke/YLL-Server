package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerFeedback;

/**
 * 意见反馈处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerFeedbackVo extends CustomerFeedback {

    //================返回参数======================
    /** 用户名 */
    private String username;
    /** 手机号码 */
    private String phone;

}
