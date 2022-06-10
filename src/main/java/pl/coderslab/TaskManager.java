package pl.coderslab;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args) {
        List<CSVRecord> tasks = readCSV("tasks.csv");
        printMenu();
    }

    public static void printMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:" + ConsoleColors.RESET);
        System.out.println("add");
        System.out.println("remove");
        System.out.println("list");
        System.out.println("exit");
    }

    public static void programExit(int statusCode) {
        System.exit(statusCode);
    }

    public static List<CSVRecord> readCSV(String filename) {
        Path file = Paths.get(filename);
        if (!Files.exists(file)) {
            System.out.println("The tasks file does not exist");
        } else {
            try {
                Reader reader = Files.newBufferedReader(file);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
                return csvParser.getRecords();
            } catch (IOException e) {
                System.out.println("Error reading from " + filename + " file");
                programExit(1);
            }
        }
        return null;
    }
}

