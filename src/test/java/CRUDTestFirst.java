import aquality.selenium.core.logging.Logger;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import project.ModelCRUD;
import project.TestEntity;
import utils.DataBase;
import static utils.TestingConfigurations.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static utils.TestingConfigurations.getSqlQuery;
import static utils.GeneratorActualDateAndTime.*;

public class CRUDTestFirst {

    private final ModelCRUD modelCRUD = new ModelCRUD();
    private Connection connection = null;

    @BeforeClass
    void prepareConnection(){
        String driverName = getJdbcData("/driverName");
        String jdbcUrl = getValidUrl();
        String jdbcUserName = getJdbcData("/jdbcUsername");
        String jdbcPassword = getJdbcData("/jdbcPassword");
        connection = DataBase.getConnection(driverName,jdbcUrl, jdbcUserName, jdbcPassword);
    }

    @Test
    void firstTestCase() {
        Assert.assertTrue(true);
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("select * from test order by id desc");
            resultSet.next();
            TestEntity testEntity = new TestEntity(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void onTestSuccess(ITestResult testResult) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("select * from test order by id desc");
        String sql = String.format("insert into test (name, status_id, method_name, project_id, session_id, start_time, end_time, env, browser, author_id) \n" +
                        "values ('%s', '3', '%s', 2, 5, '%s', '%s', '%s', '%s', null);"
                ,testResult.getName(), testResult.getStatus(), getTimeAndDate(), getTimeAndDate(), testResult.getInstanceName(), testResult.getFactoryParameters());
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            List<TestEntity> metaDataRow = null;
            metaDataRow = modelCRUD.read((getSqlQuery("/justSelect")), connection.createStatement());
            Logger.getInstance().info(metaDataRow.get(metaDataRow.size() - 1).toString());
            Assert.assertFalse(metaDataRow.isEmpty(), "sorry the rows is not empty");
            DataBase.closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
