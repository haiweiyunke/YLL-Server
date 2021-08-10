package yll.service.model.vo;

import lombok.Data;
import yll.entity.ActivitiesAnswer;

/**
 * 活动竞赛答案处理类
 */
@SuppressWarnings("serial")
@Data
public class ActivitiesAnswerVo extends ActivitiesAnswer {

    //================返回参数======================
    /** 选项内容。用于题目关联查询返回时，与题目内容区分 */
    private String acontent;

    //================查询条件======================
    /** 活动ID */
    private String activityId;
    /** 正确答案标识 */
    private String answerFlag;

}
