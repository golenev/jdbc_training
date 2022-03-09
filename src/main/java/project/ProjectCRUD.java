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


    public void create(String sql) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<TestEntity> read(String sql, Statement statement) throws SQLException {
        List<TestEntity> result = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            result.add(new TestEntity(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("status_id"),
                    resultSet.getString("method_name"), resultSet.getInt("project_id"),
                    resultSet.getInt("session_id"), resultSet.getString("start_time"),
                    resultSet.getString("end_time"), resultSet.getString("env"), resultSet.getString("browser"),
                    resultSet.getInt("author_id")));

        }
        return result;
    }

    public void update(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void delete(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}