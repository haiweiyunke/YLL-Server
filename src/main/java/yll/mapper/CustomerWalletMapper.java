package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.CustomerWallet;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 * 用户钱包_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerWalletMapper extends BasicMapper<CustomerWallet, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerWallet> findBy(CustomerWallet condition);

    /**
     * 根据条件查询(带主表关联)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerWallet> findByWithName(CustomerWallet condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    CustomerWallet getAppDetail(CustomerWallet condition);

}
