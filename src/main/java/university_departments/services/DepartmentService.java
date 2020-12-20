package university_departments.services;

import university_departments.entities.Department;
import university_departments.entities.dto.StatisticDto;
import university_departments.repositories.DepartmentRepository;
import university_departments.repositories.interfaces.IDepartmentRepository;

import java.sql.SQLException;
import java.util.Optional;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 18.12.2020
 */
public class DepartmentService {
    private final IDepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Optional<Department> findByName(String name) throws SQLException {
        return this.departmentRepository.findByName(name);
    }

    public Optional<StatisticDto> getDepartmentStatistic(String name) throws SQLException {
        return this.departmentRepository.getDepartmentStatistic(name);
    }

    public Optional<Double> getDepartmentAverageSalary(String name) throws SQLException {
        return this.departmentRepository.getDepartmentAverageSalary(name);
    }

    public Optional<Integer> getDepartmentEmployeesCount(String depName) throws SQLException {
        return this.departmentRepository.getDepartmentEmployeesCount(depName);
    }

}
