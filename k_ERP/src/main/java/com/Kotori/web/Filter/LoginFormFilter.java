package com.Kotori.web.Filter;

import com.Kotori.domain.AjaxResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class LoginFormFilter extends FormAuthenticationFilter {

    /***
     * @brief  Set callback info when login procedure succeeds
     * @params token, subject, request, response
     * @return A response which contains an AjaxResult in json format
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setResult(true);
        ajaxResult.setMsg("认证成功");
        // Convert ajaxResult into json string
        String jsonString = new ObjectMapper().writeValueAsString(ajaxResult);

        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonString);
        return false;
    }

    /***
     * @brief  Set callback info when login procedure fails
     * @params token, subject, request, response
     * @return A response which contains an AjaxResult in json format
     * @throws Exception
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setMsg("认证失败");

        // Set msg for different exception
        if (null != e) {
            String exceptionName = e.getClass().getName();
            if (exceptionName.equals(UnknownAccountException.class.getName())) {
                ajaxResult.setMsg("用户不存在");
            } else if (exceptionName.equals(IncorrectCredentialsException.class.getName())) {
                ajaxResult.setMsg("密码不正确");
            } else {
                ajaxResult.setMsg("未知错误");
            }
        }

        try {
            // Convert ajaxResult into json string
            String jsonString = new ObjectMapper().writeValueAsString(ajaxResult);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
