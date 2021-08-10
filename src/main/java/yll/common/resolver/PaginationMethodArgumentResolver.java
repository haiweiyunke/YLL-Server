package yll.common.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.github.relucent.base.util.convert.ConvertUtil;
import com.github.relucent.base.util.page.Pagination;



/**
 * 分页视图适配器
 */
public class PaginationMethodArgumentResolver implements HandlerMethodArgumentResolver {

    // ==============================Fields===========================================
    private static final String START_KEY = ":start";
    private static final String LIMIT_KEY = ":limit";
    private static final int DEFAULT_LIMIT = 10;
    private static final int MAX_LIMIT = 1000;

    // ==============================Methods==========================================
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return Pagination.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) throws Exception {
        int start = ConvertUtil.toInteger(webRequest.getParameter(START_KEY), 0);
        int limit = ConvertUtil.toInteger(webRequest.getParameter(LIMIT_KEY), DEFAULT_LIMIT);
        limit = Math.min(limit, MAX_LIMIT);// 防止页面前台一次查询数量过大，限定limit不能大于MAX_LIMIT
        return new Pagination(start, limit);
    }
}
