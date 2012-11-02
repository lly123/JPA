package com.freeroom.dao.impl;

import com.freeroom.dao.EmployeeDAO;
import com.freeroom.dao.impl.UniqueNameEntityDaoImpl;
import com.freeroom.domain.Department;
import com.freeroom.domain.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Flushable;
import java.util.List;

@Service
public class EmployeeDAOImpl extends UniqueNameEntityDaoImpl<Employee, Integer> implements EmployeeDAO{

    public Employee findByEmployeeNo(String employeeNo) {
        return findByName(employeeNo);
    }

    public List<Employee> findByEmployeeNos(List<String> employeeNos) {
        return findByNames(employeeNos);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Employee> findByDepartment(Department department) {
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findByDepartment", Employee.class);
        return query.setParameter("department", department).getResultList();
    }
}

