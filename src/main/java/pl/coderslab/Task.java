package pl.coderslab;
import java.time.LocalDate;

public record Task(
        String description,
        LocalDate date,
        boolean done
) {
}
