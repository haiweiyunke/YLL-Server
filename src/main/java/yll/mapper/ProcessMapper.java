package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Process;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ProcessVo;

import java.util.List;

/**
 * 流程_Mapper接口
 * @author cc
 */
@Mapper
public interface ProcessMapper extends BasicMapper<Process, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Process> findBy(Process condition);

    /**
     * 查询动态
     * @param condition
     * @return 动态信息
     */
    Process findByCondition(Process condition) ;

    /**
     * 刪除记录
     * @param pid
     */
    void deleteByPid(String pid);

    /**
     * 获取列表 (包含子集集合)
     * @param condition
     */
    List<ProcessVo> getList(ProcessVo condition);


    /**
     * 获取列表-app列表
     * @param getList
     */
    List<ProcessVo> getAppList(ProcessVo condition);


}
