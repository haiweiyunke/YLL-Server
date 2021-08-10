package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerAddresses;

/**
 * 用户地址处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerAddressesVo extends CustomerAddresses {

    //================返回参数======================
    /** 编码名称 */
    private String codename;
    /** 省名称 */
    private String provinceName;
    /** 市名称 */
    private String cityName;
    /** 区/县名称 */
    private String districtName;

}
