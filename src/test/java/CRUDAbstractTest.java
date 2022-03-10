import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import utils.DataBase;

import static utils.DataBase.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

abstract public class CRUDAbstractTest {

    private Connection connection;
    private Statement statement;
    protected static int author_id;
    protected static int project_id;


    @BeforeClass
    void init() throws ClassNotFoundException, IOException, SQLException {

        connection = DataBase.getConnection();
        Statement prepareIds = connection.createStatement();
        prepareIds.executeUpdate("insert into project (name) values ('newTest" + Math.random() + "')");
        prepareIds.executeUpdate("insert into author (name, login, email) values ('newAuthor7', 'newLogin7', 'newemail7@mail.ru')");
        ResultSet resultSet = prepareIds.executeQuery("select id from project order by id desc;");
        resultSet.next();
        project_id = resultSet.getInt(1);
        resultSet = prepareIds.executeQuery("select id from author order by id desc;");
        resultSet.next();
        author_id = resultSet.getInt(1);

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
