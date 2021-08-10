package yll.common.security.app;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import yll.service.model.YllConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息提供者
 */
public class AppSecurityImplementor implements AppSecuritys {

    private final ThreadLocal<String> holder = new ThreadLocal<String>();

    @Autowired
    private  TokenCache cache;

    @Autowired
    private AppRealm realm;

    private final AppPrincipal localPrincipal = new AppPrincipal(){
        @Override
        public String getCustomerId() {
            return getCid();
        }
    };

    @Override
    public void login(AppToken params) throws Exception{
        AppPrincipal appPrincipal = realm.doGetAuthenticationInfo(params);
        String token = cache.put(appPrincipal.getCustomerId());
        //token返给app端
        params.setToken(token);
    }

    @Override
    public void logout() {
        cache.remove(holder.get());
    }

    /**
     * 获得当前登录用户
     * @return 当前登录用户id
     */
    @Override
    public AppPrincipal getAppPrincipal() {
        return  localPrincipal;
    }

    /**
     * 验证当前用户是否认证通过(是否登录)
     * @return 如果认证通过返回true，否则返回false.
     */
    @Override
    public boolean isAuthenticated() { return cache.check(holder.get()); }

    @Override
    public String getCustomerId() { return cache.get(holder.get()); }

    @Override
    public String getSessionId() {
        return holder.get();
    }

    public String getCid() { return getCustomerId(); }

    /**
     * 绑定TOKEN
     * @param request
     */
    public void bind(HttpServletRequest request) {
     String token = request.getHeader(YllConstants.TOKEN_NAME);
        if (StringUtils.isEmpty(token)) {
            holder.remove();
        } else {
            holder.set(token);
        }
    }

    /**
     * 移除当前线程的TOKEN
     */
    public void unbind() {
        holder.remove();
    }
}
