package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.RecruitEnterpriseDetails;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.RecruitEnterpriseDetailsVo;

import java.util.List;

/**
 * 招聘达人申请_Mapper接口
 * @author cc
 */
@Mapper
public interface RecruitEnterpriseDetailsMapper extends BasicMapper<RecruitEnterpriseDetails, String> {

    /**
     * 根据条件查询-列表
     * @param condition 查询条件
     * @return 列表
     */
    List<RecruitEnterpriseDetails> findBy(RecruitEnterpriseDetails condition);


    /**
     * 根据条件查询
     * @param condition
     * @return 流程信息
     */
    RecruitEnterpriseDetails findByCondition(RecruitEnterpriseDetails condition) ;

    /**
     * 获取列表-app
     * @param condition
     */
    List<RecruitEnterpriseDetailsVo> getAppList(RecruitEnterpriseDetailsVo condition);

    /**
     * 获取列表-招聘广场-app
     * @param condition
     */
    List<RecruitEnterpriseDetailsVo> getIndexAppList(RecruitEnterpriseDetailsVo condition);

    /**
     * 获取达人申请列表-app
     * @param condition
     */
    List<RecruitEnterpriseDetailsVo> getAppCelebrityApplyList(RecruitEnterpriseDetailsVo condition);

    /**
     * 获取详情-app
     * @param condition
     */
    RecruitEnterpriseDetailsVo getAppDetails(RecruitEnterpriseDetailsVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);


    /**
     * 定时任务列表查询
     * @param condition
     */
    List<RecruitEnterpriseDetailsVo> findBySchedule(RecruitEnterpriseDetailsVo condition);

}
