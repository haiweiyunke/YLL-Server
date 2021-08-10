package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerLikes;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerLikesVo;

import java.util.List;

/**
 * 我的点赞Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerLikesMapper extends BasicMapper<CustomerLikes, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerLikes> findBy(CustomerLikes condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerLikes> findByWithType(CustomerLikes condition);

    /**
     * 根据条件查询(app点赞列表)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerLikesVo> findByWithJoin(CustomerLikes condition);

     /**
     * 根据条件查询(查询有无收藏记录)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerLikes> findById(CustomerLikes condition);

    /**
     * 批量修改(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
