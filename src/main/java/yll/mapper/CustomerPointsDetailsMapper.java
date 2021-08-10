package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerPointsDetails;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerPointsDetailsVo;

import java.util.List;

/**
 * 积分明细_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerPointsDetailsMapper extends BasicMapper<CustomerPointsDetails, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerPointsDetails> findBy(CustomerPointsDetails condition);

    /**
     * 根据条件查询(带主表关联)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerPointsDetails> findByWithName(CustomerPointsDetails condition);

    /**
     * 根据条件查询积分和
     * @param condition 查询条件
     * @return  和
     */
    Integer findSum(CustomerPointsDetails condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    List<CustomerPointsDetailsVo> getAppList(CustomerPointsDetailsVo condition);

    /**
     * 完成次数(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    CustomerPointsDetailsVo getCompletions(CustomerPointsDetailsVo condition);

    /**
     * 批量修改(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
