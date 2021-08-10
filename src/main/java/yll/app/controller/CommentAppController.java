package yll.app.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.Comment;
import yll.service.CustomerService;
import yll.service.CommentService;
import yll.service.model.vo.CommentVo;

/**
 * 评论
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/comment")
public class CommentAppController {

    // ==============================Fields===========================================
    @Autowired
    private CommentService commentService;

    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/comment/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(Comment condition) {
        String remark = condition.getRemark();
        if(StringUtils.isBlank(remark)){
            return Result.error("写点儿什么吧");
        }
        String id = condition.getId();
        if (StringUtils.isBlank(id)) {
            commentService.insert(condition);
        } else {
            commentService.update(condition);
        }
        return Result.ok();
    }


    /**
     * [POST] /app/comment/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, CommentVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CommentVo());
        String dynamicId = condition.getDynamicId();
        if(StringUtils.isBlank(dynamicId)){
           return Result.error("缺少动态标识");
        }
        Page<CommentVo> page = commentService.getAppList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [POST] /app/comment/cancel <br>
     * 根据编号删除
     */
    @PostMapping(value = "/cancel")
    public Result<?> delete(CommentVo condition) {
        String id = condition.getId();
        if(StringUtils.isBlank(id)){
            return Result.error("id不能为空");
        }
        commentService.deleteById(id);
        return Result.ok();
    }

}
