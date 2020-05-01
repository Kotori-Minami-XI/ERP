package com.Kotori.service;


import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;

public interface EmployeeService {
    public PageListResult getEmployeeList(QueryViewObject queryViewObject);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void updateState(Long id);

    PageListResult vagueQueryEmployee(QueryViewObject queryViewObject);
}
