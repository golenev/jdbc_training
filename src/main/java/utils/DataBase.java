package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import static utils.TestingConfigurations.getJdbcData;

public class DataBase {
    private static Connection connection = null;

     private static void initDB(String driverName, String jdbcUrl, String jdbcUserName, String jdbcPassword){
         try {
             Class.forName(driverName);
         } catch (ClassNotFoundException e) {
             e.printStackTrace();
         }
         try {

             connection = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }


    public static Connection getConnection(String driverName, String jdbcUrl, String jdbcUserName, String jdbcPassword){
        if (connection == null) initDB(driverName, jdbcUrl, jdbcUserName, jdbcPassword);
        return connection;
    }


    public static void closeDB(){
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
