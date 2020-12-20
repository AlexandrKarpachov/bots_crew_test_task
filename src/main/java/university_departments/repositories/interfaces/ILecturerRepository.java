package university_departments.repositories.interfaces;

import university_departments.entities.Lecturer;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 19.12.2020
 */
public interface ILecturerRepository {
    List<Lecturer> findByNameOrSurname(String key) throws SQLException;
}
