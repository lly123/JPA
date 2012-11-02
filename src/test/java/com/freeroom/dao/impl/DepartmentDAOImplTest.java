package com.freeroom.dao.impl;

import com.freeroom.dao.DepartmentDAO;
import com.freeroom.dao.EmployeeDAO;
import com.freeroom.domain.Department;
import com.freeroom.domain.Employee;
import com.freeroom.domain.EmployeeGrade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class DepartmentDAOImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Autowired
    private EmployeeDAO employeeDAO;

    private Department createDepartment() {
        Department department = new Department();
        department.setName("BMC");
        departmentDAO.persist(department);
        return department;
    }

    private Employee createEmployee(String employeeNo, String employeeName,
                                    EmployeeGrade employeeGrade, Department department) {
        Employee employee = new Employee();
        employee.setEmployeeNo(employeeNo);
        employee.setEmployeeName(employeeName);
        employee.setGrade(employeeGrade);
        employee.setDepartment(department);
        employeeDAO.persist(employee);
        return employee;
    }

    @Test
    public void should_find_out_department_by_name() {
        createDepartment();

        entityManager.flush();
        entityManager.clear();

        Department department = departmentDAO.findByName("BMC");
        assertThat(department, notNullValue());
    }

    @Test
    public void should_get_different_grade_employees() {
        Department department = createDepartment();
        createEmployee("TW001", "Roys", EmployeeGrade.PRINCIPAL, department);
        createEmployee("TW002", "Rick", EmployeeGrade.LEAD, department);
        createEmployee("TW002", "Banana", EmployeeGrade.JUNIOR, department);

        entityManager.flush();
        entityManager.clear();

        department = departmentDAO.findByName("BMC");
        assertThat(department, notNullValue());
        assertThat(department.getEmployees().size(), is(3));

        assertThat(department.getEmployees().get(EmployeeGrade.LEAD).getEmployeeName(), is("Rick"));
        assertThat(department.getEmployees().get(EmployeeGrade.JUNIOR).getEmployeeName(), is("Banana"));
    }

}
