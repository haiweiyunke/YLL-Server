package yll.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import yll.entity.OpLog;
import yll.mapper.basic.BasicMapper;

/**
 * 操作日志_Mapper接口
 */
@Mapper
public interface OpLogMapper extends BasicMapper<OpLog, String> {

    /**
     * 批量插入记录
     * @param record 记录
     */
    void inserts(OpLog[] record);

    /**
     * 用于分页查询
     * @param condition 查询条件
     * @return 查询结果
     */
    List<OpLog> findBy(OpLog condition);

}
