package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.PlatformGroup;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.PlatformGroupVo;

import java.util.List;

/**
 *  平台自定义属性-中间表_Mapper接口
 * @author cc
 */
@Mapper
public interface PlatformGroupMapper extends BasicMapper<PlatformGroup, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<PlatformGroup> findBy(PlatformGroup condition);

    /**
     * 查询
     * @param condition
     * @return 信息
     */
    PlatformGroup findByCondition(PlatformGroup condition) ;

}
