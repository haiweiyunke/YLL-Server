package yll.service.model.vo;

import lombok.Data;
import yll.entity.TaskCelebrityLive;
import yll.entity.TaskProcess;

/**
 * 任务达人直播结案处理类
 */
@SuppressWarnings("serial")
@Data
public class TaskCelebrityLiveVo extends TaskCelebrityLive {

    //================返回参数======================
    /**  商品-接收（字串","隔开） */
    private String productsIn;
    /**  完成时间 */
    private String completeTimeStr;

}
