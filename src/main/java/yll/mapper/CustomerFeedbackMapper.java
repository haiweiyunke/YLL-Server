package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerFeedback;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerFeedbackVo;

import java.util.List;

/**
 * 意见反馈_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerFeedbackMapper extends BasicMapper<CustomerFeedback, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerFeedback> findBy(CustomerFeedback condition);

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerFeedbackVo> findByCustomer(CustomerFeedbackVo condition);

    /**
     * 查询详情
     * @param id 查询条件
     * @return 详情
     */
    CustomerFeedbackVo getDetailByCustomer(String id);

    /**
     * 修改用户(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
