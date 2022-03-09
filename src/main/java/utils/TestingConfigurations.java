package utils;

public class TestingConfigurations {
    private TestingConfigurations() {
    }

    public static String getSqlQuery (String key){
        return TestingEnvironment.getCurrentEnvironment("sqlQuery.json").getValue(key).toString();
    }

}
