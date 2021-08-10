package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.RecruitCelebrity;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.RecruitCelebrityVo;

import java.util.List;

/**
 * 招聘达人简历_Mapper接口
 * @author cc
 */
@Mapper
public interface RecruitCelebrityMapper extends BasicMapper<RecruitCelebrity, String> {

    /**
     * 根据条件查询-列表
     * @param condition 查询条件
     * @return 列表
     */
    List<RecruitCelebrity> findBy(RecruitCelebrity condition);


    /**
     * 根据条件查询
     * @param condition
     * @return 流程信息
     */
    RecruitCelebrity findByCondition(RecruitCelebrity condition) ;

    /**
     * 获取列表-app
     * @param condition
     */
    List<RecruitCelebrityVo> getAppList(RecruitCelebrityVo condition);

    /**
     * 获取详情-app
     * @param condition
     */
    RecruitCelebrityVo getAppDetails(RecruitCelebrityVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);

}
