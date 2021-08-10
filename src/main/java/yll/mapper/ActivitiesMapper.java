package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Activities;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ActivitiesVo;

import java.util.List;

/**
 * 活动_Mapper接口
 * @author cc
 */
@Mapper
public interface ActivitiesMapper extends BasicMapper<Activities, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Activities> findBy(Activities condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesVo> findByWithType(ActivitiesVo condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesVo> getAppList(ActivitiesVo condition);

}
