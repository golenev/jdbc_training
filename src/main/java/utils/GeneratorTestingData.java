package utils;

import java.util.ArrayList;
import java.util.List;
import static utils.TestingConfigurations.*;

public class GeneratorTestingData {
    public static List<String> expressions = new ArrayList<>();

    public static List<String> getListOfExpressions() {
        String percent =  getAdvData("/percent");
        for (int num = NumsAndIndexes.ZERO.getValue(); num < NumsAndIndexes.TEN.getValue(); num++) {
            String value = String.valueOf(StringUtils.buildSomeString(percent, num, percent, num, percent));
            expressions.add(value);
        }
        return expressions;
    }

}

