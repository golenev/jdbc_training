package project;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TestEntity {

    private int id;
    private String name;
    private int status_id;
    private String method_name;
    private int project_id;
    private int session_id;
    private String start_time;
    private String end_time;
    private String env;
    private String browser;
    private long author_id;
    private static Properties prop = new Properties();
    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement rowUpdater = null;

    public TestEntity(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(1);
        this.name = resultSet.getString(2);
        this.status_id = resultSet.getInt(3);
        this.method_name = resultSet.getString(4);
        this.project_id = resultSet.getInt(5);
        this.session_id = resultSet.getInt(6);
        this.start_time = resultSet.getString(7);
        this.end_time = resultSet.getString(8);
        this.env = resultSet.getString(9);
        this.browser = resultSet.getString(10);
        this.author_id = resultSet.getLong(11);
    }

    public TestEntity(int id, String name, int status_id, String method_name, int project_id,
                      int session_id, String start_time, String end_time, String env,
                      String browser, long author_id) {
        this.id = id;
        this.name = name;
        this.status_id = status_id;
        this.method_name = method_name;
        this.project_id = project_id;
        this.session_id = session_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.env = env;
        this.browser = browser;
        this.author_id = author_id;
    }

    public static void init() throws ClassNotFoundException, IOException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (FileInputStream configFile = new FileInputStream("src/test/resources/jdbc.properties")) {
            prop.load(configFile);
        }
        connection = DriverManager.getConnection(prop.getProperty("jdbcURL"), prop.getProperty("jdbcUsername"), prop.getProperty("jdbcPassword"));
        // statement = connection.createStatement();
        rowUpdater = connection.prepareStatement("insert into test (`name`, `status_id`, " +
                "`method_name`, `project_id`, `session_id`, `start_time`, `end_time`, `env`, `browser`, 'author_id')" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)");
    }

    public boolean insert() throws SQLException, IOException, ClassNotFoundException {
        if (rowUpdater == null) {
            init();
        }
        rowUpdater.setString(1, this.getName());
        rowUpdater.setString(2, this.getName());
        rowUpdater.setString(3, this.getName());
        rowUpdater.setString(4, this.getName());
        rowUpdater.setString(5, this.getName());
        rowUpdater.setString(6, this.getName());
        rowUpdater.setString(7, this.getName());
        rowUpdater.setString(8, this.getName());
        rowUpdater.setString(9, this.getName());
        rowUpdater.setString(10, this.getName());
        try {
            rowUpdater.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStatus_id() {
        return status_id;
    }

    public String getMethod_name() {
        return method_name;
    }

    public int getProject_id() {
        return project_id;
    }

    public int getSession_id() {
        return session_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public String getEnv() {
        return env;
    }

    public String getBrowser() {
        return browser;
    }

    public long getAuthor_id() {
        return author_id;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status_id=" + status_id +
                ", method_name='" + method_name + '\'' +
                ", project_id=" + project_id +
                ", session_id=" + session_id +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", env='" + env + '\'' +
                ", browser='" + browser + '\'' +
                ", author_id=" + author_id +
                '}';
    }
}
