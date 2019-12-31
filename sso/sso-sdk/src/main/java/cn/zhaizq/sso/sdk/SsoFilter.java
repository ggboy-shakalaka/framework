package cn.zhaizq.sso.sdk;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SsoFilter implements Filter {
    private String appId;
    private String server;
    private String login;
    private String logout;
    private String ignore;

    private SsoService ssoService;

    public void init(FilterConfig filterConfig) throws ServletException {
        appId = filterConfig.getInitParameter(Conf.APP_ID);
        server = filterConfig.getInitParameter(Conf.SERVER_PATH);
        login = filterConfig.getInitParameter(Conf.LOGIN_PATH);
        logout = filterConfig.getInitParameter(Conf.LOGOUT_PATH);
        ignore = filterConfig.getInitParameter(Conf.IGNORE_PATH);

        login = login == null || login.length() == 0 ? "/login" : login;
        logout = logout == null || logout.length() == 0 ? "/logout" : logout;
        ignore = ignore == null ? "" : ignore;

        ssoService = new SsoService(server, appId);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestUri = request.getRequestURI();

        if (SsoHelper.isMatch(login, requestUri)) {
            response.sendRedirect(ssoService.getLoginPath());
            return;
        }

        if (SsoHelper.isMatch(logout, requestUri)) {
            response.sendRedirect(ssoService.getLogoutPath());
            return;
        }

        for (String ignoreUrl : ignore.split(",")) {
            if (SsoHelper.isMatch(ignoreUrl, requestUri)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        String requestedSessionId = request.getRequestedSessionId();
        System.out.println(request.getRequestURI());
        System.out.println(requestedSessionId);
//
//        response.sendRedirect(server + logout);
//        Cookie[] cookies = request.getCookies();
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {
    }

    public static class Conf {
        public final static String APP_ID = "APP_ID";
        public final static String SERVER_PATH = "SERVER_PATH";
        public final static String LOGIN_PATH = "LOGIN_PATH";
        public final static String LOGOUT_PATH = "LOGOUT_PATH";
        public final static String IGNORE_PATH = "IGNORE_PATH";
    }
}