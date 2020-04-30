package com.Kotori.service;

import com.Kotori.domain.Department;
import org.springframework.stereotype.Service;

import java.util.List;

public interface DepartmentService {
    public List<Department> getAllDepartment();
}
