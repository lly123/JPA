package com.freeroom.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "NAME"))
public class Project extends UniqueNameEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<Employee>();

    public int getId() {
        return id;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        removeEmployees(this.employees);
        addEmployees(employees);
    }

    public void addEmployees(Set<Employee> employees) {
        for (Employee employee : employees) {
            employee.setProject(this);
        }
    }

    public void removeEmployees(Set<Employee> employees) {
        for (Employee employee : employees) {
            employee.setProject(null);
        }
    }
}
