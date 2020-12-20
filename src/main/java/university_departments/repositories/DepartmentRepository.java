package university_departments.repositories;

import university_departments.entities.Degree;
import university_departments.entities.Department;
import university_departments.entities.Lecturer;
import university_departments.entities.dto.StatisticDto;
import university_departments.repositories.interfaces.IDepartmentRepository;

import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;


/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 17.12.2020
 */
public class DepartmentRepository implements IDepartmentRepository, AutoCloseable {
    private static final Logger LOG = Logger.getLogger(DepartmentRepository.class.getName());
    private Connection connection;

    public static DepartmentRepository getNewInstance() {
        return new DepartmentRepository().init();
    }

    private DepartmentRepository() {}

    private DepartmentRepository init() {
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

    public List<Department> findAll() throws SQLException {
        String sql = "SELECT d.id AS dep_id" +
                ", d.name AS dep_name" +
                ", d.id AS dep_id" +
                ", c.id AS chief_id" +
                ", c.name AS chief_name" +
                ", c.surname AS chief_surname" +
                ", c.salary AS chief_salary" +
                ", c.degree AS chief_degree" +
                ", l.id AS lecturer_id" +
                ", l.name AS lecturer_name" +
                ", l.surname AS lecturer_surname" +
                ", l.salary AS lecturer_salary" +
                ", l.degree AS lecturer_degree" +
                " FROM departments d" +
                " LEFT JOIN lecturers c ON c.id = d.chief_id" +
                " LEFT JOIN departments_lecturers dep_l ON dep_l.department_id = d.id" +
                " LEFT JOIN lecturers l ON (dep_l.department_id = d.id AND dep_l.lecturer_id = l.id)" +
                " ORDER BY d.name";
        try (Statement conn = this.connection.createStatement()) {
            ResultSet reqResult = conn.executeQuery(sql);
            return this.mapDepartments(reqResult);
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            throw e;
        }
    }

    public Optional<StatisticDto> getDepartmentStatistic(String name) throws SQLException {
        String sql = "SELECT COUNT(*) as department_count, " +
                "COUNT(CASE WHEN l.degree = 'ASSISTANT' then 1 end) as assistant_count, " +
                "COUNT(CASE WHEN l.degree = 'PROFESSOR' then 1 end) as professor_count, " +
                "COUNT(CASE WHEN l.degree = 'ASSOCIATE_PROFESSOR' then 1 end) as a_professor_count " +
                "FROM departments d " +
                "   LEFT JOIN departments_lecturers dl on d.id = dl.department_id " +
                "   LEFT JOIN lecturers l on dl.lecturer_id = l.id " +
                "WHERE d.name LIKE ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();

            Optional<StatisticDto> result = Optional.empty();
            rs.next();
            if(rs.getInt("department_count") > 0) {
                StatisticDto statisticDto = new StatisticDto();
                statisticDto.setAssistantCount(rs.getInt("assistant_count"));
                statisticDto.setProfessorCount(rs.getInt("professor_count"));
                statisticDto.setAssociateProfessorCount(rs.getInt("a_professor_count"));
                result = Optional.of(statisticDto);
            }

            return result;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
            throw e;
        }

    }

    public Optional<Double> getDepartmentAverageSalary(String name) throws SQLException {
        String sql = "SELECT avg(l.salary) as avg_salary" +
                " FROM departments d" +
                " LEFT JOIN departments_lecturers dep_l ON dep_l.department_id = d.id" +
                " LEFT JOIN lecturers l ON (dep_l.department_id = d.id AND dep_l.lecturer_id = l.id)" +
                " WHERE d.name LIKE ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            rs.next();
            String result = rs.getString("avg_salary");

            return this.mapToDouble(result);
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            throw e;
        }
    }

    private Optional<Double> mapToDouble(String value) {
        Optional<Double> result = Optional.empty();
        if (value != null) {
            result = Optional.of(Double.parseDouble(value));
        }
        return result;
    }

    public Optional<Integer> getDepartmentEmployeesCount(String depName) throws SQLException {
        String sql = "SELECT count(l) as lecturers_count" +
                " FROM departments d" +
                " LEFT JOIN departments_lecturers dep_l ON dep_l.department_id = d.id" +
                " LEFT JOIN lecturers l ON (dep_l.department_id = d.id AND dep_l.lecturer_id = l.id)" +
                " WHERE d.name LIKE ?";

        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, depName);
            ResultSet rs = statement.executeQuery();
            rs.next();
            String result = rs.getString("lecturers_count");

            return this.mapToInteger(result);
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            throw e;
        }
    }

    private Optional<Integer> mapToInteger(String value) {
        Optional<Integer> result = Optional.empty();
        if (value != null) {
            result = Optional.of(Integer.parseInt(value));
        }
        return result;
    }

    public Optional<Department> findByName(String name) throws SQLException {
        String sql = "SELECT d.id AS dep_id" +
                ", d.name AS dep_name" +
                ", d.id AS dep_id" +
                ", c.id AS chief_id" +
                ", c.name AS chief_name" +
                ", c.surname AS chief_surname" +
                ", c.salary AS chief_salary" +
                ", c.degree AS chief_degree" +
                ", l.id AS lecturer_id" +
                ", l.name AS lecturer_name" +
                ", l.surname AS lecturer_surname" +
                ", l.salary AS lecturer_salary" +
                ", l.degree AS lecturer_degree" +
                " FROM departments d" +
                " LEFT JOIN lecturers c ON c.id = d.chief_id" +
                " LEFT JOIN departments_lecturers dep_l ON dep_l.department_id = d.id" +
                " LEFT JOIN lecturers l ON (dep_l.department_id = d.id AND dep_l.lecturer_id = l.id)" +
                " WHERE d.name LIKE ?" +
                " ORDER BY d.name";
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            List<Department> result = this.mapDepartments(rs);

            return result.size() == 0 ? Optional.empty() : Optional.of(result.get(0));
        } catch (Exception e) {
            LOG.warning(e.getMessage());
            throw e;
        }

    }

    private List<Department> mapDepartments(ResultSet rs) throws SQLException {
        List<Department> result = new ArrayList<>();
        Department department = null;
        while (rs.next()) {
            UUID depId = UUID.fromString(rs.getString("dep_id"));

            if (department == null || !department.getId().equals(depId)) {
                department = new Department();
                department.setId(depId);
                department.setName(rs.getString("dep_name"));
                result.add(department);
            }

            String chiefId = rs.getString("chief_id");
            if (chiefId != null) {
                Lecturer chief = new Lecturer();
                chief.setId(UUID.fromString(chiefId));
                chief.setDegree(Degree.valueOf(rs.getString("chief_degree")));
                chief.setName(rs.getString("chief_name"));
                chief.setSurname(rs.getString("chief_surname"));
                chief.setSalary(rs.getDouble("chief_salary"));
                department.setChief(chief);
            }

            String lecturerId = rs.getString("lecturer_id");
            if (lecturerId != null) {
                Lecturer lecturer = new Lecturer();
                lecturer.setId(UUID.fromString(lecturerId));
                lecturer.setDegree(Degree.valueOf(rs.getString("lecturer_degree")));
                lecturer.setName(rs.getString("lecturer_name"));
                lecturer.setSurname(rs.getString("lecturer_surname"));
                lecturer.setSalary(rs.getDouble("lecturer_salary"));
                department.addLecturer(lecturer);
            }
        }
        return result;
    }
}
