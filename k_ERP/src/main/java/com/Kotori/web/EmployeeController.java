package com.Kotori.web;

import com.Kotori.domain.AjaxResult;
import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
