package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Customer;
import yll.entity.MallUser;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerVo;

import java.util.List;

/**
 * app用户_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerMapper extends BasicMapper<Customer, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Customer> findBy(Customer condition);

    /**
     * 查询用户
     * @param 条件
     * @return 用户信息
     */
    Customer findByCondition(Customer condition) ;

    /**
     * 列表查询（附带积分）
     * @param condition
     * @param <T>
     * @return
     */
    <T> List<T> pagedQueryWithPoints(Customer condition);

    /**
     * 列表查询（附带积分）
     * @param condition
     * @param <T>
     * @return
     */
    <T> List<T> pagedQueryWithPoints(CustomerVo condition);

    /**
     * 查询用户-app
     * @param condition 条件
     * @return 用户信息
     */
    Customer getAppDetail(Customer condition) ;

    /**
     * 根据条件查询所有昵称
     * @param condition 查询条件
     * @return 列表
     */
    List<Customer> findAllForTask(Customer condition);
}
