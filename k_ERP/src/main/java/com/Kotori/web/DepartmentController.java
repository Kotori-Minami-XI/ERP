package com.Kotori.web;

import com.Kotori.domain.Department;
import com.Kotori.mapper.DepartmentMapper;
import com.Kotori.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /***
     * @brief  Obtain all departments from database
     * @param  null
     * @return list of departments wrapped in json
     */
    @RequestMapping("/getDepartmentList.action")
    @ResponseBody
    public List<Department> getDepartmentList() {
        List<Department> list = departmentService.getAllDepartment();
        return list;
    }

}
