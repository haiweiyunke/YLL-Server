package yll.service.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 修改密码
 */
@SuppressWarnings("serial")
@Data
public class PasswordDto implements Serializable {
    private String oldPassword;
    private String newPassword;
    /** base64明文密码（用于商城） */
    private String mp;
}
