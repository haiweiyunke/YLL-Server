package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerAuthentications;

/**
 * 企业认证类
 */
@SuppressWarnings("serial")
@Data
public class CustomerAuthenticationsVo extends CustomerAuthentications {
    /** 用户名 */
    private String username;

}
