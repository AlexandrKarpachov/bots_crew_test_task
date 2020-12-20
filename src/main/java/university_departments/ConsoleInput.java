package university_departments;

import java.util.Scanner;

/**
 * @author Aleksandr Karpachov
 * @version 1.0
 * @since 18.12.2020
 */
public class ConsoleInput implements IInput{
    @Override
    public String ask() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    @Override
    public String ask(String message) {
        Scanner input = new Scanner(System.in);
        System.out.println(message);
        return input.nextLine();
    }
}
