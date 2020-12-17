package com.test.demo.config;

import cn.hutool.core.util.StrUtil;
import com.test.demo.entity.User;
import com.test.demo.entity.UserThreadLocal;
import com.test.demo.utils.TokenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    private final String HTTP_HEADER_TOKEN_KEY = "token";

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 基于URL实现的拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!isHandlerMethod(handler)) {
            return true;
        }
        String clientToken = getToken(request);
        if (StrUtil.isBlank(clientToken)) {
            throw new RuntimeException("token不能为空");
        }
        User user = TokenUtil.getUserFromToken(clientToken);
        UserThreadLocal.set(user);
        return true;

    }


    protected String getToken(HttpServletRequest request) {
        String token = request.getHeader(HTTP_HEADER_TOKEN_KEY);
        return token;
    }

    protected boolean isHandlerMethod(Object handler) {
        return (handler instanceof HandlerMethod);
    }
}