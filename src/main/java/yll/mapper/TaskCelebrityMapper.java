package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.poi.ss.formula.functions.T;
import yll.entity.TaskCelebrity;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TaskCelebrityVo;

import java.util.List;

/**
 * 任务达人_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskCelebrityMapper extends BasicMapper<TaskCelebrity, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskCelebrity> findBy(TaskCelebrity condition);

    /**
     * 查询动态
     * @param condition
     * @return 动态信息
     */
    TaskCelebrity findByCondition(TaskCelebrity condition) ;

    /**
     * 刪除记录
     * @param pid
     */
    void deleteByPid(String pid);

    /**
     * 获取列表 (自定义查询集合)
     * @param condition
     */
    List<TaskCelebrityVo> getList(TaskCelebrityVo condition);


    /**
     * 获取列表-app列表
     * @param getList
     */
    List<TaskCelebrityVo> getAppList(TaskCelebrityVo condition);


    /**
     * 分页查询-后台使用
     * @param condition
     * @param <T>
     * @return
     */
    List<TaskCelebrityVo> pagedQueryAdminList(TaskCelebrityVo condition);

}
