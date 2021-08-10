package yll.service.model.vo;

import lombok.Data;
import yll.entity.Area;

import java.util.List;

/**
 * 行政区域处理类
 */
@SuppressWarnings("serial")
@Data
public class AreaVo extends Area {

    //================返回参数======================
    /** 下级区划集合 */
    private List<AreaVo> list;

}
