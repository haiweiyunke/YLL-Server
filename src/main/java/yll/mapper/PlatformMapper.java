package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Platform;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.PlatformVo;

import java.util.List;

/**
 * 达人平台信息_Mapper接口
 * @author cc
 */
@Mapper
public interface PlatformMapper extends BasicMapper<Platform, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Platform> findBy(Platform condition);

    /**
     * 查询数据
     * @param condition
     * @return 数据信息
     */
    Platform findByCondition(Platform condition) ;

    /**
     * 查询数据
     * @param condition
     * @return 数据信息
     */
    List<PlatformVo> findByDic(PlatformVo condition) ;

    /**
     * 获取详情-app
     * @param condition
     */
    PlatformVo getAppDetail(PlatformVo condition);

    /**
     * 获取详情-app认证详情
     * @param condition
     */
    List<PlatformVo> getAppAuth(PlatformVo condition);

    /**
     * 删除所属用户数据
     * @param id
     */
    void deleteByCreator(String creator);

    /**
     * 获取详情-app认证详情
     * @param condition
     */
    List<PlatformVo> findByAttributeList(PlatformVo condition);

}
