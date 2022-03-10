package project;

import utils.DataBase;
import static utils.TestingConfigurations.*;
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
    private int author_id;
    private static Properties prop = new Properties();
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
        this.author_id = resultSet.getInt(11);
    }

    public TestEntity(int id, String name, int status_id, String method_name, int project_id,
                      int session_id, String start_time, String end_time, String env,
                      String browser, int author_id) {
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

    public boolean insert() throws SQLException, IOException, ClassNotFoundException {
        if (rowUpdater == null) {
            Connection connection = DataBase.getConnectionAsSingleton();
            rowUpdater = connection.prepareStatement(getSqlPattern("/insertNewRowIntoTest"));
        }
        rowUpdater.setString(1, this.getName());
        rowUpdater.setInt(2, this.getStatus_id());
        rowUpdater.setString(3, this.getMethod_name());
        rowUpdater.setInt(4, this.getProject_id());
        rowUpdater.setInt(5, this.getSession_id());
        rowUpdater.setNull(6, 0);
        rowUpdater.setString(7, this.getEnd_time());
        rowUpdater.setString(8, this.getEnv());
        rowUpdater.setString(9, this.getBrowser());
        rowUpdater.setInt(10, this.getAuthor_id());
        try {
            rowUpdater.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
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
