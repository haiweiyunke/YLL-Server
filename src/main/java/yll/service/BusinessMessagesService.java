package yll.service;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yll.common.BaseConstants.BoolInts;
import yll.common.BaseConstants.Ids;
import yll.common.identifier.IdHelper;
import yll.common.standard.CommonAttributeUtil;
import yll.entity.BusinessMessages;
import yll.mapper.BusinessMessagesMapper;
import yll.service.model.YllConstants;
import yll.service.model.vo.BusinessMessagesVo;

/**
 *  商务洽谈
 */
@Transactional
@Service
public class BusinessMessagesService {

    // ==============================Fields===========================================
    @Autowired
    private BusinessMessagesMapper businessMessagesMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(BusinessMessages vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        BusinessMessages entity = new BusinessMessages();
        IdHelper.setIfEmptyId(entity);

        entity.setCid(vo.getCid());
        entity.setNegotiator(vo.getNegotiator());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        entity.setEnabled(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        businessMessagesMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        businessMessagesMapper.deleteById(id);
//        voRoleMapper.deleteByBusinessMessagesId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(BusinessMessages vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        BusinessMessages entity = businessMessagesMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setCid(vo.getCid());
        entity.setNegotiator(vo.getNegotiator());
        entity.setContent(vo.getContent());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        if(null != vo.getDeleted()){
            entity.setDeleted(vo.getDeleted());
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        businessMessagesMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        BusinessMessages entity = new BusinessMessages();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        businessMessagesMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public BusinessMessages getById(String id) {
        BusinessMessages entity = businessMessagesMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<BusinessMessages> pagedQuery(Pagination pagination, BusinessMessages condition) {
        return MybatisHelper.selectPage(pagination, () -> businessMessagesMapper.findBy(condition));
    }

    /**
     * 分页查询-App
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<BusinessMessagesVo> getAppList(Pagination pagination, BusinessMessagesVo condition) {
        return MybatisHelper.selectPage(pagination, () -> businessMessagesMapper.getAppList(condition));
    }

    /**
     * 查询-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public BusinessMessagesVo getAppDetail(BusinessMessagesVo condition) {
        return businessMessagesMapper.getAppDetail(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(BusinessMessages vo) {
        String id = vo.getId();
        String cid = vo.getCid();
        String content = vo.getContent();
        String negotiator = vo.getNegotiator();

        if (StringUtils.isEmpty(cid)) {
            throw ExceptionHelper.prompt("达人不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            throw ExceptionHelper.prompt("内容不能为空");
        }
        if (StringUtils.isEmpty(negotiator)) {
            throw ExceptionHelper.prompt("洽谈人不能为空");
        }
    }
}
