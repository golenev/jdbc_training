package db;

import java.sql.*;

public class Driver {
    public static Statement statement;
    public static String jdbcURL = "jdbc:mysql://localhost:3306/union_reporting?useSSL=false";
    public static String jdbcUsername = "root";
    public static String jdbcPassword = "123456";
    public static PreparedStatement preparedStatement;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
   delete();
    }


    public static void create() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        statement = connection.createStatement();
        statement.executeUpdate("INSERT project  (id, name) VALUES ('9', 'Volgograd');");

    }

    public static void read() throws SQLException {
        ResultSet resultSet = statement.executeQuery("select * from project");
        while (resultSet.next()) {
            System.out.println(resultSet.getString("id") + resultSet.getString("name"));
        }

    }

    public static void update () throws SQLException {
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