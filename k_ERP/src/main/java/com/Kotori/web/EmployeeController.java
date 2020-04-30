package com.Kotori.web;

import com.Kotori.domain.PageListResult;
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
     * @param  null
     * @return View location of employee.jsp
     */
    @RequestMapping("/employee.action")
    public String employee() {
        return "/WEB-INF/view/employee.jsp";
    }

    /***
     * @brief  Obtain all employees in database
     * @param  null
     * @return Divide all results into pages and return one page
     */
    @RequestMapping("/getEmployeeList.action")
    @ResponseBody
    public PageListResult getEmployeeList() {
        PageListResult page = employeeService.getEmployeeList();
        return page;
    }
}
