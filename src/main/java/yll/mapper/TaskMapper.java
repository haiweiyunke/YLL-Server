package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Task;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.TaskCelebrityProductsVo;
import yll.service.model.vo.TaskCelebrityVo;
import yll.service.model.vo.TaskVo;

import java.util.List;

/**
 * 任务_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskMapper extends BasicMapper<Task, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Task> findBy(Task condition);

    /**
     * 查询任务
     * @param condition
     * @return 任务信息
     */
    Task findByCondition(Task condition) ;

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);


    /**
     * 根据条件查询-后台使用
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> findByAdminList(TaskVo condition);

    /**
     * 根据条件查询(slide为0的数据=非轮播图)
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> findBySlide(TaskVo condition);

    /**
     * 根据条件查询(extension为0的数据=非推广)
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> findByExtension(TaskVo condition);

    /**
     * app列表
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> getAppList(TaskVo condition);

    /**
     * 获取详情-app
     * @param condition
     */
    TaskVo getAppDetail(TaskVo condition);

    /**
     * 企业主任务列表
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> getAppEnterpriseTaskList(TaskVo condition);

    /**
     * 企业主任务订单列表
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> getAppEnterpriseOrderList(TaskVo condition);

    /**
     * 达人任务订单列表
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> getAppCelebrityTaskList(TaskVo condition);

    /**
     * 企业主任务订单详情
     * @param condition 查询条件
     * @return 列表
     */
    TaskVo getAppEnterpriseTaskDetail(TaskVo condition);


    /**
     * 企业主任务达人列表
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskVo> getAppEnterpriseTaskCelebrityList(TaskCelebrityVo condition);


    /**
     * 达人任务订单详情
     * @param condition 查询条件
     * @return 列表
     */
    TaskVo getAppCelebrityTaskDetail(TaskCelebrityVo condition);

}
