package university_departments.repositories;

import university_departments.entities.Degree;
import university_departments.entities.Lecturer;
import university_departments.entities.dto.StatisticDto;
import university_departments.repositories.interfaces.ILecturerRepository;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class LecturerRepository implements ILecturerRepository, AutoCloseable {

    private static final Logger LOG = Logger.getLogger(LecturerRepository.class.getName());
    private Connection connection;

    public static LecturerRepository getNewInstance() {
        return new LecturerRepository().init();
    }

    private LecturerRepository() {}

    private LecturerRepository init() {
        try (InputStream in = DepartmentRepository.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            this.connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            throw new IllegalStateException(e);
        }

        return this;
    }

    public void close()  {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOG.warning(e.getMessage());
            }
        }
    }


    @Override
    public List<Lecturer> findByNameOrSurname(String key) throws SQLException {
        String sql = "SELECT * " +
                "        FROM lecturers l " +
                "        WHERE l.name LIKE ? OR l.surname LIKE ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, "%" + key + "%");
            statement.setString(2, "%" + key + "%");
            ResultSet rs = statement.executeQuery();

            List<Lecturer> result = new ArrayList<>();
            while (rs.next()) {
                Lecturer lecturer = new Lecturer();
                lecturer.setId(UUID.fromString(rs.getString("id")));
                lecturer.setName(rs.getString("name"));
                lecturer.setSurname(rs.getString("surname"));
                lecturer.setDegree(Degree.valueOf(rs.getString("degree")));
                lecturer.setSalary(rs.getDouble("salary"));
                result.add(lecturer);
            }

            return result;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
            throw e;
        }
    }
}
