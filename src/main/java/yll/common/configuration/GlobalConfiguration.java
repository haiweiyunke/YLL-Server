package yll.common.configuration;

import com.github.relucent.base.plug.security.Securitys;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import yll.common.security.AuthRealm;
import yll.common.security.SecurityFilter;
import yll.common.security.SecurityImplementor;
import yll.common.security.app.AppRealm;
import yll.common.security.app.AppSecurityImplementor;
import yll.common.security.app.AppSecuritys;
import yll.common.security.app.AppSecuritysUtil;
import yll.common.thymeleaf.CustomThymeleafDialect;

import javax.annotation.PostConstruct;

/**
 * 项目公用配置
 * @author YYL
 */
@Configuration
public class GlobalConfiguration {

    /** 权限信息工具 */
    @Primary
    @Bean
    public Securitys securitys() {
        return new SecurityImplementor();
    }

    /** 密码编码器 */
    @Primary
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /** 安全过滤器(注册) */
    @Bean
    public FilterRegistrationBean<SecurityFilter> securityFilterRegistration() {
        FilterRegistrationBean<SecurityFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(securityFilter());
        registration.addUrlPatterns("/*");
        registration.setName("security_filter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

    /** 安全过滤器 */
    @Primary
    @Bean
    public SecurityFilter securityFilter() {
        return new SecurityFilter();
    }

    /** APP登录信息工具 */
    @Primary
    @Bean
    public AppSecuritys appSecuritys() {
        return new AppSecurityImplementor();
    }

    /** APP认证领域类*/
    @Bean
    public AppRealm appRealm() { return new AppRealm(); }

    /** 认证领域类 */
    @Bean
    public AuthRealm authRealm() {
        return new AuthRealm();
    }

    /** 自定义_Thymeleaf标签 */
    @Bean
    public CustomThymeleafDialect customThymeleafDialect() {
        return new CustomThymeleafDialect(securitys());
    }

    @PostConstruct
    public void postConstruct() {
        new AppSecuritysUtil() {
            {
                setAppSecuritys(appSecuritys());
            }
        };
    }

}
