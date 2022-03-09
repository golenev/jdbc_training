import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

abstract public class CRUDAbstractTest {
    private Properties prop = new Properties();
    private Connection connection;
    private Statement statement;


    @BeforeClass
    void init() throws ClassNotFoundException, IOException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (FileInputStream configFile = new FileInputStream("src/test/resources/jdbc.properties")) {
            prop.load(configFile);
        }
        connection = DriverManager.getConnection(prop.getProperty("jdbcURL"), prop.getProperty("jdbcUsername"), prop.getProperty("jdbcPassword"));
        statement = connection.createStatement();
    }

    @AfterClass
    void exit() throws SQLException {
        connection.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

}
