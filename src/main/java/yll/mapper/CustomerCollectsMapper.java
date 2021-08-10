package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.Customer;
import yll.entity.CustomerCollects;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerCollectsVo;

import java.util.List;

/**
 * 我的收藏_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerCollectsMapper extends BasicMapper<CustomerCollects, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerCollects> findBy(CustomerCollects condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerCollects> findByWithType(CustomerCollects condition);

    /**
     * 根据条件查询(app收藏列表)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerCollectsVo> findByWithJoin(CustomerCollectsVo condition);

    /**
     * 根据条件查询(app收藏列表-客户规定列表展示详情页)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerCollectsVo> findByWithJoinXG(CustomerCollectsVo condition);


     /**
     * 根据条件查询(查询有无收藏记录)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerCollects> findById(CustomerCollects condition);

    /**
     * 删除用户数据
     * @param condition 查询条件
     */
    void deleteByCustomer(CustomerCollectsVo condition);

    /**
     * 删除指定已关注数据
     * @param condition 查询条件
     */
    void deleteByTargetId(CustomerCollectsVo condition);

    /**
     * 删除用户数据
     * @param condition 查询条件
     */
    void deleteByIdAndCustomer(CustomerCollectsVo condition);

    /**
     * 修改用户(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
