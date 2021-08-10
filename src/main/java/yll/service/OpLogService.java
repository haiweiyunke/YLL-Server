package yll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.relucent.base.plug.mybatis.MybatisHelper;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.plug.security.Securitys;
import com.github.relucent.base.util.lang.DateUtil;
import com.github.relucent.base.util.lang.DateUtil.DateUnit;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;

import yll.common.identifier.IdHelper;
import yll.common.standard.AuditableUtil;
import yll.entity.OpLog;
import yll.mapper.OpLogMapper;

/**
 * 消息服务
 */
@Transactional
@Service
public class OpLogService {

    // ==============================Fields===========================================
    @Autowired
    private OpLogMapper opLogMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /** 新增(批量) */
    public void inserts(OpLog[] entities) {
        if (entities.length == 0) {
            return;
        }
        Principal principal = securitys.getPrincipal();
        for (OpLog entity : entities) {
            IdHelper.setIfEmptyId(entity);
            AuditableUtil.setCreated(entity, principal);
        }
        opLogMapper.inserts(entities);
    }

    /** 删除 */
    public void deleteById(String id) {
        opLogMapper.deleteById(id);
    }

    /** 查询 */
    public OpLog getById(String id) {
        return opLogMapper.getById(id);
    }

    /** 分页查询 */
    public Page<OpLog> pagedQuery(Pagination pagination, OpLog condition) {
        condition.setCreatedAtBegin(DateUtil.begin(condition.getCreatedAtBegin(), DateUnit.DATE));
        condition.setCreatedAtEnd(DateUtil.end(condition.getCreatedAtEnd(), DateUnit.DATE));
        return MybatisHelper.selectPage(pagination, () -> opLogMapper.findBy(condition));
    }

    // ==============================ToolMethods======================================
    // ...
}
