package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.TaskCelebrityContract;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TaskCelebrityContractVo;

import java.util.List;

/**
 * 任务达人合同_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskCelebrityContractMapper extends BasicMapper<TaskCelebrityContract, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskCelebrityContract> findBy(TaskCelebrityContract condition);

    /**
     * 查询动态
     * @param condition
     * @return 动态信息
     */
    TaskCelebrityContract findByCondition(TaskCelebrityContract condition) ;

    /**
     * 刪除记录
     * @param pid
     */
    void deleteByPid(String pid);

    /**
     * 获取列表 (自定义查询集合)
     * @param condition
     */
    List<TaskCelebrityContractVo> getList(TaskCelebrityContractVo condition);


    /**
     * 获取列表-app列表
     * @param getList
     */
    List<TaskCelebrityContractVo> getAppList(TaskCelebrityContractVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);
}
