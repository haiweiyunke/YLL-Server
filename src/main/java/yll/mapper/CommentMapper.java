package yll.mapper;

import org.apache.ibatis.annotations.Mapper;
import yll.entity.Comment;
import yll.mapper.basic.BasicMapper;
import yll.service.model.vo.CommentVo;

import java.util.List;

/**
 * 评论_Mapper接口
 * @author cc
 */
@Mapper
public interface CommentMapper extends BasicMapper<Comment, String> {

    /**
     * 根据条件查询
     * @param condition 查询条件
     * @return 列表
     */
    List<Comment> findBy(Comment condition);

    /**
     * 查询动态
     * @param condition
     * @return 动态信息
     */
    Comment findByCondition(Comment condition) ;

    /**
     * 刪除记录
     * @param creator 记录creator
     */
    void deleteByCreator(String creator);

    /**
     * 刪除记录
     * @param dynamicId 动态ID
     */
    void deleteByDynamicId(String dynamicId);

    /**
     * 获取列表-app列表
     * @param condition
     */
    List<CommentVo> getAppList(CommentVo condition);

}
