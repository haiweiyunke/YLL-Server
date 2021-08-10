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
import yll.entity.Dic;
import yll.entity.DicType;
import yll.mapper.DicMapper;
import yll.service.model.YllConstants;

import java.util.List;

/**
 * 数据字典
 */
@Transactional
@Service
public class DicService {

    // ==============================Fields===========================================
    @Autowired
    private DicMapper dicMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Dic vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Dic entity = new Dic();
        IdHelper.setIfEmptyId(entity);

        entity.setTargetId(vo.getTargetId());
        entity.setCode(vo.getCode());
        entity.setCodename(vo.getCodename());
        entity.setRemark(vo.getRemark());
        entity.setRemarks(vo.getRemarks());
        entity.setOrdinal(vo.getOrdinal());

        entity.setState(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        dicMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        dicMapper.deleteById(id);
//        voRoleMapper.deleteByDicId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(Dic vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        Dic entity = dicMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setTargetId(vo.getTargetId());
        entity.setCode(vo.getCode());
        entity.setCodename(vo.getCodename());
        entity.setRemark(vo.getRemark());
        entity.setRemarks(vo.getRemarks());
        entity.setOrdinal(vo.getOrdinal());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        dicMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        Dic entity = new Dic();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        dicMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Dic getById(String id) {
        Dic entity = dicMapper.getById(id);
        return entity;
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public Dic getByCode(String code) {
        Dic entity = dicMapper.getByCode(code);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Dic> pagedQuery(Pagination pagination, Dic condition) {
        return MybatisHelper.selectPage(pagination, () -> dicMapper.findBy(condition));
    }

    /**
     * 查询所有
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Dic> all(Dic condition) {
        return dicMapper.findBy(condition);
    }

    /**
     * 查询所有(App使用)
     * @param code 查询条件
     * @return 分页结果
     */
    public List<Dic> getAppList(String code) {
        return dicMapper.findByTargetId(code);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Dic vo) {
        String id = vo.getId();
        String code = vo.getCode();
        String codename = vo.getCodename();

        if (StringUtils.isEmpty(code)) {
            throw ExceptionHelper.prompt("编码不能为空");
        }
        if (StringUtils.isEmpty(codename)) {
            throw ExceptionHelper.prompt("编码名称内容不能为空");
        }
        Dic dic = dicMapper.getByCode(code);
        if (null != dic && !id.equals(dic.getId())) {
            throw ExceptionHelper.prompt("该编码已存在");
        }
    }
}
