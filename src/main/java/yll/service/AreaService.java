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
import yll.common.identifier.IdHelper;
import yll.entity.Area;
import yll.mapper.AreaMapper;

import java.util.List;

/**
 * 行政区划
 */
@Transactional
@Service
public class AreaService {

    // ==============================Fields===========================================
    @Autowired
    private AreaMapper areaMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(Area vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        Area entity = new Area();
        IdHelper.setIfEmptyId(entity);

        entity.setPid(vo.getPid());
        entity.setNode(vo.getNode());
        entity.setName(vo.getName());
        entity.setLevel(vo.getLevel());
        entity.setLat(vo.getLat());
        entity.setLng(vo.getLng());

        areaMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        Area entity = areaMapper.getById(id);
        if (null == entity.getPid() || 0 == entity.getPid()) {
            throw ExceptionHelper.prompt("一级节点不能被删除");
        }
        areaMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(Area vo) {
        validate(vo);

        Area entity = areaMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setPid(vo.getPid());
        entity.setNode(vo.getNode());
        entity.setName(vo.getName());
        entity.setLevel(vo.getLevel());
        entity.setLat(vo.getLat());
        entity.setLng(vo.getLng());

        areaMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public Area getById(String id) {
        Area entity = areaMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<Area> pagedQuery(Pagination pagination, Area condition) {
        return MybatisHelper.selectPage(pagination, () -> areaMapper.findBy(condition));
    }

    /**
     * 查询集合（带上下级）-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Area> getAppList2(Area condition) {
        return areaMapper.getAppList2(condition);
    }

    /**
     * 查询集合-App
     * @param condition 查询条件
     * @return 分页结果
     */
    public List<Area> getAppList(Area condition) {
        return areaMapper.getAppList(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(Area vo) {
        String id = vo.getId();
        String name = vo.getName();
        Integer level = vo.getLevel();
        Double lat = vo.getLat();
        Double lng = vo.getLng();

        if (StringUtils.isEmpty(name)) {
            throw ExceptionHelper.prompt("名称不能为空");
        }
        if (null == level || 0 == level) {
            throw ExceptionHelper.prompt("级别不能为空");
        }
        if (null == lat || 0 == lat) {
            throw ExceptionHelper.prompt("经度不能为空");
        }
        if (null == lng || 0 == lng) {
            throw ExceptionHelper.prompt("维度不能为空");
        }
    }
}
