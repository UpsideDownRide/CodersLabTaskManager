package pl.coderslab.commands;

import pl.coderslab.Task;
import pl.coderslab.TaskManager;

public class ListTasks implements Command {
    @Override
    public void execute() {
        int counter = 0;
        for(Task t: TaskManager.tasks){
            String msg = String.format("%s : %s %s %s", counter++, t.description(), t.date(), t.important());
            System.out.println(msg);
        }
    }
}
