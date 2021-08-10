package yll.service.model.vo;

import lombok.Data;
import yll.entity.CustomerActivitiesAnswer;

/**
 * 我的活动竞赛结果处理类
 */
@SuppressWarnings("serial")
@Data
public class CustomerActivitiesAnswerVo extends CustomerActivitiesAnswer {

    //================返回参数======================
    /** 答题进度序号 */
    public Integer progress;

    //================查询条件======================

}
