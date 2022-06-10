package pl.coderslab;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import pl.coderslab.commands.AddTask;
import pl.coderslab.commands.Exit;
import pl.coderslab.commands.ListTasks;
import pl.coderslab.commands.RemoveTask;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class TaskManager {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    public static List<Task> tasks = new ArrayList<>();
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        readCSV(tasks, "tasks.csv");
        Menu taskMenu = new Menu();
        initializeTaskMenu(taskMenu);
        while(true) {
            taskMenu.printMenu();
            taskMenu.runCommand(getCommand(scanner, taskMenu));
        }
    }

    private static void initializeTaskMenu(Menu taskMenu){
        taskMenu.addCommand("add", new AddTask());
        taskMenu.addCommand("remove", new RemoveTask());
        taskMenu.addCommand("list", new ListTasks());
        taskMenu.addCommand("exit", new Exit());
    }

    public static String getValue(String prompt, Pattern regex, Scanner scanner) {
        System.out.print(prompt);
        while(!scanner.hasNext(regex)){
            System.out.println(ConsoleColors.RED + "Invalid value" + ConsoleColors.RESET);
            System.out.print(prompt);
            scanner.next();
        }
        return scanner.next();
    }

    private static String getCommand(Scanner scanner, Menu menu) {
        while(!scanner.hasNext(menu.commandsRegex)){
            System.out.println(ConsoleColors.RED + "Invalid command" + ConsoleColors.RESET);
            menu.printMenu();
            scanner.next();
        }
        return scanner.next();
    }

    public static void programExit(int statusCode, String message) {
        System.out.println(message);
        scanner.close();
        System.exit(statusCode);
    }

    private static Task parseRecord(CSVRecord record) throws IllegalArgumentException {
        if (record.size() != 3) throw new IllegalArgumentException("Incorrect task record size");
        String description = record.get(0);
        LocalDate date = LocalDate.from(dateFormat.parse(record.get(1).trim()));
        boolean important = record.get(2).trim().equals("true");
        return new Task(description, date, important);
    }

    private static void readCSV(List<Task> tasks, String filename) {
        Path file = Paths.get(filename);
        if (!Files.exists(file)) {
            System.out.println("The tasks file does not exist");
        } else {
            try {
                Reader reader = Files.newBufferedReader(file);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
                csvParser.getRecords().forEach(r -> tasks.add(parseRecord(r)));
            } catch (IOException e) {
                programExit(1,"Error reading from " + filename + " file" );
            } catch (IllegalArgumentException e) {
                programExit(1, "Error parsing task in file " + filename + " file");
            } catch (Exception e) {
                programExit(1, e.getMessage());
            }
        }
    }
}

