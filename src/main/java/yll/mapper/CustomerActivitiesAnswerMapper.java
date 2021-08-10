package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerActivitiesAnswer;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerActivitiesAnswerVo;

import java.util.List;

/**
 * 我的活动竞赛结果_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerActivitiesAnswerMapper extends BasicMapper<CustomerActivitiesAnswer, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerActivitiesAnswer> findBy(CustomerActivitiesAnswer condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    CustomerActivitiesAnswerVo getAppDetail(CustomerActivitiesAnswerVo condition);

    /**
     * 修改用户(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
