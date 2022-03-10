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



    private static void initDB() throws ClassNotFoundException, IOException, SQLException {
        Class.forName(getJdbcData("/driverName"));
        connection = DriverManager.getConnection(getJdbcData("/jdbcURL"),
                getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
    }

    public static Connection getConnectionAsSingleton() throws SQLException, IOException, ClassNotFoundException {
        if (connection == null) initDB();
        return connection;
    }

    public static void closeDB() throws SQLException {
        connection.close();
    }

}
