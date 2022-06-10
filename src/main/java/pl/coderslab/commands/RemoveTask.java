package pl.coderslab.commands;

import pl.coderslab.TaskManager;

import java.util.regex.Pattern;

import static pl.coderslab.TaskManager.scanner;
import static pl.coderslab.TaskManager.tasks;

public class RemoveTask implements Command {
    @Override
    public void execute() {
        int positionToRemove = Integer.MIN_VALUE;
        while (positionToRemove == Integer.MIN_VALUE) {
            String unparsed = TaskManager.getValue("Please select position to remove: ",
                    Pattern.compile("\\d+"), scanner);
            int unchecked = positionToRemove;
            try {
                unchecked = Integer.parseInt(unparsed);
            } catch (NumberFormatException e) {
                System.out.println("Please enter an integer");
            }
            if (!tasks.isEmpty() && unchecked < tasks.size()){
                positionToRemove = unchecked;
            } else {
                System.out.println("Please enter a valid position");
            }
            tasks.remove(positionToRemove);
        }
    }
}
