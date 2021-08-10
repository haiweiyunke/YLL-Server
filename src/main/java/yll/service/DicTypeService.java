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
import yll.entity.DicType;
import yll.mapper.DicTypeMapper;
import yll.service.model.YllConstants;

import java.util.List;

/**
 * 数据字典
 */
@Transactional
@Service
public class DicTypeService {

    // ==============================Fields===========================================
    @Autowired
    private DicTypeMapper dicTypeMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(DicType vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        DicType entity = new DicType();
        IdHelper.setIfEmptyId(entity);

        entity.setCode(vo.getCode());
        entity.setCodename(vo.getCodename());
        entity.setRemark(vo.getRemark());

        entity.setState(YllConstants.ONE);

        CommonAttributeUtil.setCreated(entity, principal);
        dicTypeMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        dicTypeMapper.deleteById(id);
//        voRoleMapper.deleteByDicTypeId(id);   关联删除
    }

    /**
     * 更新
     * @param
     */
    public void update(DicType vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        DicType entity = dicTypeMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setCode(vo.getCode());
        entity.setCodename(vo.getCodename());
        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));

        CommonAttributeUtil.setUpdated(entity, principal);
        dicTypeMapper.update(entity);
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        DicType entity = new DicType();
        entity.setId(id);
        entity.setState(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        dicTypeMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public DicType getById(String id) {
        DicType entity = dicTypeMapper.getById(id);
        return entity;
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public DicType getByCode(String code) {
        DicType entity = dicTypeMapper.getByCode(code);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<DicType> pagedQuery(Pagination pagination, DicType condition) {
        return MybatisHelper.selectPage(pagination, () -> dicTypeMapper.findBy(condition));
    }

    /**
     * 查询所有
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<DicType> all(DicType condition) {
        return dicTypeMapper.findBy(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(DicType vo) {
        String id = vo.getId();
        String code = vo.getCode();
        String codename = vo.getCodename();

        if (StringUtils.isEmpty(code)) {
            throw ExceptionHelper.prompt("编码不能为空");
        }
        if (StringUtils.isEmpty(codename)) {
            throw ExceptionHelper.prompt("编码名称内容不能为空");
        }
        DicType dicType = dicTypeMapper.getByCode(code);
        if (null != dicType && !id.equals(dicType.getId())) {
            throw ExceptionHelper.prompt("该编码已存在");
        }
    }
}
