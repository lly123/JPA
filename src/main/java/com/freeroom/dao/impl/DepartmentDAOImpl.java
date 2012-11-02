package com.freeroom.dao.impl;

import com.freeroom.dao.DepartmentDAO;
import com.freeroom.domain.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentDAOImpl extends UniqueNameEntityDaoImpl<Department, Integer> implements DepartmentDAO {
    public Department findByName(String name) {
        return super.findByName(name);
    }

    public List<Department> findByNames(List<String> names) {
        return findByNames(names);
    }
}
