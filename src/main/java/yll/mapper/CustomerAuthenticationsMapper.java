package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import yll.entity.CustomerAuthentications;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerAuthenticationsVo;

import java.util.List;

/**
 * 企业认证_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerAuthenticationsMapper extends BasicMapper<CustomerAuthentications, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerAuthentications> findBy(CustomerAuthentications condition);

    /**
     * 根据条件查询(带主表关联)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerAuthentications> findByWithName(CustomerAuthentications condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    CustomerAuthenticationsVo getAppDetail(CustomerAuthenticationsVo condition);

    /**
     * 修改用户(账号合并)
     * @param oldId  旧id
     * @param newId 新id
     */
    void updateMerge(@Param("oldId") String oldId, @Param("newId") String newId) ;

}
