package yll.controller;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.relucent.base.util.model.PageResult;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.page.Page;
import com.github.relucent.base.util.page.Pagination;

import yll.common.BaseConstants.Ids;
import yll.common.BaseConstants.PermissionType;
import yll.entity.Permission;
import yll.service.PermissionService;
import yll.service.model.MenuNode;

/**
 * 功能权限管理
 * @author YYL
 */
@RestController
@RequestMapping(value = "/rest/permission")
public class PermissionRestController {
    // ==============================Fields===========================================
    @Autowired
    private PermissionService permissionService;

    // ==============================Methods==========================================
    /**
     * [POST] /rest/user/save <br>
     * 保存数据(新增|更新)
     */
    @PostMapping(value = "/save")
    public Result<?> save(@RequestBody Permission permission) {
        String id = permission.getId();
        if (id == null) {
            permissionService.insert(permission);
        } else {
            permissionService.update(permission);
        }
        return Result.ok();
    }

    /**
     * [DELETE] /rest/permission/{id} <br>
     * 删除
     */
    @DeleteMapping(value = "/{id}")
    public Result<?> delete(@PathVariable("id") String id) {
        permissionService.deleteById(id);
        return Result.ok();
    }

    /**
     * [GET] /rest/permission/{id} <br>
     * 查询
     */
    @GetMapping(value = "/{id}")
    public Result<?> getById(@PathVariable("id") String id) {
        Permission permission = permissionService.getById(id);
        return Result.ok(permission);
    }

    /**
     * [GET] /rest/permission/list <br>
     * 查询数据列表
     */
    @GetMapping(value = "/list")
    public Result<?> pagedQuery(Pagination pagination, Permission condition) {
        condition = ObjectUtils.defaultIfNull(condition, new Permission());
        Page<Permission> page = permissionService.pagedQuery(pagination, condition);
        return PageResult.of(page);
    }

    /**
     * [GET] /rest/permission/menu-tree/{rootId} <br>
     * 查询菜单树(只到菜单层级)
     */
    @GetMapping(value = "/menu-tree/{rootId}")
    public Result<?> menuTree(@PathVariable("rootId") String rootId) {
        List<MenuNode> menus = permissionService.getMenuTree(rootId, PermissionType.MENU);
        return Result.ok(menus);
    }

    /**
     * [GET] /rest/permission/menu-perm-tree <br>
     * 查询菜单功能树(全部层级)
     */
    @GetMapping(value = "/menu-perm-tree")
    public Result<?> menuPermTree() {
        List<MenuNode> nodes = permissionService.getMenuTree(Ids.ADMIN_ID, PermissionType.BUTTON);
        return Result.ok(nodes);
    }

    /**
     * [GET] /rest/permission/menu-tops <br>
     * 查询TOP层菜单树
     */
    @GetMapping(value = "/menu-tops")
    public Result<?> findModules() {
        List<MenuNode> nodes = permissionService.findModules();
        return Result.ok(nodes);
    }
}
