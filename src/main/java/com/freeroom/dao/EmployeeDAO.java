package com.freeroom.dao;

import com.freeroom.domain.Department;
import com.freeroom.domain.Employee;

import java.util.List;

public interface EmployeeDAO extends GenericPersistenceDAO<Employee, Integer> {
    Employee findByEmployeeNo(String employeeNo);
    List<Employee> findByEmployeeNos(List<String> employeeNos);
    List<Employee> findByDepartment(Department department);
}
