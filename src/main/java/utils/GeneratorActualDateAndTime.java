package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static utils.TestingConfigurations.*;

public class GeneratorActualDateAndTime {

    public static String getTimeAndDate() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(getAdvData("/timePattern"));
        LocalDateTime dt = LocalDateTime.now();
        String actualDate = dt.format(formatter);
        return actualDate;
    }
}
