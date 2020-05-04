package com.Kotori.mapper;

import com.Kotori.domain.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    void updateStateById(Long id);

    List<Employee> vagueQueryEmployee(String keyword);

    void insertEmployeeAndRoleRel(@Param("eid") Long eid, @Param("rid") Long rid);

    void deleteEmployeeAndRoleRel(Long eid);

    Employee getEmployeeByUsername(String username);

    List<String> getRolesById(Long id);

    List<String> getPermissionsById(Long id);

    List<Employee> getAllEmployee();
}