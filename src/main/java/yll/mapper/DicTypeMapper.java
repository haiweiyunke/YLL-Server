package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.DicType;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 * 数据字典类别_Mapper接口
 * @author cc
 */
@Mapper
public interface DicTypeMapper extends BasicMapper<DicType, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<DicType> findBy(DicType condition);

    /**
     * 根据编码查询
     * @param code 编码
     * @return 列表
     */
    DicType getByCode(String code);

}
