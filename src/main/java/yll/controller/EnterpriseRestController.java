package yll.controller;

import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yll.entity.Dic;
import yll.entity.Enterprise;
import yll.entity.Enterprise;
import yll.entity.Mcn;
import yll.service.DicService;
import yll.service.EnterpriseService;
import yll.service.McnService;
import yll.service.model.vo.EnterpriseVo;
import yll.service.model.vo.McnVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 企业主管理
 * @author cc
 */
@RestController
@RequestMapping(value = "/rest/enterprise")
public class EnterpriseRestController {

    // ==============================Fields===========================================
    @Autowired
    private EnterpriseService enterpriseService;
    @Autowired
    private McnService mcnService;

    @Autowired
    private DicService dicService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/enterprise/save <br>
     * 保存数据(新增|更新)
     */
     @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Enterprise entity) {
        String id = entity.getId();
        if (StringUtils.isBlank(id)) {
            enterpriseService.insert(entity);
        } else {
            enterpriseService.update(entity);
        }
        return Result.ok();
    }


    /**
     * [DELETE] /rest/enterprise/{id} <br>
     * 根据编号删除
     */
     @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        enterpriseService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/enterprise/{id} <br>
     * 根据id查询详情
     */
     @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Enterprise entity = enterpriseService.getById(id);
        return Result.ok(entity);
    }


    /**
     * [GET] /rest/enterprise/creator/{id} <br>
     * 根据用户查询详情
     */
    @GetMapping(value = "/creator/{id}")
    public Result<?> getByCreator(@PathVariable("id") String cid) {
        Enterprise condition = new Enterprise();
        condition.setCreator(cid);
        Enterprise entity = enterpriseService.getByCondition(condition);
        return Result.ok(entity);
    }

    /**
     * [POST] /rest/enterprise/all <br>
     * 查询所所有数据列表
     */
    @PostMapping(value = "/all")
    public Result<?> pagedQuery(Enterprise condition) {
        condition.setEnabled(1);
        condition.setState(2);
        List<EnterpriseVo> eList = enterpriseService.find4Shop(condition);

        Mcn m = new Mcn();
        m.setEnabled(1);
        m.setState(2);
        List<McnVo> mList = mcnService.find4Shop(m);

        List<Object> result = new ArrayList<>();
        result.add(eList);
        result.add(mList);
        return Result.ok(result);
    }

    /**
     * [GET] /rest/enterprise/state/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/state/list")
    public Result<?> statePagedQuery(Pagination pagination, EnterpriseVo condition) {
        condition = ObjectUtils.defaultIfNull(condition, new EnterpriseVo());
        Page<EnterpriseVo> page = enterpriseService.findByAdminList(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/enterprise/dic/all <br>
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
