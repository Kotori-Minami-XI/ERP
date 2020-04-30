package com.Kotori.service;


import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;

public interface EmployeeService {
    public PageListResult getEmployeeList();

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void updateState(Long id);
}
