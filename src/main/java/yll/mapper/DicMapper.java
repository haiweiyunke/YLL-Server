package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Dic;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 * 数据字典_Mapper接口
 * @author cc
 */
@Mapper
public interface DicMapper extends BasicMapper<Dic, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Dic> findBy(Dic condition);

    /**
     * 根据编码查询
     * @param code 编码
     * @return 列表
     */
    Dic getByCode(String code);

    /**
     * 查询所有(App使用)
     * @param code 编码
     * @return 列表
     */
    List<Dic> findByTargetId(String code);

}
