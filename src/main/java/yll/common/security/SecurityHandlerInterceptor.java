package yll.common.security;

import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.util.json.JsonUtil;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.web.WebUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import yll.common.BaseConstants.Ids;
import yll.common.annotations.PermissionAx;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局拦截
 */
public class SecurityHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = WebUtil.getPathWithinApplication(request);
        if( path.startsWith("/app/")){
            //app
            return app(request, response, handler);
        } else{
            //后台管理系统
            return admin(request, response, handler);
        }
    }

    private boolean intersect(String[] array, String[] valueToFinds) {
        for (String value : array) {
            for (String valueToFind : valueToFinds) {
                if (valueToFind.equals(value)) {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * admin后台管理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    private boolean admin(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            PermissionAx ax = hm.getMethodAnnotation(PermissionAx.class);
            if (ax != null) {
                Principal principal = SecurityImplementor.getPrincipal(request.getSession());
                String userId = principal.getUserId();
                String[] permissionIds = principal.getPermissionIds();
                if (!Ids.ADMIN_ID.equals(userId) && !intersect(ax.value(), permissionIds)) {
                    response.setHeader("unauthorized-signal", "403");
                    Class<?> returnType = (Class<?>) hm.getReturnType().getGenericParameterType();
                    if (Result.class.isAssignableFrom(returnType)) {
                        Result<?> result = Result.ofMessage(403, "没有访问该资源的权限");
                        String json = JsonUtil.encode(result);
                        WebUtil.writeJson(json, request, response);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * app
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    private boolean app(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        return true;
    }
}
