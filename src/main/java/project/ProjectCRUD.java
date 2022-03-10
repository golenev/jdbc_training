package project;

import utils.RowsName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.TestingConfigurations.*;

public class ProjectCRUD {
    public static Statement statement;


    public void create(String sql) throws ClassNotFoundException, SQLException {
        Class.forName(getJdbcData("/driverName"));
        Connection connection = DriverManager.getConnection(getJdbcData("/jdbcURL"),
                getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
        statement = connection.createStatement();
        statement.executeUpdate(sql);
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
        Connection connection = DriverManager.getConnection(getJdbcData("/jdbcURL"),
                getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
        statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    public void delete(String query) throws SQLException {
        Connection connection = DriverManager.getConnection(getJdbcData("/jdbcURL"),
                getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
        statement = connection.createStatement();
        statement.executeUpdate(query);
    }
}