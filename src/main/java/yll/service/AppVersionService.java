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
import yll.entity.AppVersion;
import yll.mapper.AppVersionMapper;
import yll.service.model.YllConstants;


/**
 * APP版本
 */
@Transactional
@Service
public class AppVersionService {

    // ==============================Fields===========================================
    @Autowired
    private AppVersionMapper appVersionMapper;
    @Autowired
    private Securitys securitys;

    // ==============================Methods==========================================
    /**
     * 新增
     * @param
     */
    public void insert(AppVersion vo) {

        validate(vo);

        Principal principal = securitys.getPrincipal();

        AppVersion entity = new AppVersion();
        IdHelper.setIfEmptyId(entity);

        entity.setVersionType(vo.getVersionType());
        entity.setVersionName(vo.getVersionName());
        entity.setCreator(vo.getCreator());
        entity.setCreatedTime(vo.getCreatedTime());
        entity.setVersionDetails(vo.getVersionDetails());
        entity.setFileUrl(vo.getFileUrl());
        entity.setState(YllConstants.ONE);
        entity.setVersionCode(vo.getVersionCode());

        appVersionMapper.insert(entity);
    }

    /**
     * 删除(标记删除)
     * @param
     */
    public void deleteById(String id) {
        AppVersion entity = appVersionMapper.getById(id);
        appVersionMapper.deleteById(id);
    }

    /**
     * 更新
     * @param vo  更新实体
     */
    public void update(AppVersion vo) {
        validate(vo);

        AppVersion entity = appVersionMapper.getById(vo.getId());

        if (entity == null) {
            throw ExceptionHelper.prompt("数据不存在或者已经失效");
        }

        entity.setVersionType(vo.getVersionType());
        entity.setVersionName(vo.getVersionName());
        entity.setCreator(vo.getCreator());
        entity.setCreatedTime(vo.getCreatedTime());
        entity.setVersionDetails(vo.getVersionDetails());
        entity.setFileUrl(vo.getFileUrl());
        entity.setState(YllConstants.ONE);
        entity.setVersionCode(vo.getVersionCode());

        appVersionMapper.update(entity);
    }

    /**
     * 查询
     * @param id  ID
     * @return 实体
     */
    public AppVersion getById(String id) {
        AppVersion entity = appVersionMapper.getById(id);
        return entity;
    }

    /**
     * 分页查询
     * @param pagination 分页条件
     * @param condition 查询条件
     * @return 分页结果
     */
    public Page<AppVersion> pagedQuery(Pagination pagination, AppVersion condition) {
        return MybatisHelper.selectPage(pagination, () -> appVersionMapper.findBy(condition));
    }

    /**
     * 查询最新版本
     * @param condition 查询条件
     * @return 分页结果
     */
    public AppVersion findByApp(AppVersion condition) {
        return appVersionMapper.findByApp(condition);
    }

    // ==============================ToolMethods======================================
    /** 验证数据 */
    private void validate(AppVersion vo) {
        String id = vo.getId();
        String name = vo.getVersionName();
        String fileUrl = vo.getFileUrl();
        Integer versionCode = vo.getVersionCode();

        if (StringUtils.isEmpty(name)) {
            throw ExceptionHelper.prompt("版本号不能为空");
        }
        if (StringUtils.isEmpty(fileUrl)) {
            throw ExceptionHelper.prompt("文件地址不能为空");
        }

        if (null == versionCode || 0 == versionCode) {
            throw ExceptionHelper.prompt("版本更新次数不能为空");
        }
    }
}
