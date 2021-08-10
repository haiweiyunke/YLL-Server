package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.TaskCelebrityLive;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TaskCelebrityLiveVo;

import java.util.List;

/**
 * 任务达人直播结案_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskCelebrityLiveMapper extends BasicMapper<TaskCelebrityLive, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskCelebrityLive> findBy(TaskCelebrityLive condition);


    /**
     * 查询流程
     * @param condition
     * @return 流程信息
     */
    TaskCelebrityLive findByCondition(TaskCelebrityLive condition) ;


    /**
     * 获取列表-app列表
     * @param condition
     */
    List<TaskCelebrityLiveVo> getAppList(TaskCelebrityLiveVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);
}
