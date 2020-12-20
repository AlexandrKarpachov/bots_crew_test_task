package university_departments.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 16.12.2020
 */
public class Lecturer {
    private UUID id;
    private String name;
    private String surname;
    private Degree degree;
    private List<Department> departments;
    private double salary;

    public Lecturer() {}

    public Lecturer(String name, String surname, Degree degree, List<Department> departments, double salary) {
        this.name = name;
        this.surname = surname;
        this.degree = degree;
        this.departments = departments;
        this.salary = salary;
    }

    public Lecturer(String name, String surname, Degree degree) {
        this.name = name;
        this.surname = surname;
        this.degree = degree;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        if (this.departments == null) {
            this.departments = new ArrayList<Department>();
        }
        this.departments.add(department);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecturer lecturer = (Lecturer) o;
        return Objects.equals(id, lecturer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
