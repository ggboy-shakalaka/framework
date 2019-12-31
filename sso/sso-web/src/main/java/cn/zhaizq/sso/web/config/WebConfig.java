package cn.zhaizq.sso.web.config;

import cn.zhaizq.sso.sdk.SsoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    private String server = "https://sso.zhaizq.cn";
    @Bean
    public FilterRegistrationBean ssoFilter() {
        FilterRegistrationBean<SsoFilter> filterRegistrationBean = new FilterRegistrationBean<SsoFilter>();
        filterRegistrationBean.setFilter(new SsoFilter());
        filterRegistrationBean.addInitParameter(SsoFilter.Conf.SERVER_PATH, server);
        filterRegistrationBean.addInitParameter(SsoFilter.Conf.IGNORE_PATH, "/api");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setEnabled(true);
        return filterRegistrationBean;
    }
}
