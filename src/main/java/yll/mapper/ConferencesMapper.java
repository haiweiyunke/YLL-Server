package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Conferences;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ConferencesVo;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.TrainVo;

import java.util.List;

/**
 * 发布会_Mapper接口
 * @author cc
 */
@Mapper
public interface ConferencesMapper extends BasicMapper<Conferences, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Conferences> findBy(Conferences condition);

    /**
     * 查询发布会
     * @param condition
     * @return 发布会信息
     */
    Conferences findByCondition(Conferences condition) ;

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);

    /**
     * 根据条件查询-App使用
     * @param condition 查询条件
     * @return 列表
     */
    List<ConferencesVo> getAppList(ConferencesVo condition);

    /**
     * 根据条件查询(slide为0的数据=非轮播图)
     * @param condition 查询条件
     * @return 列表
     */
    List<ConferencesVo> findBySlide(ConferencesVo condition);

    /**
     * 根据条件查询(extension为0的数据=非推广)
     * @param condition 查询条件
     * @return 列表
     */
    List<ConferencesVo> findByExtension(ConferencesVo condition);

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<ConferencesVo> findByAdminList(ConferencesVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    ConferencesVo getAppDetail(ConferencesVo condition);

}
