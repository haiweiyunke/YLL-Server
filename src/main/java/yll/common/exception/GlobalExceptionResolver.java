package yll.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.github.relucent.base.util.expection.ErrorType;
import com.github.relucent.base.util.expection.GeneralException;
import com.github.relucent.base.util.json.JsonUtil;
import com.github.relucent.base.util.model.Result;
import com.github.relucent.base.util.web.WebUtil;

import lombok.extern.slf4j.Slf4j;



/**
 * spring_mvc 异常处理
 */
@Component
@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    // ==============================Fields===========================================
    protected String errorPage = "/_common/500";

    // ==============================Methods==========================================
    /**
     * 处理异常
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logException(ex);
        if (isRestful(request)) {
            try {
                String message = ex.getMessage();
                if (message == null) {
                    message = "Service Error !";
                }
                Result<?> result = Result.error(message);
                String json = JsonUtil.encode(result);
                WebUtil.writeJson(json, request, response);
            } catch (IllegalStateException e) {
                log.warn("!", e);
            } catch (Exception e) {
                log.error("!", e);
            }
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put("errorMsg", ex.getMessage());
            return new ModelAndView(errorPage, map);
        }
        return new ModelAndView();
    }

    /**
     * 异常日志
     * @param e 异常
     */
    protected void logException(Exception e) {
        if (e instanceof GeneralException && ErrorType.PROMPT.equals(((GeneralException) e).getType())) {
            log.warn(e.getMessage());
        } else if (e instanceof org.apache.catalina.connector.ClientAbortException) {
            log.warn(e.toString());
        } else {
            log.error("!", e);
        }
    }

    protected boolean isRestful(HttpServletRequest request) {
        return WebUtil.isAjax(request);
    }

    // ==============================IocMethods=======================================
    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
