package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.ActivitiesModules;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ActivitiesModulesVo;

import java.util.List;

/**
 * 活动详情模块_Mapper接口
 * @author cc
 */
@Mapper
public interface ActivitiesModulesMapper extends BasicMapper<ActivitiesModules, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesModules> findBy(ActivitiesModules condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesModules> findByWithType(ActivitiesModules condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesModulesVo> getAppList(ActivitiesModulesVo condition);

}
