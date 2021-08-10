package yll.entity;

import lombok.Data;
import yll.common.standard.Idable;

import java.io.Serializable;

/** 行政区划 */
@SuppressWarnings("serial")
@Data
public class Area implements Idable,  Serializable {

    /** id */
    private String id;
    /** 上级编码 */
    private Integer pid;
    /** 编码节点 */
    private String node;
    /** 名称 */
    private String name;
    /** 级别 */
    private Integer level;
    /** 经度 */
    private Double lat;
    /** 纬度 */
    private Double lng;

}
