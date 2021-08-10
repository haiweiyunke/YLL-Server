package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerAddresses;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerAddressesVo;

import java.util.List;

/**
 * 用户地址_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerAddressesMapper extends BasicMapper<CustomerAddresses, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerAddresses> findBy(CustomerAddresses condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerAddressesVo> getAppList(CustomerAddressesVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    CustomerAddressesVo getAppDetail(CustomerAddressesVo condition);

    /**
     * 初始化状态
     * @param condition 查询条件
     */
    void updateInit(CustomerAddresses condition);

    /**
     * 修改用户(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
