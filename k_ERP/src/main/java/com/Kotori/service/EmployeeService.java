package com.Kotori.service;


import com.Kotori.domain.Employee;
import com.Kotori.domain.PageListResult;
import com.Kotori.domain.QueryViewObject;

import java.util.List;

public interface EmployeeService {
    public PageListResult getEmployeeList(QueryViewObject queryViewObject);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void updateState(Long id);

    PageListResult vagueQueryEmployee(QueryViewObject queryViewObject);

    Employee getEmployeeByUsername(String username);

    List<String> getRolesById(Long id);

    List<String> getPermissionsById(Long id);
}
