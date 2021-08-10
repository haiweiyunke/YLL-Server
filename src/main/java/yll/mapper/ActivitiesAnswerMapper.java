package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.ActivitiesAnswer;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ActivitiesAnswerVo;

import java.util.List;

/**
 * 活动竞赛答案_Mapper接口
 * @author cc
 */
@Mapper
public interface ActivitiesAnswerMapper extends BasicMapper<ActivitiesAnswer, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesAnswer> findBy(ActivitiesAnswer condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesAnswerVo> findByWithType(ActivitiesAnswerVo condition);

    /**
     * 根据条件删除
     * @param condition 查询条件
     * @return 列表
     */
    void deleteByCondition(ActivitiesAnswerVo condition);

}
