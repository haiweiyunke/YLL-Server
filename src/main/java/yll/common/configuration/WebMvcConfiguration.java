package yll.common.configuration;

import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.relucent.base.plug.jackson.MyObjectMapper;
import com.github.relucent.base.util.lang.DateUtil;

import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import yll.common.resolver.MapReferenceArgumentResolver;
import yll.common.resolver.PaginationMethodArgumentResolver;
import yll.common.security.SecurityHandlerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityHandlerInterceptor()).addPathPatterns("/rest/**");
    }

    /**
     * <pre>
     *  <mvc:argument-resolvers>
     *      <bean class="edmp.common.resolver.PaginationMethodArgumentResolver" />
     *  </mvc:argument-resolvers>
     * </pre>
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MapReferenceArgumentResolver());
        resolvers.add(new PaginationMethodArgumentResolver());
    }

    @Bean("objectMapper")
    @Primary
    public ObjectMapper objectMapper() {
        return MyObjectMapper.INSTANCE;
    }

    @Bean
    public Converter<String, Date> dateConverter() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                return DateUtil.parse(source);
            }
        };
    }

    /**
     * websocket
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
