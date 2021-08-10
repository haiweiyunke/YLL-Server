package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.TaskCelebrityProducts;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.TaskCelebrityProductsVo;

import java.util.List;

/**
 * 任务达人商品_Mapper接口
 * @author cc
 */
@Mapper
public interface TaskCelebrityProductsMapper extends BasicMapper<TaskCelebrityProducts, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<TaskCelebrityProducts> findBy(TaskCelebrityProducts condition);

    /**
     * 查询动态
     * @param condition
     * @return 动态信息
     */
    TaskCelebrityProducts findByCondition(TaskCelebrityProducts condition) ;

    /**
     * 刪除记录
     * @param pid
     */
    void deleteByPid(String pid);

    /**
     * 获取列表 (自定义查询集合)
     * @param condition
     */
    List<TaskCelebrityProductsVo> getList(TaskCelebrityProductsVo condition);


    /**
     * 获取列表-app列表
     * @param condition
     */
    List<TaskCelebrityProductsVo> getAppList(TaskCelebrityProductsVo condition);

    /**
     * 获取商品列表-app列表
     * @param condition
     */
    List<TaskCelebrityProductsVo> getAppProductsList(TaskCelebrityProductsVo condition);

    /**
     * 获取商品列表-后台列表
     * @param condition
     */
    List<TaskCelebrityProductsVo> pagedQueryProductsList(TaskCelebrityProductsVo condition);

    /**
     * 刪除记录
     * @param tcId
     */
    void deleteByTcId(String tcId);
}
