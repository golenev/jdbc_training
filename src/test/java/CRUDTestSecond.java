import aquality.selenium.core.logging.Logger;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import project.ModelCRUD;
import project.TestEntity;
import utils.DataBase;
import utils.GeneratorTestingData;
import utils.NumsAndIndexes;
import utils.RowsName;
import java.sql.*;
import java.util.List;
import static utils.TestingConfigurations.*;
import static utils.TestingConfigurations.getJdbcData;
import static utils.RandomData.*;
import static utils.GeneratorTestingData.*;

public class CRUDTestSecond {
    private final ModelCRUD modelCRUD = new ModelCRUD();
    private Connection connection = null;

    @BeforeClass
    void prepareConnection() {
        String driverName = getJdbcData("/driverName");
        String jdbcUrl = getValidUrl();
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
            int project_id = resultSetInsertIntoProject.getInt(NumsAndIndexes.ONE.getValue());
            ResultSet resultSetInsertIntoAuthor = prepareIds.executeQuery(getSqlQuery("/selectRandomIdFromAuthor"));
            resultSetInsertIntoAuthor.next();
            int author_id = resultSetInsertIntoAuthor.getInt(NumsAndIndexes.ONE.getValue());
            Statement statement = connection.createStatement();
            PreparedStatement rowSelector = connection.prepareStatement(getSqlQuery("/patternSearchForId"));
            ResultSet resultSet = statement.executeQuery(String.format(getSqlQuery("/selectRepeats"),
                    givenList.get(NumsAndIndexes.ZERO.getValue()),givenList.get(NumsAndIndexes.ONE.getValue()),
                    givenList.get(NumsAndIndexes.TWO.getValue()),givenList.get(NumsAndIndexes.THREE.getValue()),
                    givenList.get(NumsAndIndexes.FOUR.getValue()),givenList.get(NumsAndIndexes.FIVE.getValue()),
                    givenList.get(NumsAndIndexes.SIX.getValue()),givenList.get(NumsAndIndexes.SEVEN.getValue()),
                    givenList.get(NumsAndIndexes.EIGHT.getValue()),givenList.get(NumsAndIndexes.NINE.getValue())));
            while (resultSet.next()) {
                int id = resultSet.getInt(RowsName.ID.getValue());
                rowSelector.setInt(NumsAndIndexes.ONE.getValue(), id);
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
            rowUpdater.setString(NumsAndIndexes.ONE.getValue(), testEntity.getName());
            rowUpdater.setInt(NumsAndIndexes.TWO.getValue(), testEntity.getStatus_id());
            rowUpdater.setString(NumsAndIndexes.THREE.getValue(), testEntity.getMethod_name());
            rowUpdater.setInt(NumsAndIndexes.FOUR.getValue(), testEntity.getProject_id());
            rowUpdater.setInt(NumsAndIndexes.FIVE.getValue(), testEntity.getSession_id());
            rowUpdater.setNull(NumsAndIndexes.SIX.getValue(), NumsAndIndexes.ZERO.getValue());
            rowUpdater.setString(NumsAndIndexes.SEVEN.getValue(), testEntity.getEnd_time());
            rowUpdater.setString(NumsAndIndexes.EIGHT.getValue(), testEntity.getEnv());
            rowUpdater.setString(NumsAndIndexes.NINE.getValue(), testEntity.getBrowser());
            rowUpdater.setInt(NumsAndIndexes.TEN.getValue(), testEntity.getAuthor_id());
            rowUpdater.executeUpdate();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Test
    public void simulationStartTest() throws SQLException {
        Logger.getInstance().info("Main test started");
        modelCRUD.update(String.format(getSqlPattern("/updatePattern"), faker.name(), faker.business(), faker.beer(),
                NumsAndIndexes.THREE_HUNDRED_FORTY_FOUR.getValue()));
        List<TestEntity> metaDataRow = null;
        metaDataRow = modelCRUD.read((getSqlQuery("/justSelect")), connection.createStatement());
        Logger.getInstance().info(String.valueOf(metaDataRow.get(NumsAndIndexes.THREE_HUNDRED_FORTY_FOUR.getValue()).toString()));
    }

    @AfterClass
    public void deleteAndCheckValuesTest() {
        Logger.getInstance().info("Postcondition started");
        Logger.getInstance().info("start deleting values from the table");
        modelCRUD.delete(String.format(getSqlQuery("/deleteFromTestWhereIdMoreThanValue"), NumsAndIndexes.THREE_HUNDRED_FORTY_FIVE.getValue()));
        modelCRUD.delete(getSqlQuery("/deleteAuthorTable"));
        List<TestEntity> deletedRows = null;
        try {
            deletedRows = modelCRUD.read((String.format(getSqlQuery("/deleteValueFromTestWhereIdMoreThanValue"),
                    NumsAndIndexes.THREE_HUNDRED_FORTY_FIVE.getValue() )), connection.createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(deletedRows.isEmpty(), "sorry, the table is not empty");
        Logger.getInstance().info("deletion completed successfully");
    }
}
