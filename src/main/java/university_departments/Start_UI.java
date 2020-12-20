package university_departments;

import university_departments.repositories.DepartmentRepository;
import university_departments.repositories.LecturerRepository;
import university_departments.services.DepartmentService;
import university_departments.services.LecturerService;

import java.util.function.Consumer;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 17.12.2020
 */
public class Start_UI {
    private final IInput input;
    private final CommandsHandler handler;
    private final Consumer<String> output;

    public Start_UI(IInput input, CommandsHandler handler, Consumer<String> output) {
        this.input = input;
        this.handler = handler;
        this.output = output;
    }

    public void start() {
        String command = input.ask("Welcome to University departments \nEnter your command");
        while (!command.equals("Exit")) {
            String answer = this.handler.handle(command);
            this.output.accept(answer);
            command = input.ask();
        }

    }

    public static void main(String[] args) {
        DepartmentRepository depRepo = DepartmentRepository.getNewInstance();
        LecturerRepository lecturerRepo = LecturerRepository.getNewInstance();

        new Start_UI(
                new ConsoleInput(),
                CommandsHandler.getNewInstance(new DepartmentService(depRepo), new LecturerService(lecturerRepo)),
                System.out::println
        ).start();

        depRepo.close();
        lecturerRepo.close();
    }
}
