package project;

import utils.RowsName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectCRUD {
    public static Statement statement;
    public static String jdbcURL = "jdbc:mysql://localhost:3306/union_reporting?useSSL=false";
    public static String jdbcUsername = "root";
    public static String jdbcPassword = "123456";
    public static PreparedStatement preparedStatement;
    public static Connection connection;

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
            result.add(new TestEntity(resultSet.getInt(RowsName.ID.getValue()), resultSet.getString(RowsName.NAME.getValue()),
                    resultSet.getInt(RowsName.STATUS_ID.getValue()),
                    resultSet.getString(RowsName.METHOD_NAME.getValue()), resultSet.getInt(RowsName.PROJECT_ID.getValue()),
                    resultSet.getInt(RowsName.SESSION_ID.getValue()), resultSet.getString(RowsName.START_TIME.getValue()),
                    resultSet.getString(RowsName.END_TIME.getValue()), resultSet.getString(RowsName.ENV.getValue()),
                    resultSet.getString(RowsName.BROWSER.getValue()),
                    resultSet.getInt(RowsName.AUTHOR_ID.getValue())));
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