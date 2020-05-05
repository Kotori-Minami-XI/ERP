package com.Kotori.interceptor;


import com.Kotori.util.RequestUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor {
    /***
     * @brief  Store http request in TheadLocal so that thr request could be used
     *         across different modules or classes
     * @params request, response, handler
     * @return True if no exception is thrown
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RequestUtil.setRequest(request);
        return true;
    }
}
