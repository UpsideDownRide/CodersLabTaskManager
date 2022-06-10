package pl.coderslab;

import pl.coderslab.commands.Command;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Menu {
    private final Map<String, Command> menuItems = new LinkedHashMap<>();
    public Pattern commandsRegex;

    public void addCommand(String operation, Command command) {
        menuItems.put(operation, command);
        updateRegex();
    }

    public void runCommand(String operation) {
        menuItems.get(operation).execute();
    }

    private void updateRegex() {
        commandsRegex = Pattern.compile(String.join("|", menuItems.keySet()));
    }

    public void printMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        menuItems.keySet().forEach((System.out::println));
    }


}
