package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Information;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.InformationVo;

import java.util.List;

/**
 * 资讯_Mapper接口
 * @author cc
 */
@Mapper
public interface InformationMapper extends BasicMapper<Information, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Information> findBy(Information condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<Information> findByWithType(Information condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<InformationVo> getAppList(InformationVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    InformationVo getAppDetail(InformationVo condition);

    /**
     * 根据条件查询(slide为0的数据=非轮播图)
     * @param condition 查询条件
     * @return 列表
     */
    List<Information> findBySlide(Information condition);
}
