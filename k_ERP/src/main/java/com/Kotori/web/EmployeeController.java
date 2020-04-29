package com.Kotori.web;

import com.Kotori.mapper.EmployeeMapper;
import com.Kotori.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/employee.action")
    public String employee() {
        return "/WEB-INF/view/employee.jsp";
    }

    @RequestMapping("/getEmployeeList.action")
    public String getEmployeeList() {
        employeeService.getEmployeeList();
        return "";
    }
}
