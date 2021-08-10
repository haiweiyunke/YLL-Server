package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.TaskEnterpriseComplaint;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TaskEnterpriseComplaintVo;

import java.util.List;

/**
 * 任务达人商品_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskEnterpriseComplaintMapper extends BasicMapper<TaskEnterpriseComplaint, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskEnterpriseComplaint> findBy(TaskEnterpriseComplaint condition);


    /**
     * 查询流程
     * @param condition
     * @return 流程信息
     */
    TaskEnterpriseComplaint findByCondition(TaskEnterpriseComplaint condition) ;


    /**
     * 获取列表
     * @param condition
     */
    List<TaskEnterpriseComplaintVo> getList(TaskEnterpriseComplaintVo condition);


    /**
     * 获取列表-app列表
     * @param condition
     */
    List<TaskEnterpriseComplaintVo> getAppList(TaskEnterpriseComplaintVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);

}
