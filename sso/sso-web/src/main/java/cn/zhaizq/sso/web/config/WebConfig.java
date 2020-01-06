package cn.zhaizq.sso.web.config;

import cn.zhaizq.sso.sdk.SsoFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    private String server = "http://localhost";
    @Bean
    public FilterRegistrationBean ssoFilter() {
        FilterRegistrationBean<SsoFilter> filterRegistrationBean = new FilterRegistrationBean<SsoFilter>();
        filterRegistrationBean.setFilter(new SsoFilter());
        filterRegistrationBean.addInitParameter(SsoFilter.Conf.SERVER_PATH, server);
        filterRegistrationBean.addInitParameter(SsoFilter.Conf.APP_ID, "zhaizq.appid");
        filterRegistrationBean.addInitParameter(SsoFilter.Conf.IGNORE_PATH, "/login.html,/api,/favicon.ico");
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setEnabled(true);
        return filterRegistrationBean;
    }
}
