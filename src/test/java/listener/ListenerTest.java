package listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import project.RowsValues;
import utils.DataBase;
import utils.RowsName;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ListenerTest implements ITestListener {

    @Override
    public void onTestSuccess(ITestResult result) {
        String sql = String.format("insert into test (name, status_id, method_name, project_id, session_id, start_time, end_time, env, browser, author_id) \n" +
                        "values ('%s', '3', '%s', 2, 5, '2022-12-22 22:22:22', '2022-12-22 22:22:22', '%s', 'chrome', null);"
                , RowsValues.DONALD_TRUMP.getValue(), RowsValues.MOST_SMART_METHOD.getValue(), RowsValues.JDBC_CONNECTOR.getValue());
        Connection connection = null;
        try {
            connection = DataBase.getConnectionAsSingleton();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
