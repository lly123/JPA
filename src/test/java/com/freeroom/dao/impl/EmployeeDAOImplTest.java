package com.freeroom.dao.impl;

import com.freeroom.dao.DepartmentDAO;
import com.freeroom.dao.EmployeeDAO;
import com.freeroom.domain.Department;
import com.freeroom.domain.Employee;
import com.freeroom.domain.EmployeeGrade;
import com.freeroom.domain.EmployeeVacationBookings;
import org.apache.commons.lang.time.DateUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class EmployeeDAOImplTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private DepartmentDAO departmentDAO;

    @Test
    public void should_find_out_employee_by_no() {
        createEmployee();

        entityManager.flush();
        entityManager.clear();

        Employee employee = employeeDAO.findByEmployeeNo("TW1158");
        assertThat(employee.getEmployeeName(), is("Richard Li"));
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeNo("TW1158");
        employee.setEmployeeName("Richard Li");
        employee.setGrade(EmployeeGrade.JUNIOR);
        employeeDAO.persist(employee);
        return employee;
    }

    @Test
    public void should_associate_employee_with_department() {
        Department department = new Department();
        department.setName("BMC");
        departmentDAO.persist(department);

        Employee employee = createEmployee();
        employee.setDepartment(department);

        entityManager.flush();
        entityManager.clear();

        employee = employeeDAO.findByEmployeeNo("TW1158");
        assertThat(employee.getDepartment(), is(department));

        department = departmentDAO.findByName("BMC");
        assertThat(department.getEmployees(), hasEntry(EmployeeGrade.JUNIOR, employee));
    }

    @Test
    public void should_add_booking_records() throws ParseException {
        Employee employee = createEmployee();

        EmployeeVacationBookings employeeVacationBookings = new EmployeeVacationBookings();
        employeeVacationBookings.setStartDate(DateUtils.parseDate("2010-05-08", new String[]{"yyyy-MM-dd"}));
        employeeVacationBookings.setDays(10);
        employee.getEmployeeVacationBookings().add(employeeVacationBookings);

        entityManager.flush();
        entityManager.clear();

        employee = employeeDAO.findByEmployeeNo("TW1158");
        assertThat(employee.getEmployeeVacationBookings().size(), Matchers.is(1));
    }

    @Test
    public void should_add_nickname() {
        Employee employee = createEmployee();
        employee.getNickNames().add("nick name");

        entityManager.flush();
        entityManager.clear();

        employee = employeeDAO.findByEmployeeNo("TW1158");
        assertThat(employee.getNickNames().size(), is(1));
    }

    @Test
    public void should_remove_nickname() {
        should_add_nickname();

        entityManager.flush();
        entityManager.clear();

        Employee employee = employeeDAO.findByEmployeeNo("TW1158");
        employee.getNickNames().remove("nick name");

        entityManager.flush();
        entityManager.clear();

        employee = employeeDAO.findByEmployeeNo("TW1158");
        assertThat(employee.getNickNames().size(), is(0));
    }

    @Test
    public void should_get_booking_records_in_order() throws ParseException {
        Employee employee = createEmployee();

        EmployeeVacationBookings employeeVacationBookings = new EmployeeVacationBookings();
        employeeVacationBookings.setStartDate(DateUtils.parseDate("2010-05-08", new String[]{"yyyy-MM-dd"}));
        employeeVacationBookings.setDays(2);
        employee.getEmployeeVacationBookings().add(employeeVacationBookings);

        employeeVacationBookings = new EmployeeVacationBookings();
        employeeVacationBookings.setStartDate(DateUtils.parseDate("2010-05-09", new String[]{"yyyy-MM-dd"}));
        employeeVacationBookings.setDays(12);
        employee.getEmployeeVacationBookings().add(employeeVacationBookings);

        entityManager.flush();
        entityManager.clear();

        employee = employeeDAO.findByEmployeeNo("TW1158");
        assertThat(employee.getEmployeeVacationBookings().size(), is(2));
        assertThat(employee.getEmployeeVacationBookings().get(0).getDays(), is(12));
        assertThat(employee.getEmployeeVacationBookings().get(1).getDays(), is(2));
    }

    @Test
    public void should_add_phone_numbers() {
        Employee employee = createEmployee();

        employee.getPhoneNumbers().put("MOBILE", "13911501188");
        employee.getPhoneNumbers().put("HOME", "66321188");

        entityManager.flush();
        entityManager.clear();

        employee = employeeDAO.findByEmployeeNo("TW1158");
        assertThat(employee.getPhoneNumbers().size(), is(2));
    }

    @Test
    public void should_return_employees_by_department() {
        should_associate_employee_with_department();

        Department department = departmentDAO.findByName("BMC");
        List<Employee> employees = employeeDAO.findByDepartment(department);
        assertThat(employees.size(), is(1));
    }
}
