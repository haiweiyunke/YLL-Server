package yll.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.InternetCelebrity;
import yll.entity.InternetCelebrity;
import yll.entity.InternetCelebrity;
import yll.entity.Platform;
import yll.service.InternetCelebrityService;
import yll.service.PlatformService;
import yll.service.model.YllConstants;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.PlatformVo;

import java.util.List;

/**
 * 网红（达人）信息管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/internet-celebrity")
public class InternetCelebrityRestController {

    // ==============================Fields===========================================
    @Autowired
    private InternetCelebrityService internetCelebrityService;
    @Autowired
    private PlatformService platformService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/internet-celebrity/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody InternetCelebrity entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            internetCelebrityService.insert(entity);
        } else {
            internetCelebrityService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/internet-celebrity/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        internetCelebrityService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/internet-celebrity/{id} <br>
     * id查询数据
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
         //达人信息
        InternetCelebrity entity = internetCelebrityService.getById(id);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/internet-celebrity/creator/{id} <br>
     * 查询数据
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        InternetCelebrity condition = new InternetCelebrity();
        condition.setCreator(cid);
        //达人信息
        InternetCelebrity entity = internetCelebrityService.getByConditionVo(condition);

        //达人平台信息
        PlatformVo platformVo = new PlatformVo();
        platformVo.setCreator(entity.getCreator());
        List<PlatformVo> platformVos = platformService.getAppAuth(platformVo);
        String platformListStr = "";
        for (PlatformVo vo :
                platformVos) {
            String codename = vo.getCodename();
            if(StringUtils.isNotBlank(codename)){
                platformListStr += codename + "，";
            }
        }
        if(StringUtils.isNotBlank(platformListStr)){
            platformListStr = platformListStr.substring(0, platformListStr.length()-1);
        }
        //封装
        InternetCelebrityVo icv = new InternetCelebrityVo();
        BeanUtils.copyProperties(entity, icv);
        icv.setPlatformListStr(platformListStr);

        return Result.ok(icv);
    }

    /**
     * [GET] /rest/internet-celebrity/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        Page<InternetCelebrityVo> page = internetCelebrityService.pagedQuerySlide(pagination, condition);
        return PageResult.of(page);
    }

   /**
     * [GET] /rest/internet-celebrity/list-hot <br>
     * 查询数据列表-热门
     */
    @GetMapping(value = "/list-hot")
    public Result<?> pagedQueryListHot(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        Page<InternetCelebrityVo> page = internetCelebrityService.pagedQueryHot(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/internet-celebrity/state/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/state/list")
    public Result<?> statePagedQuery(Pagination pagination, InternetCelebrityVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new InternetCelebrityVo());
        Page<InternetCelebrityVo> page = internetCelebrityService.findByAdminList(pagination, condition);
        return PageResult.of(page);
    }


    /**
     * [POST] /rest/internet-celebrity/slide/list <br>
     * 查询轮播图数据
     */
    @PostMapping(value = "/slide/list")
    public Result<?> slideList(@RequestBody InternetCelebrityVo entity) {
        List<InternetCelebrityVo> list = internetCelebrityService.findBySlide(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/internet-celebrity/slide/save <br>
     * 保存数据-轮播(新增|更新)
     */
    @PostMapping(value = "/slide/save")
    public Result<?> slideSave(@RequestBody InternetCelebrityVo entity) {
        internetCelebrityService.slide(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/internet-celebrity/slide/{id} <br>
     * 根据编号删除-轮播
     */
    @DeleteMapping(value = "/slide/{id}")
    public Result<?> slideDelete(@PathVariable("id") String id) {
        InternetCelebrity entity = internetCelebrityService.getById(id);
        entity.setSlide(YllConstants.ZERO);
        entity.setOrdinal(YllConstants.LAST);
        internetCelebrityService.update(entity);
        return Result.ok();
    }


 /**
     * [POST] /rest/internet-celebrity/hot/list <br>
     * 查询热门数据
     */
    @PostMapping(value = "/hot/list")
    public Result<?> hotList(@RequestBody InternetCelebrityVo entity) {
        List<InternetCelebrityVo> list = internetCelebrityService.findByHot(entity);
        return Result.ok(list);
    }

    /**
     * [POST] /rest/internet-celebrity/hot/save <br>
     * 保存数据-热门(新增|更新)
     */
    @PostMapping(value = "/hot/save")
    public Result<?> hotSave(@RequestBody InternetCelebrityVo entity) {
        internetCelebrityService.hot(entity);
        return Result.ok();
    }

    /**
     * [DELETE] /rest/internet-celebrity/hot/{id} <br>
     * 根据编号删除-热门
     */
    @DeleteMapping(value = "/hot/{id}")
    public Result<?> hotDelete(@PathVariable("id") String id) {
        InternetCelebrity entity = internetCelebrityService.getById(id);
        entity.setHot(YllConstants.ZERO);
        entity.setHotOrdinal(YllConstants.LAST);
        internetCelebrityService.update(entity);
        return Result.ok();
    }


}
