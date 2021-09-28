package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerWallet;

/**
 * 用户钱包处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerWalletVo extends CustomerWallet {
    /** 用户名 */
    private String username;
}
