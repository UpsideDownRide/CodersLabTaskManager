package pl.coderslab;

import java.util.Date;

public record Task(
        String description,
        Date date,
        boolean done
) {
}
