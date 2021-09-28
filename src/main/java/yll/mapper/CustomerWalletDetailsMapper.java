package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerWalletDetails;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerWalletDetailsVo;

import java.util.List;

/**
 * 钱包订单流水_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerWalletDetailsMapper extends BasicMapper<CustomerWalletDetails, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerWalletDetails> findBy(CustomerWalletDetails condition);

    /**
     * 根据条件查询(带主表关联)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerWalletDetails> findByWithName(CustomerWalletDetails condition);

    /**
     * 根据条件查询积分和
     * @param condition 查询条件
     * @return  和
     */
    Integer findSum(CustomerWalletDetails condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    List<CustomerWalletDetailsVo> getAppList(CustomerWalletDetailsVo condition);

    /**
     * 完成次数(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    CustomerWalletDetailsVo getCompletions(CustomerWalletDetailsVo condition);

    /**
     * 批量修改(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
