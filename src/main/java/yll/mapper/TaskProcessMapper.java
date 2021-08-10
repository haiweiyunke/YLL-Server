package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.TaskProcess;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TaskProcessVo;

import java.util.List;

/**
 * 任务达人订单流程_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskProcessMapper extends BasicMapper<TaskProcess, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskProcess> findBy(TaskProcess condition);

    /**
     * 查询流程
     * @param condition
     * @return 流程信息
     */
    TaskProcess findByCondition(TaskProcess condition) ;

    /**
     * 刪除记录
     * @param pid
     */
    void deleteByPid(String pid);


    /**
     * 获取列表 (自定义查询集合)
     * @param condition
     */
    List<TaskProcessVo> getList(TaskProcessVo condition);


    /**
     * 获取列表-app列表
     * @param getList
     */
    List<TaskProcessVo> getAppList(TaskProcessVo condition);


    /**
     * 查询最新流程
     * @param condition
     * @return 流程信息
     */
    TaskProcessVo findByCurrent(TaskProcess condition) ;

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);
}
