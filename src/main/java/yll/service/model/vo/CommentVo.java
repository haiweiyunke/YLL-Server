package yll.service.model.vo;

import lombok.Data;
import yll.entity.Comment;

/**
 * DH
 * 评论
 */
@SuppressWarnings("serial")
@Data
public class CommentVo extends Comment {

    //================返回参数======================
    private String appCreatedTime;
    private String nickname;
    private String headImg;
}
