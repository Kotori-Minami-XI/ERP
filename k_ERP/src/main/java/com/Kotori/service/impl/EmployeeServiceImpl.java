package com.Kotori.service.impl;

import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;
import com.Kotori.domain.Role;
import com.Kotori.mapper.EmployeeMapper;
import com.Kotori.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public PageListResult getEmployeePage(QueryViewObject queryViewObject) {
        Page<Object> page = PageHelper.startPage(queryViewObject.getPage(), queryViewObject.getRows());
        List<Employee> employeeList = employeeMapper.selectAll();

        PageListResult pageListResult = new PageListResult();
        pageListResult.setTotal(page.getTotal());
        pageListResult.setRows(employeeList);
        return pageListResult;
    }

    @Override
    public void saveEmployee(Employee employee) {
        // Insert the new employee and have its id stored in employee
        employeeMapper.insert(employee);

        // Insert relation between employee and role
        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeAndRoleRel(employee.getId(), role.getRid());
        }
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.updateByPrimaryKey(employee);

        employeeMapper.deleteEmployeeAndRoleRel(employee.getId());

        for (Role role : employee.getRoles()) {
            employeeMapper.insertEmployeeAndRoleRel(employee.getId(), role.getRid());
        }
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateStateById(id);
    }

    @Override
    public PageListResult vagueQueryEmployee(QueryViewObject queryViewObject) {
        Page<Object> page = PageHelper.startPage(queryViewObject.getPage(), queryViewObject.getRows());
        List<Employee> employeeList = employeeMapper.vagueQueryEmployee(queryViewObject.getKeyword());

        PageListResult pageListResult = new PageListResult();
        pageListResult.setTotal(page.getTotal());
        pageListResult.setRows(employeeList);

        System.out.println(employeeList);
        return pageListResult;
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeMapper.getEmployeeByUsername(username);
    }

    @Override
    public List<String> getRolesById(Long id) {
        return employeeMapper.getRolesById(id);
    }

    @Override
    public List<String> getPermissionsById(Long id) {
        return employeeMapper.getPermissionsById(id);
    }

    @Override
    public List<Employee> getEmployeeList() {
        return employeeMapper.getAllEmployee();
    }
}
