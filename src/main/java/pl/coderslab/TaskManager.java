package pl.coderslab;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd");
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String LIST = "list";
    private static final String EXIT = "exit";
    private static final Set<String> commands = new LinkedHashSet<>(List.of(ADD, REMOVE, LIST, EXIT));
    private static final Pattern commandsRegex = Pattern.compile(String.join("|", commands));

    public static void main(String[] args) {
        List<Task> tasks = readCSV("tasks.csv");
        printMenu();
        Scanner scanner = new Scanner(System.in);
        while(!scanner.hasNext(commandsRegex)){
            System.out.println(ConsoleColors.RED + "Invalid command" + ConsoleColors.RESET);
            printMenu();
            scanner.next();
        }
    }

    public static void printMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        commands.forEach(System.out::println);
    }

    public static void programExit(int statusCode, String message) {
        System.out.println(message);
        System.exit(statusCode);
    }

    private static Task parseRecord(CSVRecord record) throws IllegalArgumentException {
        if (record.size() != 3) throw new IllegalArgumentException("Incorrect task record size");
        String description = record.get(0);
        LocalDate date = LocalDate.from(dateFormat.parse(record.get(1).trim()));
        boolean completed = record.get(2).equals("true");
        return new Task(description, date, completed);

    }

    public static List<Task> readCSV(String filename) {
        Path file = Paths.get(filename);
        if (!Files.exists(file)) {
            System.out.println("The tasks file does not exist");
        } else {
            try {
                Reader reader = Files.newBufferedReader(file);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
                return csvParser.getRecords().stream().map(TaskManager::parseRecord).toList();
            } catch (IOException e) {
                programExit(1,"Error reading from " + filename + " file" );
            } catch (IllegalArgumentException e) {
                programExit(1, "Error parsing task in file " + filename + " file");
            } catch (Exception e) {
                programExit(1, e.getMessage());
            }
        }
        return null;
    }
}

