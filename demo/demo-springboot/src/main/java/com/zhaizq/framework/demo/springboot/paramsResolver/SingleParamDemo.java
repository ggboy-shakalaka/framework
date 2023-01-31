package com.zhaizq.framework.demo.springboot.paramsResolver;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SingleParamDemo implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SingleParamResolver());
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.PARAMETER})
    public @interface SingleParam {
        String value() default "";
    }

    public static class SingleParamResolver implements HandlerMethodArgumentResolver {
        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasMethodAnnotation(SingleParam.class)
                    || parameter.hasParameterAnnotation(SingleParam.class);
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
            JSONObject jsonObject = (JSONObject) webRequest.getAttribute("SINGLE_PARAM_JSON", 0);

            if (jsonObject == null) {
                HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
                assert request != null;

                BufferedReader reader = request.getReader();
                String body = reader.lines().collect(Collectors.joining("\n"));

                jsonObject = JSONObject.parseObject(body);
                request.setAttribute("SINGLE_PARAM_JSON", jsonObject);
            }

            String name = parameter.getParameterName();
            if (parameter.hasParameterAnnotation(SingleParam.class)) {
                SingleParam singleParam = parameter.getParameterAnnotation(SingleParam.class);
                assert singleParam != null;
                name = singleParam.value().isEmpty() ? name : singleParam.value();
            }

            return jsonObject == null ? null : jsonObject.getObject(name, parameter.getParameterType());
        }
    }
}
