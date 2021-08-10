package yll.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import yll.entity.User;
import yll.mapper.basic.BasicMapper;

/**
 * 系统用户_Mapper接口
 * @author _yyl
 */
@Mapper
public interface UserMapper extends BasicMapper<User, String> {

    /**
     * 根据账号查询用户
     * @param account 账号
     * @return 用户信息
     */
    User getByAccount(String account);

    /**
     * 根据条件查询用户
     * @param condition 查询条件
     * @return 用户列表
     */
    List<User> findBy(User condition);
}
