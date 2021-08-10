package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.RecruitCelebrityApply;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.RecruitCelebrityApplyVo;

import java.util.List;

/**
 * 招聘达人申请_Mapper接口      TODO  继续搞这个，然后搞企业主招聘两个类
 * @author cc
 */
@Mapper
public interface RecruitCelebrityApplyMapper extends BasicMapper<RecruitCelebrityApply, String> {

    /**
     * 根据条件查询-列表
     * @param condition 查询条件
     * @return 列表
     */
    List<RecruitCelebrityApply> findBy(RecruitCelebrityApply condition);


    /**
     * 根据条件查询
     * @param condition
     * @return 流程信息
     */
    RecruitCelebrityApply findByCondition(RecruitCelebrityApply condition) ;

    /**
     * 获取列表-app
     * @param condition
     */
    List<RecruitCelebrityApplyVo> getAppList(RecruitCelebrityApplyVo condition);

    /**
     * 招聘信息-申请人列表-app
     * @param condition
     */
    List<RecruitCelebrityApplyVo> getCelebrityApplyList(RecruitCelebrityApplyVo condition);

    /**
     * 获取详情-app
     * @param condition
     */
    RecruitCelebrityApplyVo getAppDetails(RecruitCelebrityApplyVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);

}
