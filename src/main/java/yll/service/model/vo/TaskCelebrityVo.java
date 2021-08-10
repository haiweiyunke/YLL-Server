package yll.service.model.vo;

import lombok.Data;
import yll.entity.Process;
import yll.entity.TaskCelebrity;

/**
 * 任务达人处理类
 */
@SuppressWarnings("serial")
@Data
public class TaskCelebrityVo extends TaskCelebrity {

    //================返回参数======================
    /** 达人姓名 */
    private String celebrityName;

}
