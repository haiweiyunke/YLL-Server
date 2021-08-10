package yll.service.model.vo;

import lombok.Data;

/**
 * 流程操作处理类
 */
@SuppressWarnings("serial")
@Data
public class OperationVo{

    //================返回参数======================


    //================查询条件======================
    /** 任务表id */
    private String tid;

    /** 任务达人表id */
    private String tcId;

    /** 编码 */
    private String code;


}
