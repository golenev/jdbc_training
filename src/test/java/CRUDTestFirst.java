import aquality.selenium.core.logging.Logger;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import project.ModelCRUD;
import project.RowsValues;
import project.TestEntity;
import utils.DataBase;
import static utils.TestingConfigurations.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static utils.TestingConfigurations.getSqlQuery;

public class CRUDTestFirst {

    private final ModelCRUD modelCRUD = new ModelCRUD();
    private Connection connection = null;

    @BeforeClass
    void prepareConnection(){
        String driverName = getJdbcData("/driverName");
        String jdbcUrl = getJdbcData("/jdbcURL");
        String jdbcUserName = getJdbcData("/jdbcUsername");
        String jdbcPassword = getJdbcData("/jdbcPassword");
        connection = DataBase.getConnection(driverName,jdbcUrl, jdbcUserName, jdbcPassword);
    }

    @Test
    void firstTestCase() {
        Assert.assertTrue(true);
        try {
           /* String driverName = getJdbcData("/driverName");
            String jdbcUrl = getJdbcData("/jdbcURL");
            String jdbcUserName = getJdbcData("/jdbcUsername");
            String jdbcPassword = getJdbcData("/jdbcPassword");
            Connection connection = DataBase.getConnection(driverName,jdbcUrl, jdbcUserName, jdbcPassword);*/
            ResultSet resultSet = connection.createStatement().executeQuery("select * from test order by id desc");
            resultSet.next();
            TestEntity testEntity = new TestEntity(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void onTestSuccess() throws SQLException {

        ResultSet resultSet = connection.createStatement().executeQuery("select * from test order by id desc");
        String sql = String.format("insert into test (name, status_id, method_name, project_id, session_id, start_time, end_time, env, browser, author_id) \n" +
                        "values ('%s', '3', '%s', 2, 5, '2022-12-22 22:22:22', '2022-12-22 22:22:22', '%s', 'chrome', null);"
                , RowsValues.DONALD_TRUMP.getValue(), RowsValues.MOST_SMART_METHOD.getValue(), RowsValues.JDBC_CONNECTOR.getValue());

        try {

            Statement statement = connection.createStatement();
            statement.execute(sql);
            List<TestEntity> metaDataRow = null;
            metaDataRow = modelCRUD.read((getSqlQuery("/justSelect")), connection.createStatement());
            Logger.getInstance().info(metaDataRow.get(metaDataRow.size() - 1).toString());
            Assert.assertFalse(metaDataRow.isEmpty());
            Assert.assertEquals(metaDataRow.get(metaDataRow.size() - 1).getName(), RowsValues.DONALD_TRUMP.getValue(), "sorry, the data doesn't match");
            Assert.assertEquals(metaDataRow.get(metaDataRow.size() - 1).getMethod_name(), RowsValues.MOST_SMART_METHOD.getValue(), "sorry, the data doesn't match");
            Assert.assertEquals(metaDataRow.get(metaDataRow.size() - 1).getEnv(), RowsValues.JDBC_CONNECTOR.getValue(), "sorry, the data doesn't match");
            DataBase.closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
