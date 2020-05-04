package com.Kotori.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static ThreadLocal<HttpServletRequest> local = new ThreadLocal();

    public static HttpServletRequest getRequest(){
        return local.get();
    }

    public static void setRequest(HttpServletRequest httpServletRequest){
        local.set(httpServletRequest);
    }

}
