package yll.service.model.vo;

import lombok.Data;
import yll.entity.Process;
import yll.entity.TaskProcess;

import java.util.List;

/**
 * 任务达人订单流程处理类
 */
@SuppressWarnings("serial")
@Data
public class TaskProcessVo extends TaskProcess {

    //================返回参数======================
    /** 流程名称 */
    private String processName;
   /** 流程编码 */
    private String processCode;

    /** 当前操作流程编码 */
    private String processCodeCurrent;

}
