package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.CelebrityInvite;
import yll.mapper.basic.BasicMapper;

import java.util.List;

/**
 * 达人邀请_Mapper接口
 * @author cc
 */
@Mapper
public interface CelebrityInviteMapper extends BasicMapper<CelebrityInvite, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<CelebrityInvite> findBy(CelebrityInvite condition);

    /**
     * 查询达人邀请
     * @param condition
     * @return 达人邀请信息
     */
    CelebrityInvite findByCondition(CelebrityInvite condition) ;

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);

    /**
     * 获取详情-app认证详情(达人查看列表)-mcn查看列表得另写
     * @param condition
     */
    List<CelebrityInvite> getAppListCelebrity(CelebrityInvite condition);

}
