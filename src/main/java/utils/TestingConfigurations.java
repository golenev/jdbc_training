package utils;

import static utils.StringUtils.buildSomeString;

public class TestingConfigurations {

    private TestingConfigurations() {
    }

    public static String getSqlQuery (String key){
        return TestingEnvironment.getCurrentEnvironment("sqlQuery.json").getValue(key).toString();
    }
    public static String getSqlPattern (String key){
        return TestingEnvironment.getCurrentEnvironment("sqlPatternQuery.json").getValue(key).toString();
    }
    public static String getJdbcData(String key){
        return TestingEnvironment.getCurrentEnvironment("jdbc.json").getValue(key).toString();
    }
    public static String getValidUrl(){
        return String.valueOf(buildSomeString(getJdbcData("/jdbcProtocol"), getJdbcData("/jdbcAddress"),
                getJdbcData("/jdbcPort"), getJdbcData("/jdbcNameDB"), getJdbcData("/jdbcParams")));
    }
    public static String getValuesOfDB (String key){
        return TestingEnvironment.getCurrentEnvironment("valuesOfDB.json").getValue(key).toString();
    }

   public static String getAdvData(String key){
       return TestingEnvironment.getCurrentEnvironment("someAnotherTestingData.json").getValue(key).toString();
   }

}
