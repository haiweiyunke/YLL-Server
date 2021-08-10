package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.PlatformAttributeValue;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.ExcelExportChartVo;
import yll.service.model.vo.PlatformAttributeValueVo;

import java.util.List;

/**
 *  平台自定义属性-值表_Mapper接口
 * @author cc
 */
@Mapper
public interface PlatformAttributeValueMapper extends BasicMapper<PlatformAttributeValue, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<PlatformAttributeValue> findBy(PlatformAttributeValue condition);

    /**
     * 查询
     * @param condition
     * @return 信息
     */
    PlatformAttributeValue findByCondition(PlatformAttributeValue condition) ;


    /**
     * 根据条件查询-表外连接
     * @param condition 查询条件
     * @return 列表
     */
    List<PlatformAttributeValue> getBySelf(PlatformAttributeValue condition);

    /**
     * 图表
     * @param condition 查询条件
     * @return 列表
     */
    List<PlatformAttributeValueVo> chart(PlatformAttributeValueVo condition);

    /**
     * 特殊计算图表-拼多多
     * @param condition 查询条件
     * @return 列表
     */
    List<PlatformAttributeValueVo> chartCalculationPinduoduo(PlatformAttributeValueVo condition);

    /**
     * Excel导出-拼多多
     * @param condition 查询条件
     * @return 列表
     */
    List<ExcelExportChartVo> exportExcelForPinduoduo(PlatformAttributeValueVo condition);

    /**
     * 删除
     * @param gid
     */
    void deleteByGid(String gid) ;
}
