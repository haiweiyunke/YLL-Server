package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.ActivitiesQuestions;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ActivitiesQuestionsVo;

import java.util.List;

/**
 * 活动竞赛题目_Mapper接口
 * @author cc
 */
@Mapper
public interface ActivitiesQuestionsMapper extends BasicMapper<ActivitiesQuestions, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesQuestions> findBy(ActivitiesQuestions condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesQuestionsVo> findByWithType(ActivitiesQuestionsVo condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesQuestionsVo> getAppList(ActivitiesQuestionsVo condition);

}
