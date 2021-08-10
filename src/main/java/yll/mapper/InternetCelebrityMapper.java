package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Information;
import yll.entity.InternetCelebrity;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ConferencesVo;
import yll.service.model.vo.InternetCelebrityVo;

import java.util.List;

/**
 * 网红（达人）信息_Mapper接口
 * @author cc
 */
@Mapper
public interface InternetCelebrityMapper extends BasicMapper<InternetCelebrity, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<InternetCelebrity> findBy(InternetCelebrity condition);

    /**
     * 查询网红
     * @param condition
     * @return 网红信息
     */
    InternetCelebrity findByCondition(InternetCelebrity condition) ;

    /**
     * 查询网红
     * @param condition
     * @return 网红信息
     */
    InternetCelebrityVo findByConditionVo(InternetCelebrity condition) ;

    /**
     * 列表查询（附带积分）
     * @param condition
     * @param <T>
     * @return
     */
    <T> List<T> pagedQueryWithPoints(InternetCelebrity condition);

    /**
     * app列表
     * @param condition 查询条件
     * @return 列表
     */
    List<InternetCelebrityVo> getAppList(InternetCelebrityVo condition);

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);

    /**
     * 获取详情-app
     * @param condition
     */
    InternetCelebrityVo getAppDetail(InternetCelebrityVo condition);


    /**
     * 获取详情-app认证详情
     * @param condition
     */
    InternetCelebrityVo getAppAuth(InternetCelebrityVo condition);


    /**
     * 获取详情-app邀请列表
     * @param condition
     */
    List<InternetCelebrityVo> getAppInviteSearch(InternetCelebrityVo condition);

    /**
     * 根据条件查询(slide为0的数据=非轮播图)
     * @param condition 查询条件
     * @return 列表
     */
    List<InternetCelebrityVo> findBySlide(InternetCelebrityVo condition);

        /**
     * 根据条件查询(hot为0的数据=非热门)
     * @param condition 查询条件
     * @return 列表
     */
    List<InternetCelebrityVo> findByHot(InternetCelebrityVo condition);

    /**
     * 根据条件查询-后台使用
     * @param condition 查询条件
     * @return 列表
     */
    List<InternetCelebrityVo> findByAdminList(InternetCelebrityVo condition);

}
