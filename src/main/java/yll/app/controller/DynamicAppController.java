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
import yll.entity.Dynamic;
import yll.service.CustomerService;
import yll.service.DynamicService;
import yll.service.model.vo.DynamicVo;

/**
 * 动态
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/dynamic")
public class DynamicAppController {

    // ==============================Fields===========================================
    @Value("${cc.image.accept}")
    private String imageAccept = "jpg,jpeg,png";

    @Value("${cc.video.accept}")
    private String videoAccept = "mp4,avi,rmvb,mkv";

    @Autowired
    private DynamicService dynamicService;

    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/dynamic/save <br>
     * 新增/编辑
     */
    @PostMapping(value = "/save")
    public Result<?> save(Dynamic condition) {
        //非达人不可添加
        /*String customerId = AppSecuritysUtil.getCustomerId();
        Customer customer = customerService.getById(customerId);
        String roleType = customer.getRoleType();
        if(!"2".equals(roleType)){
            return Result.error("无操作权限");
        }*/
        String id = condition.getId();
        String image = condition.getImage();
        String words = condition.getWords();
        String visibleScope = condition.getVisibleScope();
        if(StringUtils.isBlank(image) && StringUtils.isBlank(words)){
            return Result.error("请发表感想或上传图片");
        }
        if(StringUtils.isBlank(visibleScope)){
            return Result.error("缺少可见范围");
        }
        //图片视频混合上传，后台处理图片视频分开存放
        if(StringUtils.isNotBlank(image)){
            String[] array = image.split(",");
            String img = "";
            String video = "";
            for (String str:
                 array) {
                String s = "";
                //切割cos地址
                String[] split = str.split("yshd-1256225403.cos.ap-beijing.myqcloud.com/yshd/prod/");
                if(split.length > 1){
                    //cos上传
                    s = split[1];
                } else{
                    //普通上传
                    s = str;
                }
                String[] sarr = s.split("\\.");
                if(split.length > 1){
                    s = str;
                }
                if(sarr.length > 1){
                    if(imageAccept.indexOf(sarr[1]) != -1){
                        //图像
                        img += s;
                        img += ",";
                    } else if(videoAccept.indexOf(sarr[1]) != -1){
                        //视频
                        video += s;
                        video += ",";
                    }
                }
            }
            if(StringUtils.isNotBlank(img)){
                img = img.substring(0, img.length()-1);
            }
            if(StringUtils.isNotBlank(video)){
                video = video.substring(0, video.length()-1);
            }
            condition.setImage(img);
            condition.setVideo(video);
        }
        if (StringUtils.isBlank(id)) {
            dynamicService.insert(condition);
        } else {
            dynamicService.update(condition);
        }
        return Result.ok();
    }


    /**
     * [POST] /app/dynamic/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, DynamicVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new DynamicVo());
        String id = condition.getId();
        if(StringUtils.isBlank(id)){
            id = AppSecuritysUtil.getCustomerId();
        }
        condition.setCreator(id);
        condition.setId(null);
        Page<DynamicVo> page = dynamicService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [POST] /app/dynamic/celebrity/list <br>
     * 查询数据列表
     */
    @PostMapping(value = "/celebrity/list")
    public Result<?> celebrityPagedQuery(Pagination pagination, DynamicVo condition) {
        String id = condition.getId();
        if(StringUtils.isBlank(id)){
            return Result.error("缺少达人标识");
        }
        condition = ObjectUtils.defaultIfNull(condition, new DynamicVo());
        condition.setCreator(id);   //根据达人customer表的id查询
        condition.setId(null);
        Page<DynamicVo> page = dynamicService.getAppList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /app/dynamic/{id} <br>
     * 查询数据详情
     */
    @GetMapping(value = "/{id}")
    public Result<?> get(@PathVariable("id") String id) {
        //封装
        String customerId = AppSecuritysUtil.getCustomerId();
        DynamicVo entity = new DynamicVo();
        entity.setId(id);
        entity.setCreator(customerId);
        //获取详情
        entity = dynamicService.getAppDetail(entity);
        return Result.ok(entity);
    }

    /**
     * [POST] /app/dynamic/cancel <br>
     * 根据编号删除
     */
    @PostMapping(value = "/cancel")
    public Result<?> delete(DynamicVo condition) {
        String id = condition.getId();
        if(StringUtils.isBlank(id)){
            return Result.error("id不能为空");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        DynamicVo entity = new DynamicVo();
        entity.setId(id);
        entity.setCreator(customerId);
        //获取详情``````
        entity = dynamicService.getAppDetail(entity);
        if(null == entity){
            return Result.error("数据不存在");
        }
        dynamicService.deleteById(id);
        return Result.ok();
    }

}
