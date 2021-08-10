package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.TaskProducts;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TaskCelebrityProductsVo;
import yll.service.model.vo.TaskCelebrityVo;
import yll.service.model.vo.TaskProductsVo;

import java.util.List;

/**
 * 任务达人_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskProductsMapper extends BasicMapper<TaskProducts, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskProducts> findBy(TaskProducts condition);

    /**
     * 查询动态
     * @param condition
     * @return 动态信息
     */
    TaskProducts findByCondition(TaskProducts condition) ;

    /**
     * 刪除记录
     * @param pid
     */
    void deleteByPid(String pid);

    /**
     * 获取列表 (自定义查询集合)
     * @param condition
     */
    List<TaskProductsVo> getList(TaskProductsVo condition);


    /**
     * 获取列表-app列表
     * @param getList
     */
    List<TaskProductsVo> getAppList(TaskProductsVo condition);

    /**
     * 获取列表-Admin列表
     * @param getList
     */
    List<TaskProductsVo> getAdminProductsList(TaskCelebrityProductsVo condition);

}
