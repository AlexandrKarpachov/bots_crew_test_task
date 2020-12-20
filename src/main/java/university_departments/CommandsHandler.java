package university_departments;

import university_departments.entities.Department;
import university_departments.entities.Lecturer;
import university_departments.entities.dto.StatisticDto;
import university_departments.services.DepartmentService;
import university_departments.services.LecturerService;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 17.12.2020
 */
public class CommandsHandler {
    private final DepartmentService departmentService;
    private final LecturerService lecturerService;

    private final Map<String, Function<String, String>> commands = new HashMap<>();

    private CommandsHandler (DepartmentService departmentService, LecturerService lecturerService) {
        this.departmentService = departmentService;
        this.lecturerService = lecturerService;
    }

    public static CommandsHandler getNewInstance(DepartmentService depService, LecturerService lecturerService) {
        CommandsHandler handler = new CommandsHandler(depService, lecturerService);
        handler.commands.put("Who is head of department ", handler::getDepartmentHead);
        handler.commands.put("Show statistics ", handler::getStatistics);
        handler.commands.put("Show the average salary for the department ", handler::getAverageSalary);
        handler.commands.put("Show count of employee for ", handler::getEmployeeCount);
        handler.commands.put("Global search by ", handler::SearchBy);

        return handler;
    }

    private static final String ERROR_TEXT = "Wrong command." + System.lineSeparator() +
            "List of  available commands:" + System.lineSeparator() +
            "Who is head of department {department_name}" + System.lineSeparator() +
            "Show statistics {department_name}" + System.lineSeparator() +
            "Show the average salary for the department {department_name}" + System.lineSeparator() +
            "Show count of employee for {department_name}" + System.lineSeparator() +
            "Global search by {template}" + System.lineSeparator() +
            "Exit";


    public String handle(String command) {
        for (String key: this.commands.keySet()) {
            if (command.startsWith(key)) {
                Function<String, String> rst = this.commands.get(command);
                return this.commands.get(key).apply(command);
            }
        }
        return ERROR_TEXT;
    }

    public static void main(String[] args) {
        System.out.println("Who is head of department ".length());
    }

    private String getStatistics(String command) {
        String name = command.substring(16);
        String result = "Not found department with such name";
        try {
            Optional<StatisticDto> statistic = this.departmentService.getDepartmentStatistic(name);
            if (statistic.isPresent()) {
                StatisticDto stats = statistic.get();
                result = String.format("Answer: assistans - %d" + System.lineSeparator() +
                        "        associate professors - %d" + System.lineSeparator() +
                        "        professors - %d\n" + System.lineSeparator(),
                        stats.getAssistantCount(), stats.getAssociateProfessorCount(), stats.getProfessorCount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    private String getDepartmentHead(String command) {
        String name = command.substring(26);
        String result = "Not found department with such name";
        try {
            Optional<Department> department = this.departmentService.findByName(name);
            if (department.isPresent()) {
                Lecturer chief = department.get().getChief();
                result = String.format("Head of %s department is %s %s %s",
                        name, chief.getName(), chief.getSurname(), System.lineSeparator());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result = "";
        }
        return result;
    }

    private String getAverageSalary(String command) {
        String name = command.substring(43);
        String result = "Not found department with such name";
        try {
            Optional<Double> avg = this.departmentService.getDepartmentAverageSalary(name);
            if (avg.isPresent()) {
                result = String.format("The average salary of %s is %f", name, avg.get());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            result = "";
        }
        return result;
    }

    private String getEmployeeCount(String command) {
        String name = command.substring(27);
        String result = "Not found department with such name";
        try {
            Optional<Integer> count = this.departmentService.getDepartmentEmployeesCount(name);
            if (count.isPresent()) {
                result = count.get().toString();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            result = "";
        }
        return result;
    }

    private String SearchBy(String command) {
        String key = command.substring(17);
        String result = "Not found!";
        try {
            List<Lecturer> lecturers = this.lecturerService.findByNameOrSurname(key);
            if (lecturers.size() > 0) {
                result = lecturers.stream()
                        .map(lecturer -> lecturer.getName() + " " + lecturer.getSurname())
                        .collect(Collectors.joining(", "));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            result = "";
        }
        return result;
    }

}
