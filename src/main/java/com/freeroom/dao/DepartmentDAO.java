package com.freeroom.dao;

import com.freeroom.domain.Department;
import com.freeroom.domain.Employee;
import com.freeroom.domain.UniqueNameEntity;

import java.util.List;

public interface DepartmentDAO extends GenericPersistenceDAO<Department, Integer> {
    Department findByName(String name);
    List<Department> findByNames(List<String> names);
}
