import aquality.selenium.core.logging.Logger;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import project.ModelCRUD;
import project.TestEntity;
import utils.DataBase;
import utils.NumsAndIndexes;

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
            ResultSet resultSet = connection.createStatement().executeQuery(getSqlQuery("/selectAndSort"));
            resultSet.next();
            TestEntity testEntity = new TestEntity(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void onTestSuccess(ITestResult testResult) throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery(getSqlQuery("/selectAndSort"));
        String sql = String.format(getSqlPattern("/insertNewDataToRowTest")
                ,testResult.getName(), NumsAndIndexes.THREE.getValue(),  testResult.getStatus(),
                NumsAndIndexes.TWO.getValue(), NumsAndIndexes.FIVE.getValue(),
                getTimeAndDate(), getTimeAndDate(), testResult.getInstanceName(), testResult.getFactoryParameters());
        try {
            Statement statement = connection.createStatement();
            statement.execute(sql);
            List<TestEntity> metaDataRow = null;
            metaDataRow = modelCRUD.read((getSqlQuery("/justSelect")), connection.createStatement());
            Logger.getInstance().info(metaDataRow.get(metaDataRow.size() - NumsAndIndexes.ONE.getValue()).toString());
            Assert.assertFalse(metaDataRow.isEmpty(), "sorry the rows is not empty");
            DataBase.closeDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
