package pl.coderslab.commands;

import pl.coderslab.ConsoleColors;
import pl.coderslab.TaskManager;

public class Exit implements Command {
    @Override
    public void execute() {
        TaskManager.programExit(0,ConsoleColors.RED + "Bye, bye." + ConsoleColors.RESET );
    }
}
