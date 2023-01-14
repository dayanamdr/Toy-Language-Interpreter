package view;

import domain.ADTs.IDictionary;
import domain.ADTs.MyDictionary;
import domain.Exceptions.MyException;

import java.util.Scanner;

public class TextMenu {
    private IDictionary<String, Command> commands;

    public TextMenu() {
        this.commands = new MyDictionary<>();
    }

    public void addCommand(Command command) {
        this.commands.update(command.getKey(), command);
    }

    private void printMenu() {
        for (Command command: commands.values()) {
            String line = String.format("%4s: %s", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Input the option: ");
            String key = scanner.nextLine();
            try {
                Command command = commands.lookup(key);
                command.execute();
            } catch (MyException exception) {
                System.out.println("Invalid option");
            }
        }
    }
}
