package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBase {
    private static Connection connection = null;
    private static Properties prop = new Properties();

    private static void initDB() throws ClassNotFoundException, IOException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (FileInputStream configFile = new FileInputStream("src/test/resources/jdbc.properties")) {
            prop.load(configFile);
        }
        connection = DriverManager.getConnection(prop.getProperty("jdbcURL"), prop.getProperty("jdbcUsername"), prop.getProperty("jdbcPassword"));
    }

    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        if (connection == null) initDB();
        return connection;
    }

    public static void closeDB() throws SQLException {
        connection.close();
    }

}
