package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.CustomerPoints;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 * 积分_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerPointsMapper extends BasicMapper<CustomerPoints, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerPoints> findBy(CustomerPoints condition);

    /**
     * 根据条件查询(带主表关联)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerPoints> findByWithName(CustomerPoints condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    CustomerPoints getAppDetail(CustomerPoints condition);

}
