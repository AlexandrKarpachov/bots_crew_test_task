package university_departments.repositories.interfaces;

import university_departments.entities.Department;
import university_departments.entities.dto.StatisticDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDepartmentRepository {

    Optional<Department> findByName(String name) throws SQLException;

    List<Department> findAll() throws SQLException;

    Optional<StatisticDto> getDepartmentStatistic(String name) throws SQLException;

    Optional<Double> getDepartmentAverageSalary(String name) throws SQLException;

    Optional<Integer> getDepartmentEmployeesCount(String depName) throws SQLException;
}
