package project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectCRUD {
    public static Statement statement;
    public static String jdbcURL = "jdbc:mysql://localhost:3306/union_reporting?useSSL=false";
    public static String jdbcUsername = "root";
    public static String jdbcPassword = "123456";
    public static PreparedStatement preparedStatement;


    public static void create() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT project  (id, name) VALUES ('9', 'Volgograd');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ProjectEntity> read(String sql, Statement statement) throws SQLException {
        List<ProjectEntity> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            result.add(new ProjectEntity(resultSet.getInt("id"), resultSet.getString("name")));
           // System.out.println(resultSet.getString("id") + resultSet.getString("name"));
        }
        return result;
    }

    public static void update() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        statement = connection.createStatement();
        statement.executeUpdate("update project SET name = 'Wolgograd' where id = 9 ;");
    }

    public static void delete() throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        statement = connection.createStatement();
        statement.executeUpdate("delete from project where id = 9");
    }
}