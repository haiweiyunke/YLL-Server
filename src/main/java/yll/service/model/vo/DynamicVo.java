package yll.service.model.vo;

import lombok.Data;
import yll.entity.Dynamic;

/**
 * DH
 * 动态
 */
@SuppressWarnings("serial")
@Data
public class DynamicVo extends Dynamic {

    //================返回参数======================
    private String appCreatedTime;
}
