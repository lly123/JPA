package com.freeroom.domain;

import com.freeroom.dao.EmployeeDAO;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.Console;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.hibernate.validator.util.CollectionHelper.asSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@Transactional
public class PackageTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void should_create_employee_project_relation_from_employee_side() {
        Project project = new Project();
        project.setName("BMC");
        entityManager.persist(project);

        Employee employee = new Employee();
        employee.setEmployeeNo("TW1158");
        employee.setEmployeeName("Richard Li");
        employee.setGrade(EmployeeGrade.JUNIOR);
        employee.setProject(project);
        entityManager.persist(employee);

        entityManager.flush();
        entityManager.clear();

        project = entityManager.find(Project.class, project.getId());
        assertThat(project.getEmployees().size(), is(1));
        assertThat(project.getEmployees().iterator().next(), is(employee));
    }

    @Test
    public void should_create_employee_project_relation_from_project_side() {
        Employee employee = new Employee();
        employee.setEmployeeNo("TW1158");
        employee.setEmployeeName("Richard Li");
        employee.setGrade(EmployeeGrade.JUNIOR);
        entityManager.persist(employee);

        Project project = new Project();
        project.setName("BMC");
        project.addEmployees(asSet(employee));
        entityManager.persist(project);

        entityManager.flush();
        entityManager.clear();

        project = entityManager.find(Project.class, project.getId());
        assertThat(project.getEmployees().size(), is(1));
        assertThat(project.getEmployees().iterator().next(), is(employee));
    }

    @Test
    public void should_remove_employee_project_relation_from_employee_side() {
        should_create_employee_project_relation_from_employee_side();

        entityManager.flush();
        entityManager.clear();

        Employee employee = (Employee) entityManager.createQuery("from Employee e where e.employeeName = :name", Employee.class)
                .setParameter("name", "Richard Li").getSingleResult();
        assertThat(employee, notNullValue());
        assertThat(employee.getProject(), Matchers.<Object>notNullValue());

        employee.setProject(null);

        entityManager.flush();
        entityManager.clear();

        employee = (Employee) entityManager.createQuery("from Employee e where e.employeeName = :name", Employee.class)
                .setParameter("name", "Richard Li").getSingleResult();
        assertThat(employee, notNullValue());
        assertThat(employee.getProject(), nullValue());
    }

    @Test
    public void should_remove_employee_project_relation_from_project_side() {
        should_create_employee_project_relation_from_project_side();

        entityManager.flush();
        entityManager.clear();

        Project project = (Project) entityManager.createQuery("from Project p where p.name = :name", Project.class)
                .setParameter("name", "BMC").getSingleResult();
        assertThat(project, notNullValue());
        assertThat(project.getEmployees().size(), greaterThan(0));

        Employee employee = (Employee) entityManager.createQuery("from Employee e where e.employeeName = :name", Employee.class)
                .setParameter("name", "Richard Li").getSingleResult();
        assertThat(employee, notNullValue());

        project.removeEmployees(asSet(employee));

        entityManager.flush();
        entityManager.clear();

        project = (Project) entityManager.createQuery("from Project p where p.name = :name", Project.class)
                .setParameter("name", "BMC").getSingleResult();
        assertThat(project, notNullValue());
        assertThat(project.getEmployees().size(), is(0));
    }

    @Test
    public void should_replace_project_employees_with_new_employees() {
        should_create_employee_project_relation_from_project_side();

        entityManager.flush();
        entityManager.clear();

        Project project = (Project) entityManager.createQuery("from Project p where p.name = :name", Project.class)
                .setParameter("name", "BMC").getSingleResult();
        assertThat(project, notNullValue());
        assertThat(project.getEmployees().size(), greaterThan(0));

        Employee employee = new Employee();
        employee.setEmployeeNo("TW1159");
        employee.setEmployeeName("XiaoQing");
        employee.setGrade(EmployeeGrade.SENIOR);
        entityManager.persist(employee);

        project.setEmployees(asSet(employee));

        entityManager.flush();
        entityManager.clear();

        project = (Project) entityManager.createQuery("from Project p where p.name = :name", Project.class)
                .setParameter("name", "BMC").getSingleResult();
        assertThat(project, notNullValue());
        assertThat(project.getEmployees().size(), greaterThan(0));
        assertThat(project.getEmployees().iterator().next(), is(employee));
    }

    @Test
    public void test() {
        List<Integer> integers = Arrays.asList(1, 2, 3);
        Iterable<String> transform = Iterables.transform(integers, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        });

        Iterable<Integer> concat = Iterables.concat(integers, Arrays.asList(4, 5, 6));



        for(Integer s: concat) {
            System.out.println(">>> " + s);
        }
    }

    @Test
    public void test2() {
        ImmutableSet<Integer> ints = ImmutableSet.of(1, 2, 3);
    }
}
