package com.freeroom.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.*;

@Entity
@Table(name = "EMPLOYEE")
@AttributeOverride(name = "name", column = @Column(name = "EMPLOYEE_NO"))
@NamedQueries(
        @NamedQuery(name = "Employee.findByDepartment", query = "select e from Employee e where e.department = :department")
)
public class Employee extends UniqueNameEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "EMPLOYEE_NAME", nullable = false)
    private String employeeName;

    @ManyToOne
//    @JoinColumn(name = "DEPARTMENT_ID")
    @JoinTable(name = "EMPLOYEE_DEPARTMENT_MAP",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID"),
            inverseJoinColumns = @JoinColumn(name = "DEPARTMENT_ID"))
    private Department department;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "EMPLOYEE_GRADE")
    private EmployeeGrade grade;

    //    @Lob
//    @Column(name = "PHOTO")
    @Transient
    private byte[] photo;

    @ElementCollection
    @CollectionTable(name = "EMPLOYEE_NICKNAMES", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @Column(name = "NICKNAME")
    private Set<String> nickNames = new HashSet<String>();

    @ElementCollection
    @CollectionTable(name = "EMPLOYEE_VACATION_BOOKINGS", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @OrderBy("startDate DESC")
    private List<EmployeeVacationBookings> employeeVacationBookings = new ArrayList<EmployeeVacationBookings>();

    @ElementCollection
    @CollectionTable(name = "EMPLOYEE_PHONE_NUMBERS", joinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @MapKeyColumn(name = "PHONE_TYPE")
    @Column(name = "PHONE_NUMBER")
    private Map<String, String> phoneNumbers = new HashMap<String, String>();

    @Version
    private int version;

    public int getId() {
        return id;
    }

    public String getEmployeeNo() {
        return getName();
    }

    public void setEmployeeNo(String employeeNo) {
        setName(employeeNo);
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Set<String> getNickNames() {
        return nickNames;
    }

    public void setNickNames(Set<String> nickNames) {
        this.nickNames = nickNames;
    }

    public List<EmployeeVacationBookings> getEmployeeVacationBookings() {
        return employeeVacationBookings;
    }

    public void setEmployeeVacationBookings(List<EmployeeVacationBookings> employeeVacationBookings) {
        this.employeeVacationBookings = employeeVacationBookings;
    }

    public Map<String, String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Map<String, String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public EmployeeGrade getGrade() {
        return grade;
    }

    public void setGrade(EmployeeGrade grade) {
        this.grade = grade;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        if (project != null) {
            project.getEmployees().add(this);
            this.project = project;
        } else {
            if (project != null) {
                project.getEmployees().remove(this);
            }
            this.project = null;
        }
    }

    public int getVersion() {
        return version;
    }

    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof Employee)) return false;

        Employee that = (Employee) o;
        if (that == this) return true;
        if (this.getEmployeeNo() != null && this.getEmployeeNo().equals(that.getEmployeeNo())) {
            return true;
        }

        return false;
    }

    public int hashCode() {
        int result = getEmployeeNo() != null ? getEmployeeNo().hashCode() : 0;
        return result;
    }
}
