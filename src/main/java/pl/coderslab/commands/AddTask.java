package pl.coderslab.commands;

import pl.coderslab.ConsoleColors;
import pl.coderslab.Task;
import pl.coderslab.TaskManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;
import static pl.coderslab.TaskManager.scanner;

public class AddTask implements Command {
    @Override
    public void execute() {
        String description = TaskManager.getValue("Please add task description: ",
                Pattern.compile(".*"), scanner);
        LocalDate date = null;
        while (date == null){
            String dateToParse = TaskManager.getValue("Please add task due date YYYY-MM-DD: ",
                    Pattern.compile("\\d{4}-\\d{2}-\\d{2}"), scanner);
            try {
                date = LocalDate.from(TaskManager.dateFormat.parse(dateToParse));
            } catch (DateTimeParseException e) {
                System.out.println(ConsoleColors.RED + "Invalid date entered" + ConsoleColors.RESET);
            }
        }
        boolean important = TaskManager.getValue("Is your task important (true/false): ",
                Pattern.compile("true|false"), scanner).equals("true");
        TaskManager.tasks.add(new Task(description, date, important));
    }
}
