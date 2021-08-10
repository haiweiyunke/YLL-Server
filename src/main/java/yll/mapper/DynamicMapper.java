package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Dynamic;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.DynamicVo;

import java.util.List;

/**
 * 动态_Mapper接口
 * @author cc
 */
@Mapper
public interface DynamicMapper extends BasicMapper<Dynamic, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Dynamic> findBy(Dynamic condition);

    /**
     * 查询动态
     * @param condition
     * @return 动态信息
     */
    Dynamic findByCondition(Dynamic condition) ;

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);

    /**
     * 获取列表-app列表
     * @param condition
     */
    List<DynamicVo> getAppList(DynamicVo condition);

    /**
     * 获取列表-app列表
     * @param condition
     */
    DynamicVo getAppDetail(DynamicVo condition);

}
