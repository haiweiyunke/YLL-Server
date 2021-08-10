package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerHistories;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerHistoriesVo;

import java.util.List;

/**
 * 历史记录_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerHistoriesMapper extends BasicMapper<CustomerHistories, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerHistories> findBy(CustomerHistories condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerHistories> findByWithType(CustomerHistories condition);

    /**
     * 根据条件查询(app收藏列表)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerHistoriesVo> findByWithJoin(CustomerHistoriesVo condition);

     /**
     * 根据条件查询(查询有无收藏记录)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerHistories> findById(CustomerHistories condition);

    /**
     * 删除用户数据
     * @param condition 查询条件
     */
    void deleteByCustomer(CustomerHistoriesVo condition);

    /**
     * 删除用户数据
     * @param condition 查询条件
     */
    void deleteByIdAndCustomer(CustomerHistoriesVo condition);

    /**
     * 修改用户(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
