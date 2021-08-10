package yll.service.model.vo;

import lombok.Data;
import yll.entity.ActivitiesQuestions;

import java.util.List;

/**
 * 活动竞赛题目处理类
 */
@SuppressWarnings("serial")
@Data
public class ActivitiesQuestionsVo extends ActivitiesQuestions {

    //================返回参数======================
    /** 编码名称 */
    private String codename;

    /** 活动名称 */
    private String activityName;

    /** 答案集合 */
    private List<ActivitiesAnswerVo> answerList;

    //================查询条件======================
    /** 活动ID */
    private String activityId;

}
