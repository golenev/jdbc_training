package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeneratorActualDateAndTime {

    public static String getTimeAndDate() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("yyyy-MM-d h:mm:ss");
        LocalDateTime dt = LocalDateTime.now();
        String actualDate = dt.format(formatter);
        return actualDate;
    }
}
