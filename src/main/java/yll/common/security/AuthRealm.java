package yll.common.security;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.github.relucent.base.plug.security.AuthToken;
import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.util.expection.ExceptionHelper;
import com.github.relucent.base.util.lang.CollectionUtil;

import yll.common.BaseConstants.BoolInts;
import yll.entity.User;
import yll.service.RoleService;
import yll.service.UserService;

/**
 * 权限验证类<br>
 * @author YYL
 */
public class AuthRealm {

    // ==============================Fields===========================================
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==============================Constructors=====================================
    public AuthRealm() {
        super();
    }

    // ==============================Methods==========================================
    /**
     * (登陆验证)认证回调函数,登录时调用.
     * @param authcToken 认证凭据
     * @return 认证信息
     */
    protected Principal doGetAuthenticationInfo(AuthToken token) {

        String account = token.getAccount();
        String password = token.getPassword();

        if (StringUtils.isEmpty(account)) {
            throw ExceptionHelper.prompt("账号不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            throw ExceptionHelper.prompt("密码不能为空");
        }

        User user = userService.getByAccount(account);

        if (user == null) {
            throw ExceptionHelper.prompt("用户不存在");
        }

        if (user.getEnabled().intValue() == BoolInts.FALSE) {
            throw ExceptionHelper.prompt("用户已禁用，请联系管理员");
        }
        if (user.getDeleted().intValue() == BoolInts.TRUE) {
            throw ExceptionHelper.prompt("用户不存在，或者已经停用，请联系管理员");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw ExceptionHelper.prompt("用户名或密码错误");
        }

        String userId = user.getId();

        List<String> roldIdList = userService.findRoleIdByUserId(userId);
        String[] roleIds = CollectionUtil.toArray(roldIdList, String.class);

        List<String> permissionIdList = roleService.findPermissionIdByRoleIds(roleIds);
        String[] permissionIds = CollectionUtil.toArray(permissionIdList, String.class);

        Principal principal = new Principal();

        principal.setUserId(userId);
        principal.setName(user.getName());
        principal.setDepartmentId(user.getDepartmentId());
        principal.setRoleIds(roleIds);
        principal.setPermissionIds(permissionIds);

        return principal;
    }
}
