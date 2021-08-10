package yll.service.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 队列信息
 */
@SuppressWarnings("serial")
@Data
public class ZQueueInfo implements Serializable {
    /** 队列 */
    private String name;
    /** 长度 */
    private Integer size;
}
