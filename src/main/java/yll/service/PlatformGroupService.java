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
import yll.entity.PlatformGroup;
import yll.mapper.PlatformAttributeValueMapper;
import yll.mapper.PlatformGroupMapper;
import yll.mapper.PlatformMapper;
import yll.service.model.YllConstants;

import java.util.List;


/**
 * 自定义属性组信息
 */
@Transactional
@Service
public class PlatformGroupService {

    // ==============================Fields===========================================
    @Autowired
    private PlatformGroupMapper platformGroupMapper;
    @Autowired
    private PlatformAttributeValueMapper platformAttributeValueMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public PlatformGroup insert(PlatformGroup vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        PlatformGroup entity = new PlatformGroup();
        IdHelper.setIfEmptyId(entity);
        vo.setId(entity.getId());

        entity.setPid(vo.getPid());
        entity.setImage(vo.getImage());
        entity.setOrdinal(vo.getOrdinal());
        entity.setLiveTime(vo.getLiveTime());

        entity.setRemark(vo.getRemark());

        entity.setEnabled(YllConstants.ONE);
        entity.setDeleted(YllConstants.ZERO);
        if(null != vo.getState()){
            entity.setState(vo.getState());
        } else{
            entity.setState(YllConstants.ONE);
        }
        if(StringUtils.isBlank(vo.getCreator())){
            CommonAttributeUtil.setCreated(entity, principal);
        } else{
            //后台直接添加、用户自己注册
            entity.setCreator(vo.getCreator());
            entity.setCreatedTime(vo.getCreatedTime());
        }
        platformGroupMapper.insert(entity);

        return entity;
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        if (Ids.ADMIN_ID.equals(id)) {
            throw ExceptionHelper.prompt("该数据不能被删除");
        }
        platformGroupMapper.deleteById(id);
        //删除value值表
        platformAttributeValueMapper.deleteByGid(id);
    }

    /**
     * 更新
     * @param
     */
    public PlatformGroup update(PlatformGroup vo) {
        validate(vo);

        Principal principal = securitys.getPrincipal();

        PlatformGroup entity = platformGroupMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setPid(vo.getPid());
        entity.setImage(vo.getImage());
        entity.setOrdinal(vo.getOrdinal());
        entity.setLiveTime(vo.getLiveTime());

        entity.setRemark(vo.getRemark());

        if(null != vo.getState()){
            entity.setState(vo.getState());
        }
//        entity.setEnabled(BoolInts.normalize(vo.getEnabled()));
        if(StringUtils.isBlank(vo.getModifier())){
            CommonAttributeUtil.setUpdated(entity, principal);
        } else {
            //后台直接添加、用户自己注册
            entity.setModifier(vo.getModifier());
            entity.setModifiedTime(vo.getModifiedTime());
        }
        CommonAttributeUtil.setUpdated(entity, principal);
        platformGroupMapper.update(entity);

        return entity;
    }

    /**
     * 启用禁用
     * @param
     */
    public void enable(String id, Integer enabled) {
        Principal principal = securitys.getPrincipal();
        PlatformGroup entity = new PlatformGroup();
        entity.setId(id);
        entity.setEnabled(BoolInts.normalize(enabled));
        CommonAttributeUtil.setUpdated(entity, principal);
        platformGroupMapper.update(entity);
    }

    /**
     * 查询
     * @param
     * @return 实体
     */
    public PlatformGroup getById(String id) {
        PlatformGroup entity = platformGroupMapper.getById(id);
        return entity;
    }

    /**
     * 查询（根据条件）
     * @param
     * @return 实体
     */
    public PlatformGroup getByCondition(PlatformGroup condition) {
        return platformGroupMapper.findByCondition(condition);
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<PlatformGroup> pagedQuery(Pagination pagination, PlatformGroup condition) {
        return MybatisHelper.selectPage(pagination, () -> platformGroupMapper.findBy(condition));
    }

    /**
     * 查询所有
     * @param condition 查询条件
     * @return 结果
     */
    public List<PlatformGroup> all(PlatformGroup condition) {
        return platformGroupMapper.findBy(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(PlatformGroup vo) {
        String id = vo.getId();
        //String password = vo.getPassword();
        /*if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }*/
    }

}
