package com.Kotori.service;

import com.Kotori.domain.Employee;
import com.Kotori.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> getEmployeeList() {
        System.out.println("---------------------");
        List<Employee> list = employeeMapper.selectAll();
        System.out.println(list);
        return list;
    }
}
