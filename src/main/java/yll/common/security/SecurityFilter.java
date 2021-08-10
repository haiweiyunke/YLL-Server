package yll.common.security;

import com.github.relucent.base.plug.security.Principal;
import com.github.relucent.base.util.json.JsonUtil;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.web.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import yll.common.security.app.AppPrincipal;
import yll.common.security.app.AppSecuritys;
import yll.common.security.app.AppSecuritysUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 安全控制过滤器
 * （登录使用）
 */
@Slf4j
public class SecurityFilter implements Filter {

    @Autowired
    private AppSecuritys securitys;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = WebUtil.getPathWithinApplication(request);

        boolean authorized = false;
        if (path.startsWith("/app/") || path.startsWith("/oauth/") ){
            AppSecuritysUtil.bind(request);
            //app
            authorizedForApp(request, response, chain,authorized, path);
        } else{
            //后台管理
            authorizedForAdmin(request, response, chain,authorized, path);
        }

    }

    /**
     * 后台管理使用
     * @param path
     * @return
     */
    private boolean authc(String path) {
        if (path.startsWith("/s/")//
                || path.equals("/shutdown") //
                || path.startsWith("/login.html") //
                || path.startsWith("/rest/login") //
                || path.startsWith("/rest/logout")//
                || path.startsWith("/rest/file/preview")//
                || path.startsWith("/rest/msPath")//后台cos文件夹路径
                || path.startsWith("/share/share.html")//分享页面
                || path.startsWith("/office.html")//官网页面
                || path.startsWith("/main.html")//官网页面
                || path.startsWith("/favicon.ico")//tag图标
                || path.indexOf("/contract-back.html") != -1    //合同回调页面
                || path.startsWith("/api/")) {
            return false;
        }
        return true;
    }

    /**
     * APP使用
     * @param path
     * @return
     */
    private boolean authcForApp(String path) {
        if (path.indexOf("/app/login") != -1
            || path.indexOf("/app/code") != -1
            || path.indexOf("/app/check-code") != -1
            || path.indexOf("/app/preview") != -1
            || path.indexOf("/app/information/") != -1//资讯
            || path.indexOf("/app/slide/") != -1//混合轮播图
            || path.indexOf("/app/download") != -1//安卓APP下载
            || path.indexOf("/app/update") != -1//安卓APP更新
            || path.indexOf("/oauth/test") != -1
            || path.indexOf("/app/chart/export") != -1
            //|| path.indexOf("/app/") != -1

            //演娱，展示去掉token
            || path.indexOf("/app/dic") != -1
            || path.indexOf("/app/area/list") != -1
            || path.indexOf("/app/cos-keys") != -1
            || path.indexOf("/app/celebrity/list") != -1
            || path.indexOf("/app/celebrity/hot/list") != -1
            || path.indexOf("/app/celebrity/out-dic") != -1
            || path.indexOf("/app/celebrity/dic") != -1
            || path.indexOf("/app/celebrity/platform") != -1
            || path.indexOf("/app/celebrity/") != -1
            || path.indexOf("/app/dynamic/list") != -1
            || path.indexOf("/app/dynamic/celebrity/list") != -1
            || path.indexOf("/app/celebrity/slide/list") != -1
            || path.indexOf("/app/dynamic/list") != -1
            || path.indexOf("/app/comment/list") != -1
            || path.indexOf("/app/information/list") != -1
            || path.indexOf("/app/information/") != -1
            || path.indexOf("/app/information/slide/list") != -1
            || path.indexOf("/app/train/list") != -1
            || path.indexOf("/app/train/") != -1
            || path.indexOf("/app/train/slide/list") != -1
            || path.indexOf("/app/conferences/list") != -1
            || path.indexOf("/app/conferences/slide/list") != -1
            || path.indexOf("/app/conferences/extension/list") != -1
            || path.indexOf("/app/task/list") != -1
            || path.indexOf("/app/task/dic") != -1
            //招聘
            || path.indexOf("/app/recruit/details/enterprise") != -1
            || path.indexOf("/app/recruit/details/celebrity") != -1
            || path.indexOf("/app/recruit/index") != -1
            || path.indexOf("/app/recruit/condition") != -1
            || path.indexOf("/app/recruit/enterprise/details/get") != -1
            //和签-签名
            || path.indexOf("/app/process/sign") != -1
            //中台测试
            || path.indexOf("/app/recruit/post/test") != -1
            || path.indexOf("/app/recruit/get/test") != -1

        ) {
            //不需要登录
            return false;
        }
        return true;
    }

    /**
     *  后台管理拦截
     * @param request
     * @param response
     * @param chain
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private void authorizedForAdmin(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Boolean authorized, String path) throws IOException, ServletException{

        if("/".equals(path)){
            //跳转官网
            String ctx = WebUtil.getContextPath(request);
            request.getRequestDispatcher(ctx + "/main.html").forward(request,response);
            return ;
        } else if (!authc(path)) {
            authorized = true;
        } else {
            Principal principal = SecurityImplementor.getPrincipal(request.getSession());
            if (!Principal.NONE.equals(principal)) {
                authorized = true;
            }
        }

        boolean isRest = isRest(path);

        if (isRest) {
            WebUtil.setNoCacheHeader(response);
        }

        if(authorized){
            doChain(request, response, chain);
            return;
        }

        if (isRest) {
            response.setHeader("session-timeout-signal", "is-timeout");
            Result<?> result = Result.ofMessage(403, "SESSION超时");
            String json = JsonUtil.encode(result);
            WebUtil.writeJson(json, request, response);
            return;
        }

        String ctx = WebUtil.getContextPath(request);
        response.sendRedirect(ctx + "/login.html");

    }

    /**
     *  APP拦截
     * @param request
     * @param response
     * @param chain
     * @return
     * @throws IOException
     * @throws ServletException
     */
    private void authorizedForApp(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Boolean authorized, String path) throws IOException, ServletException{

        if (!authcForApp(path)) {
            //无需登录接口
            authorized = true;
        } else if(securitys.isAuthenticated()){
                //已登录
                authorized = true;
        }

        WebUtil.setNoCacheHeader(response);

        if(authorized){
            doChain(request, response, chain);
        } else{
            response.setHeader("session-timeout-signal", "is-timeout");
            Result<?> result = Result.ofMessage(403, "TOKEN超时");
            String json = JsonUtil.encode(result);
            WebUtil.writeJson(json, request, response);
        }
    }

    private boolean isRest(String path) {
        return path.startsWith("/rest/");
    }

    @Override
    public void init(FilterConfig config) throws ServletException {}

    @Override
    public void destroy() {}

    private void doChain(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        try {
            chain.doFilter(request, response);
        } catch (ClientAbortException e) {
            // Ignore
            log.warn("#", e.toString());
        }
    }
}
