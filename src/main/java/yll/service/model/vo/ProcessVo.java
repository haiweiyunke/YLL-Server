package yll.service.model.vo;

import lombok.Data;
import yll.entity.Process;

import java.util.List;

/**
 * 流程处理类
 */
@SuppressWarnings("serial")
@Data
public class ProcessVo extends Process {

    //================返回参数======================
    List<Process> processList;
}
