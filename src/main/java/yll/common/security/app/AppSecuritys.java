package yll.common.security.app;

import com.github.relucent.base.util.expection.GeneralException;

import javax.servlet.http.HttpServletRequest;

public interface AppSecuritys {

        void login(AppToken token) throws Exception;

        void logout();

        boolean isAuthenticated();

        String getCustomerId();

        String getSessionId();

        void bind(HttpServletRequest request);

        void unbind();

        AppPrincipal getAppPrincipal();
}
