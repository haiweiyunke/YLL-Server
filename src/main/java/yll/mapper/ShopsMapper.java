package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Shops;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ShopsVo;

import java.util.List;

/**
 * 店铺_Mapper接口
 * @author cc
 */
@Mapper
public interface ShopsMapper extends BasicMapper<Shops, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Shops> findBy(Shops condition);

    /**
     * 根据条件查询(带type转换)
     * @param condition 查询条件
     * @return 列表
     */
    List<Shops> findByWithType(Shops condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<ShopsVo> getAppList(ShopsVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    ShopsVo getAppDetail(ShopsVo condition);

    /**
     * 根据条件查询(slide为0的数据=非轮播图)
     * @param condition 查询条件
     * @return 列表
     */
    List<Shops> findBySlide(Shops condition);

}
