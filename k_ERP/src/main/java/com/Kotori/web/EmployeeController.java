package com.Kotori.web;

import com.Kotori.domain.AjaxResult;
import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /***
     * @brief  Head to employee.jsp
     * @params null
     * @return View location of employee.jsp
     */
    @RequestMapping("/employee.action")
    @RequiresPermissions("employee:index")
    public String employee() {
        return "/WEB-INF/view/employee.jsp";
    }

    /***
     * @brief  Obtain all employees in database
     * @params null
     * @return Divide all results into pages and return one page
     */
    @RequestMapping("/getEmployeeList.action")
    @ResponseBody
    public PageListResult getEmployeeList(QueryViewObject queryViewObject) {
        PageListResult page = employeeService.getEmployeeList(queryViewObject);
        return page;
    }

    /***
     * @brief  Add a new employee to database
     * @params employee
     * @return Callback info for inserting the new employee
     */
    @RequestMapping("/saveEmployee.action")
    @ResponseBody
    public AjaxResult saveEmployee(Employee employee) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            // Always on duty when adding a new employee
            employee.setState(true);
            employeeService.saveEmployee(employee);
            ajaxResult.setMsg("保存成功");
            ajaxResult.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMsg("保存失败");
            ajaxResult.setResult(false);
        }
        return ajaxResult;
    }

    /***
     * @brief  Update employee info
     * @params employee
     * @return Callback info for Updating the employee
     */
    @RequestMapping("/updateEmployee.action")
    @ResponseBody
    public AjaxResult updateEmployee(Employee employee) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            employeeService.updateEmployee(employee);
            ajaxResult.setMsg("更新成功");
            ajaxResult.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMsg("更新失败");
            ajaxResult.setResult(false);
        }
        return ajaxResult;
    }

    /***
     * @brief  Switch employee state
     * @params id
     * @return Callback info for Updating the state
     */
    @RequestMapping("/updateState.action")
    @ResponseBody
    public AjaxResult updateState(Long id) {
        AjaxResult ajaxResult = new AjaxResult();
        try {
            employeeService.updateState(id);
            ajaxResult.setMsg("更新成功");
            ajaxResult.setResult(true);
        } catch (Exception e) {
            e.printStackTrace();
            ajaxResult.setMsg("更新失败");
            ajaxResult.setResult(false);
        }
        return ajaxResult;
    }

    /***
     * @brief  vague query to retrieve employees whose names are similar to keyword
     * @params queryViewObject
     * @return List of employees meet the requirement
     */
    @RequestMapping("/vagueQueryEmployee.action")
    @ResponseBody
    public PageListResult vagueQueryEmployee(QueryViewObject queryViewObject) {
        System.out.println(queryViewObject);
        PageListResult page = employeeService.vagueQueryEmployee(queryViewObject);
        return page;
    }

    /***
     *
     * @brief  Handle shiro exception that the current subject has no permission to visit resource.
     *         A json or an http response will be returned
     * @params handlerMethod response
     * @throws IOException
     */
    @ExceptionHandler (AuthorizationException.class)
    public void handleShiroException(HandlerMethod handlerMethod, HttpServletResponse response) throws IOException {
        ResponseBody methodAnnotation = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (null != methodAnnotation) {
            AjaxResult ajaxResult = new AjaxResult();
            ajaxResult.setMsg("没有权限访问");
            ajaxResult.setResult(false);
            String jsonString = new ObjectMapper().writeValueAsString(ajaxResult);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonString);
        } else {
            response.sendRedirect("illegalAccess.jsp");
        }
    }
}
