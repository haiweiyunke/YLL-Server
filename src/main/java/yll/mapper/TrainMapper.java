package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Train;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TrainVo;

import java.util.List;

/**
 * 培训_Mapper接口
 * @author cc
 */
@Mapper
public interface TrainMapper extends BasicMapper<Train, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Train> findBy(Train condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<Train> findByWithType(Train condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<TrainVo> getAppList(TrainVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    TrainVo getAppDetail(TrainVo condition);

    /**
     * 根据条件查询(slide为0的数据=非轮播图)
     * @param condition 查询条件
     * @return 列表
     */
    List<Train> findBySlide(Train condition);
}
