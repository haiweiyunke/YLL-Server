package yll.service.model.vo;

import lombok.Data;
import yll.entity.Platform;
import yll.entity.PlatformAttributeValue;

import java.util.List;

/**
 * DH
 * 达人平台
 */
@SuppressWarnings("serial")
@Data
public class PlatformVo extends Platform {

    //================返回参数======================
    private  String codename;
    private  String fansStr;

    //================自定义参数====================
    /** 属性值集合 */
    private List<PlatformAttributeValue> attributes;
    private  String platformTypeStr;
    private  String attributesStr;
    /** 识别图片路径 */
    private  String image;
}
