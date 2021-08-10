package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Enterprise;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.EnterpriseVo;
import yll.service.model.vo.McnVo;

import java.util.List;

/**
 * 企业主_Mapper接口
 * @author cc
 */
@Mapper
public interface EnterpriseMapper extends BasicMapper<Enterprise, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Enterprise> findBy(Enterprise condition);

    /**
     * 查询企业主
     * @param condition
     * @return 企业主信息
     */
    Enterprise findByCondition(Enterprise condition) ;

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);

    /**
     * 获取详情-app认证详情
     * @param condition
     */
    EnterpriseVo getAppAuth(Enterprise condition);

    /**
     * 根据条件查询-后台使用
     * @param condition 查询条件
     * @return 列表
     */
    List<EnterpriseVo> findByAdminList(EnterpriseVo condition);

    /**
     * 根据条件查询(shop编辑页使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<EnterpriseVo> find4Shop(Enterprise condition);

}
