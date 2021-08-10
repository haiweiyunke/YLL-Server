package yll.controller;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.Dic;
import yll.entity.InternetCelebrity;
import yll.entity.Mcn;
import yll.service.DicService;
import yll.service.McnService;
import yll.service.model.vo.InternetCelebrityVo;
import yll.service.model.vo.McnVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网红（达人）信息管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/mcn")
public class McnRestController {

    // ==============================Fields===========================================
    @Autowired
    private McnService mcnService;
    @Autowired
    private DicService dicService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/mcn/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Mcn entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            mcnService.insert(entity);
        } else {
            mcnService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/mcn/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        mcnService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/mcn/{id} <br>
     * 保存数据(新增|更新)
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Mcn entity = mcnService.getById(id);
        return Result.ok(entity);
    }


    /**
     * [GET] /rest/mcn/creator/{id} <br>
     * 保存数据(新增|更新)
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        Mcn condition = new Mcn();
        condition.setCreator(cid);
        Mcn entity = mcnService.getByCondition(condition);
        return Result.ok(entity);
    }

    /**
     * [GET] /rest/mcn/all <br>
     * 查询数据列表
     */
    @GetMapping(value = "/all")
    public Result<?> all(Mcn condition) {
        condition.setState(2);
        condition = ObjectUtils.defaultIfNull(condition, new Mcn());
        List<Mcn> list = mcnService.getAllByCondition(condition);
        return Result.ok(list);
    }

    /**
     * [GET] /rest/mcn/state/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/state/list")
    public Result<?> statePagedQuery(Pagination pagination, McnVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new McnVo());
        Page<McnVo> page = mcnService.findByAdminList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/mcn/dic/all <br>
     * 查询编辑页字典所需数据
     */
    @GetMapping(value = "/dic/all")
    public Result<?> dicAll() {
        //所属行业
        List<Dic> industryList = dicService.getAppList("expertise");
        //公司人数
        List<Dic> staffList = dicService.getAppList("Scale");

        Map<String,Object> result = new HashMap<>();
        result.put("industryList", industryList);
        result.put("staffList", staffList);
        return Result.ok(result);
    }

}
