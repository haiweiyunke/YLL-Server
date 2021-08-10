package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerActivities;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ActivitiesVo;
import yll.service.model.vo.CustomerActivitiesVo;

import java.util.List;

/**
 * 我的活动_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerActivitiesMapper extends BasicMapper<CustomerActivities, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerActivities> findBy(CustomerActivities condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<ActivitiesVo> getAppList(CustomerActivitiesVo condition);

    /**
     * 修改用户(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
