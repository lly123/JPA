package com.freeroom.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@AttributeOverride(name = "name", column = @Column(name = "NAME"))
public class Department extends UniqueNameEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    @MapKeyEnumerated(EnumType.STRING)
    @MapKey(name = "grade")
    private Map<EmployeeGrade, Employee> employees = new HashMap<EmployeeGrade, Employee>();

    public int getId() {
        return id;
    }

    public Map<EmployeeGrade, Employee> getEmployees() {
        return employees;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public void setEmployees(Map<EmployeeGrade, Employee> employees) {
        this.employees = employees;
    }


    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;
        if (that == this) return true;
        if (this.getName() != null && this.getName().equals(that.getName())) {
            return true;
        }

        return false;
    }

    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        return result;
    }
}
