package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.SearchVo;

import java.util.List;

/**
 * 统一查询_Mapper接口
 * @author cc
 */
@Mapper
public interface SearchMapper extends BasicMapper<SearchVo, String> {


    /**
     * 根据条件查询(学习版块)
     * @param condition 查询条件
     * @return 列表
     */
    List<SearchVo> getStudyAppList(SearchVo condition);

    /**
     * 根据条件查询(专区版块)
     * @param condition 查询条件
     * @return 列表
     */
    List<SearchVo> getZoneAppList(SearchVo condition);

}
