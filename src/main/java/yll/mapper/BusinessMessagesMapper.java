package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.BusinessMessages;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.BusinessMessagesVo;

import java.util.List;

/**
 * 商务洽谈_Mapper接口
 * @author cc
 */
@Mapper
public interface BusinessMessagesMapper extends BasicMapper<BusinessMessages, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<BusinessMessages> findBy(BusinessMessages condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<BusinessMessagesVo> getAppList(BusinessMessagesVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    BusinessMessagesVo getAppDetail(BusinessMessagesVo condition);

}
