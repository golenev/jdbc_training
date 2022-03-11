import aquality.selenium.core.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import project.ModelCRUD;
import project.TestEntity;
import utils.DataBase;
import utils.RowsName;

import java.sql.*;
import java.util.List;

import static utils.TestingConfigurations.*;
import static utils.TestingConfigurations.getJdbcData;

public class CRUDTestSecond {
    private final ModelCRUD modelCRUD = new ModelCRUD();

    private Connection connection = null;

    @BeforeClass
    void prepareConnection() {
        String driverName = getJdbcData("/driverName");
        String jdbcUrl = getJdbcData("/jdbcURL");
        String jdbcUserName = getJdbcData("/jdbcUsername");
        String jdbcPassword = getJdbcData("/jdbcPassword");
        connection = DataBase.getConnection(driverName, jdbcUrl, jdbcUserName, jdbcPassword);
    }

    @BeforeMethod
    void searchAndCopyRepeats() {
        Logger.getInstance().info("Precondition started");

        Statement prepareIds = null;
        try {
            prepareIds = connection.createStatement();
            String projectName = "newTest" + Math.random();
            prepareIds.executeUpdate(String.format(getSqlPattern("/insertValueIntoProjectName"), projectName));
            prepareIds.executeUpdate(getSqlQuery("/createNewRowInAuthor"));
            ResultSet resultSetInsertIntoProject = prepareIds.executeQuery(String.format(getSqlPattern("/selectProjectIdWithSomeName"), projectName));
            resultSetInsertIntoProject.next();
            int project_id = resultSetInsertIntoProject.getInt(1);
            ResultSet resultSetInsertIntoAuthor = prepareIds.executeQuery(getSqlQuery("/selectRandomIdFromAuthor"));
            resultSetInsertIntoAuthor.next();
            int author_id = resultSetInsertIntoAuthor.getInt(1);
          Statement  statement = connection.createStatement();
            PreparedStatement rowSelector = connection.prepareStatement(getSqlQuery("/patternSearchForId"));
            ResultSet resultSet = statement.executeQuery(getSqlQuery("/selectRepeats"));
            while (resultSet.next()) {
                int id = resultSet.getInt(RowsName.ID.getValue());
                rowSelector.setInt(1, id);
                ResultSet row = rowSelector.executeQuery();
                Assert.assertTrue(row.next(), "sorry, something went wrong");
                TestEntity testEntity = new TestEntity(row);
                testEntity.setProject_id(project_id);
                testEntity.setAuthor_id(author_id);
                Logger.getInstance().info(String.valueOf(id));
                Logger.getInstance().info(String.valueOf(testEntity));

                Assert.assertTrue(insert(testEntity), "sorry, the data doesn't match");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean insert(TestEntity testEntity) {

        try {
            PreparedStatement rowUpdater = connection.prepareStatement(getSqlPattern("/insertNewRowIntoTest"));
            rowUpdater.setString(1, testEntity.getName());
            rowUpdater.setInt(2, testEntity.getStatus_id());
            rowUpdater.setString(3, testEntity.getMethod_name());
            rowUpdater.setInt(4, testEntity.getProject_id());
            rowUpdater.setInt(5, testEntity.getSession_id());
            rowUpdater.setNull(6, 0);
            rowUpdater.setString(7, testEntity.getEnd_time());
            rowUpdater.setString(8, testEntity.getEnv());
            rowUpdater.setString(9, testEntity.getBrowser());
            rowUpdater.setInt(10, testEntity.getAuthor_id());

            rowUpdater.executeUpdate();
            return true;
        } catch (SQLException ex) {

            return false;
        }
    }

    @Test
    public void simulationStartTest() throws SQLException {
        Logger.getInstance().info("Main test started");

        modelCRUD.update("UPDATE `union_reporting`.`test` SET `status_id` = '1', `project_id` = '4', `session_id` = '12', `browser` = 'firefox' WHERE (`id` = '345');");
        List<TestEntity> metaDataRow = null;
        metaDataRow = modelCRUD.read((getSqlQuery("/justSelect")), connection.createStatement());
        Logger.getInstance().info(metaDataRow.get(metaDataRow.size() - 1).toString());
    }

    @AfterClass
    public void deleteAndCheckValuesTest() {
        Logger.getInstance().info("Postcondition started");

        Logger.getInstance().info("start deleting values from the table");
        modelCRUD.delete(getSqlQuery("/deleteFromTestWhereIdMore450"));
        modelCRUD.delete(getSqlQuery("/deleteAuthorTable"));
        List<TestEntity> deletedRows = null;
        try {
            deletedRows = modelCRUD.read((getSqlQuery("/deleteValueFromTestWhereIdMore345")), connection.createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(deletedRows.isEmpty(), "sorry, the table is not empty");
        Logger.getInstance().info("deletion completed successfully");
    }
}
