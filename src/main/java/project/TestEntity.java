package project;

import utils.DataBase;
import utils.NumsAndIndexes;

import static utils.TestingConfigurations.*;

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


    public TestEntity(ResultSet resultSet) {
        try {
            this.id = resultSet.getInt(NumsAndIndexes.ONE.getValue());
            this.name = resultSet.getString(NumsAndIndexes.TWO.getValue());
            this.status_id = resultSet.getInt(NumsAndIndexes.THREE.getValue());
            this.method_name = resultSet.getString(NumsAndIndexes.FOUR.getValue());
            this.project_id = resultSet.getInt(NumsAndIndexes.FIVE.getValue());
            this.session_id = resultSet.getInt(NumsAndIndexes.SIX.getValue());
            this.start_time = resultSet.getString(NumsAndIndexes.SEVEN.getValue());
            this.end_time = resultSet.getString(NumsAndIndexes.EIGHT.getValue());
            this.env = resultSet.getString(NumsAndIndexes.NINE.getValue());
            this.browser = resultSet.getString(NumsAndIndexes.TEN.getValue());
            this.author_id = resultSet.getInt(NumsAndIndexes.ELEVEN.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
