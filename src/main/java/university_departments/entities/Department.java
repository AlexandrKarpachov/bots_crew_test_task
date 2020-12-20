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
public class Department {
    private UUID id;
    private String name;
    private Lecturer chief;
    private List<Lecturer> lecturers;

    public Department() {}

    public Department(UUID id, String name, Lecturer chief, List<Lecturer> lecturers) {
        this.id = id;
        this.name = name;
        this.chief = chief;
        this.lecturers = lecturers;
    }

    public Department(UUID id, String name, Lecturer chief) {
        this.id = id;
        this.name = name;
        this.chief = chief;
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

    public Lecturer getChief() {
        return chief;
    }

    public void setChief(Lecturer chief) {
        this.chief = chief;
    }

    public List<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(List<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public void addLecturer(Lecturer lecturer) {
        if (this.lecturers == null) {
            this.lecturers = new ArrayList<>();
        }
        this.lecturers.add(lecturer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
