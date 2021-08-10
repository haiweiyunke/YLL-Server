package yll.common.security.app;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 *  APP访问权限工具
 */
public class AppSecuritysUtil implements Serializable {

    protected static volatile AppSecuritys appSecuritys;

    public static void login(AppToken appToken) throws Exception { appSecuritys.login(appToken); }

    public static void logout(){
        appSecuritys.logout();
    }

    public static void bind(HttpServletRequest request){ appSecuritys.bind(request);  }

    public static void unbind(){ appSecuritys.unbind(); }

    public static String getCustomerId(){
        return appSecuritys.getCustomerId();
    }

    /**
     * 获得当前登录用户
     * @return 当前登录用户
     */
    public static AppPrincipal getAppPrincipal(){ return appSecuritys.getAppPrincipal(); }

    // ==============================IOC_Methods======================================
    @Autowired
    protected synchronized void setAppSecuritys(AppSecuritys appSecuritys) {
        AppSecuritysUtil.appSecuritys = appSecuritys;
    }
}
