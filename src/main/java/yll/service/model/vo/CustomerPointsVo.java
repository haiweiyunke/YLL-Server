package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerPoints;

/**
 * 积分处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerPointsVo extends CustomerPoints {
    /** 用户名 */
    private String username;
}
