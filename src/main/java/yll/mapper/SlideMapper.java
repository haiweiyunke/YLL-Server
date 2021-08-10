package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Slide;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.SlideVo;

import java.util.List;

/**
 * 混合轮播图_Mapper接口
 * @author cc
 */
@Mapper
public interface SlideMapper extends BasicMapper<Slide, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Slide> findBy(Slide condition);

    /**
     * 查询数据
     * @param condition
     * @return 数据信息
     */
    Slide findByCondition(Slide condition) ;


    /**
     * 获取列表-app
     * @param condition
     */
    List<SlideVo> getAppList(SlideVo condition);

}
