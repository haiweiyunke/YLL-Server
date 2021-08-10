package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.PlatformAttributeKey;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 *  平台自定义属性-键表_Mapper接口
 * @author cc
 */
@Mapper
public interface PlatformAttributeKeyMapper extends BasicMapper<PlatformAttributeKey, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<PlatformAttributeKey> findBy(PlatformAttributeKey condition);

    /**
     * 查询
     * @param condition
     * @return 信息
     */
    PlatformAttributeKey findByCondition(PlatformAttributeKey condition) ;

}
