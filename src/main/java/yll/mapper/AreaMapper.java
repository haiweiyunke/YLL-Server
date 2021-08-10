package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Area;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 * 行政区划_Mapper接口
 * @author cc
 */
@Mapper
public interface AreaMapper extends BasicMapper<Area, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Area> findBy(Area condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<Area> getAppList(Area condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<Area> getAppList2(Area condition);

}
