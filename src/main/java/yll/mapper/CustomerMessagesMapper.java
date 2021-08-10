package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.CustomerMessages;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CustomerMessagesVo;

import java.util.List;

/**
 * 我的消息_Mapper接口
 * @author cc
 */
@Mapper
public interface CustomerMessagesMapper extends BasicMapper<CustomerMessages, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerMessages> findBy(CustomerMessages condition);

    /**
     * 根据条件查询(App使用)
     * @param condition 查询条件
     * @return 列表
     */
    List<CustomerMessagesVo> getAppList(CustomerMessagesVo condition);

    /**
     * 查询详情(App使用)
     * @param condition 查询条件
     * @return 详情
     */
    CustomerMessagesVo getAppDetail(CustomerMessagesVo condition);

}
