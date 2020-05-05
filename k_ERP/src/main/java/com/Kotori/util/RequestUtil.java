package com.Kotori.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static ThreadLocal<HttpServletRequest> local = new ThreadLocal();

    /***
     * @brief  Obtain http request from ThreadLocal
     * @return Stored http request
     */
    public static HttpServletRequest getRequest(){
        return local.get();
    }

    /***
     * @brief  Store http request in TheadLocal so that thr request could be used
     *         across different modules or classes
     * @return null
     */
    public static void setRequest(HttpServletRequest httpServletRequest){
        local.set(httpServletRequest);
    }

}
