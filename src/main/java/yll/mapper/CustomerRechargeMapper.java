package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerRecharge;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerRechargeVo;

import java.util.List;

/**
 * 用户充值_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerRechargeMapper extends BasicMapper<CustomerRecharge, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerRecharge> findBy(CustomerRecharge condition);

    /**
     * 根据条件查询(带主表关联)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerRecharge> findByWithName(CustomerRecharge condition);

    /**
     * 根据条件查询积分和
     * @param condition 查询条件
     * @return  和
     */
    Integer findSum(CustomerRecharge condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    List<CustomerRechargeVo> getAppList(CustomerRechargeVo condition);

    /**
     * 完成次数(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    CustomerRechargeVo getCompletions(CustomerRechargeVo condition);

    /**
     * 批量修改(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

    /**
     * 定时任务列表查询
     * @param condition
     */
    List<CustomerRechargeVo> findBySchedule(CustomerRechargeVo condition);

}
