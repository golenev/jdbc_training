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

    private static void initDB(){
        try {
            Class.forName(getJdbcData("/driverName"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(getJdbcData("/jdbcURL"),
                    getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnectionAsSingleton(){
        if (connection == null) initDB();
        return connection;
    }

    public static void closeDB(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
