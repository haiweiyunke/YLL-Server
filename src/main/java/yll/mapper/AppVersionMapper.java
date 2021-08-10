package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.AppVersion;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 * APP版本_Mapper接口
 * @author cc
 */
@Mapper
public interface AppVersionMapper extends BasicMapper<AppVersion, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<AppVersion> findBy(AppVersion condition);

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    AppVersion findByApp(AppVersion condition);

}
