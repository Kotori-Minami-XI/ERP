package com.Kotori.aspect;

import com.Kotori.domain.Systemlog;
import com.Kotori.mapper.SystemlogMapper;
import com.Kotori.util.RequestUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class SystemAspect {
    @Autowired
    private SystemlogMapper systemlogMapper;

    /***
     * @brief  Record logs after database operation
     * @params joinPoint
     * @throws JsonProcessingException
     */
    public void writeLog(JoinPoint joinPoint) throws JsonProcessingException {
        Systemlog systemlog = new Systemlog();

        // ThreadLocal to obtain the request that is stored in interceptor
        HttpServletRequest request = RequestUtil.getRequest();

        // Obtain IP from request
        String ip = null;
        if (null != request) {
            ip = request.getRemoteAddr();
        }

        // Obtain method and params info from joint point
        String methodPath = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String params = new ObjectMapper().writeValueAsString(joinPoint.getArgs());

        // Set system log
        systemlog.setOptime(new Date());
        systemlog.setIp(ip);
        systemlog.setFunction(methodPath + ":" + methodName);
        systemlog.setParams(params);

        systemlogMapper.insert(systemlog);
    }
}
