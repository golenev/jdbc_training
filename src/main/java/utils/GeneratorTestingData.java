package utils;

import java.util.ArrayList;
import java.util.List;

public class GeneratorTestingData {
    public static List<String> expressions = new ArrayList<>();

    public static List<String> getListOfExpressions() {
        String percent = "%";
        for (int num = 0; num < 10; num++) {
            String value = String.valueOf(StringUtils.buildSomeString(percent, num, percent, num, percent));
            expressions.add(value);
        }
        return expressions;
    }

}

