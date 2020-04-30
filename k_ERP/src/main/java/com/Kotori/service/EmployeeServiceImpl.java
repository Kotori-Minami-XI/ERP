package com.Kotori.service;

import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.mapper.EmployeeMapper;
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
    public PageListResult getEmployeeList() {
        Page<Object> page = PageHelper.startPage(1, 5);
        List<Employee> employeeList = employeeMapper.selectAll();

        PageListResult pageListResult = new PageListResult();
        pageListResult.setTotal(page.getTotal());
        pageListResult.setRows(employeeList);
        return pageListResult;
    }

    @Override
    public void saveEmployee(Employee employee) {
        employeeMapper.insert(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employeeMapper.updateByPrimaryKey(employee);
    }

    @Override
    public void updateState(Long id) {
        employeeMapper.updateStateById(id);
    }
}
