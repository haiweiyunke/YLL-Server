package yll.app.controller;

import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yll.common.security.app.AppSecuritysUtil;
import yll.entity.Customer;
import yll.entity.CelebrityInvite;
import yll.service.CustomerService;
import yll.service.CelebrityInviteService;

import java.util.List;

/**
 *  达人邀请
 * @author cc
 */
@RestController
@RequestMapping(value = "/app/celebrity-invite")
public class CelebrityInviteAppController {

    // ==============================Fields===========================================
    @Autowired
    private CelebrityInviteService celebrityInviteService;
    @Autowired
    private CustomerService customerService;

    // ==============================Methods==========================================
    /**
     * [POST] /app/celebrity-invite/list <br>
     * 达人查看查询数据列表
     */
    @PostMapping(value = "/list")
    public Result<?> list(Pagination pagination, CelebrityInvite condition) {
        condition = ObjectUtils.defaultIfNull(condition, new CelebrityInvite());
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer customer = customerService.getById(customerId);
        String roleType = customer.getRoleType();
        if("2".equals(roleType)){
            //达人查看列表
            condition.setCelebrityId(customerId);
            Page<CelebrityInvite> page = celebrityInviteService.getAppListCelebrity(pagination,condition);
            return Result.ok(page);
        } else if("3".equals(roleType)){
            //mcn查看列表
        }
        return Result.ok();
    }


    /**
     * [POST] /app/celebrity-invite/save <br>   //发起邀请 ，state（1-待处理，2-接受，3-拒绝）状态判断接受未接受，接受往达人表中修改mcnId，拒绝则只改变邀请表state的状态为3
     * mcn 发起邀请
     */
    @PostMapping(value = "/save")
    public Result<?> save(CelebrityInvite condition) {
        String celebrityId = condition.getCelebrityId();
        if(StringUtils.isBlank(celebrityId)){
            return Result.error("缺少达人唯一标识");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer customer = customerService.getById(customerId);
        String roleType = customer.getRoleType();
        //只有mcn可操作
        if(!"3".equals(roleType)){
            return Result.error("当前用户不是mcn");
        }
        condition.setMcnId(customerId);
        CelebrityInvite ci = celebrityInviteService.getByCondition(condition);//mcnid 及 达人id联合查询
        if(null != ci){
            return Result.error("请勿重复邀请");
        }
        celebrityInviteService.insert(condition);   //需要在达人操作时，将信息记录置为 1或者2
        return Result.ok();
    }


    /**
     * [POST] /app/celebrity-invite/result
     * mcn 达人操作邀请
     */
    @PostMapping(value = "/result")
    public Result<?> result(CelebrityInvite condition) {
        String id = condition.getId();
        Integer state = condition.getState();
        if(StringUtils.isBlank(id)){
            return Result.error("缺少id");
        }
        if(null == state){
            return Result.error("缺少操作结果标识");
        }
        String customerId = AppSecuritysUtil.getCustomerId();
        Customer customer = customerService.getById(customerId);
        CelebrityInvite ci = celebrityInviteService.getById(id);
        if(null == ci){
            return Result.error("未查询到相关数据");
        }
        if(!customerId.equals(ci.getCelebrityId())){
            return Result.error("当前用户无操作权限");
        }
        celebrityInviteService.update(condition);
        return Result.ok();
    }

}
