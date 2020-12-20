package university_departments.services;

import university_departments.entities.Lecturer;
import university_departments.repositories.interfaces.ILecturerRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 18.12.2020
 */
public class LecturerService {
    private final ILecturerRepository repository;

    public LecturerService(ILecturerRepository repository) {
        this.repository = repository;
    }

    public List<Lecturer> findByNameOrSurname(String key) throws SQLException {
        return this.repository.findByNameOrSurname(key);
    }
}
