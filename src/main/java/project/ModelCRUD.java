package project;

import aquality.selenium.core.logging.Logger;
import utils.RowsName;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.TestingConfigurations.*;

public class ModelCRUD {
    public static Statement statement;

    public List<TestEntity> read(String sql, Statement statement) {
        List<TestEntity> result = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            Logger.getInstance().error("sql exception occurred in the method read()");
        }
        while (true) {
            try {
                if (!resultSet.next()) break;

                result.add(new TestEntity(resultSet.getInt(RowsName.ID.getValue()), resultSet.getString(RowsName.NAME.getValue()),
                        resultSet.getInt(RowsName.STATUS_ID.getValue()),
                        resultSet.getString(RowsName.METHOD_NAME.getValue()), resultSet.getInt(RowsName.PROJECT_ID.getValue()),
                        resultSet.getInt(RowsName.SESSION_ID.getValue()), resultSet.getString(RowsName.START_TIME.getValue()),
                        resultSet.getString(RowsName.END_TIME.getValue()), resultSet.getString(RowsName.ENV.getValue()),
                        resultSet.getString(RowsName.BROWSER.getValue()),
                        resultSet.getInt(RowsName.AUTHOR_ID.getValue())));
            } catch (SQLException e) {
                Logger.getInstance().error("sql exception occurred in the method read()");
            }
        }
        return result;
    }

    public void update(String query) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(getValidUrl(),
                    getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getInstance().error("sql exception occurred in the method update()");
        }
    }

    public void delete(String query) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(getValidUrl(),
                    getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
            statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            Logger.getInstance().error("sql exception occurred in the method delete()");
        }
    }

}