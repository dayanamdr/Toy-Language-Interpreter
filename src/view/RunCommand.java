package view;

import controller.Controller;

import java.util.Objects;
import java.util.Scanner;

public class RunCommand extends Command {
    private final Controller controller;

    public RunCommand(String key, String desc, Controller controller) {
        super(key, desc);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Do you want to display the steps?[Y/n]");
            Scanner readOption = new Scanner(System.in);
            String option = readOption.next();
            controller.setDisplayFlag(Objects.equals(option, "Y"));
            controller.allSteps();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
