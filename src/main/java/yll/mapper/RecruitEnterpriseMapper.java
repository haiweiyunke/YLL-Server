package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.RecruitEnterprise;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.RecruitEnterpriseVo;

import java.util.List;

/**
 * 招聘达人申请_Mapper接口
 * @author cc
 */
@Mapper
public interface RecruitEnterpriseMapper extends BasicMapper<RecruitEnterprise, String> {

    /**
     * 根据条件查询-列表
     * @param condition 查询条件
     * @return 列表
     */
    List<RecruitEnterprise> findBy(RecruitEnterprise condition);


    /**
     * 根据条件查询
     * @param condition
     * @return 流程信息
     */
    RecruitEnterprise findByCondition(RecruitEnterprise condition) ;

    /**
     * 获取列表-app
     * @param condition
     */
    List<RecruitEnterpriseVo> getAppList(RecruitEnterpriseVo condition);

    /**
     * 获取详情-app
     * @param condition
     */
    RecruitEnterpriseVo getAppDetails(RecruitEnterpriseVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);

}
