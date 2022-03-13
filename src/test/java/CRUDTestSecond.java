import aquality.selenium.core.logging.Logger;
import com.github.javafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import project.ModelCRUD;
import project.TestEntity;
import utils.*;

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
        connection = DataBase.getConnection(getJdbcData("/driverName"), getValidUrl(), getJdbcData("/jdbcUsername"), getJdbcData("/jdbcPassword"));
    }

    @BeforeMethod
    void searchAndCopyRepeats() {
        Logger.getInstance().info("Precondition started");
        Statement prepareIds = null;
        try {
            prepareIds = connection.createStatement();
            String projectName = String.valueOf(StringUtils.buildSomeString(getAdvData("/projectName"), Math.random()));
            prepareIds.executeUpdate(String.format(getSqlPattern("/insertValueIntoProjectName"), projectName));
            prepareIds.executeUpdate(String.format(getSqlQuery("/createNewRowInAuthor"), faker.name().firstName(), faker.name().username(), faker.name().title()));
            ResultSet resultSetInsertIntoProject = prepareIds.executeQuery(String.format(getSqlPattern("/selectProjectIdWithSomeName"), projectName));
            resultSetInsertIntoProject.next();
            int project_id = resultSetInsertIntoProject.getInt(NumsAndIndexes.ONE.getValue());
            ResultSet resultSetInsertIntoAuthor = prepareIds.executeQuery(getSqlQuery("/selectRandomIdFromAuthor"));
            resultSetInsertIntoAuthor.next();
            int author_id = resultSetInsertIntoAuthor.getInt(NumsAndIndexes.ONE.getValue());
            Statement statement = connection.createStatement();
            PreparedStatement rowSelector = connection.prepareStatement(String.format(getSqlQuery("/patternSearchForId"), getValuesOfDB("/tableName")));
            ResultSet resultSet = statement.executeQuery(String.format(getSqlQuery("/selectRepeats"), getValuesOfDB("/tableName"),
                    getListOfExpressions().get(NumsAndIndexes.ZERO.getValue()), getListOfExpressions().get(NumsAndIndexes.ONE.getValue()),
                    getListOfExpressions().get(NumsAndIndexes.TWO.getValue()), getListOfExpressions().get(NumsAndIndexes.THREE.getValue()),
                    getListOfExpressions().get(NumsAndIndexes.FOUR.getValue()), getListOfExpressions().get(NumsAndIndexes.FIVE.getValue()),
                    getListOfExpressions().get(NumsAndIndexes.SIX.getValue()), getListOfExpressions().get(NumsAndIndexes.SEVEN.getValue()),
                    getListOfExpressions().get(NumsAndIndexes.EIGHT.getValue()), getListOfExpressions().get(NumsAndIndexes.EIGHT.getValue()), NumsAndIndexes.TEN.getValue()));
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
            Logger.getInstance().error("sql exception occurred in the method searchAndCopyRepeats()");
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
        } catch
        (SQLException ex) {
            return false;
        }
    }

    @Test
    public void simulationStartTest() {
        Logger.getInstance().info("Main test started");
        modelCRUD.update(String.format(getSqlPattern("/updatePattern"), faker.name(), faker.business(), faker.beer(),
                NumsAndIndexes.THREE_HUNDRED_FORTY_FOUR.getValue()));
        List<TestEntity> metaDataRow = null;
        try {
            metaDataRow = modelCRUD.read((getSqlQuery("/justSelect")), connection.createStatement());
        } catch (SQLException e) {
            Logger.getInstance().error("sql exception occurred in the method simulationStartTest()");
        }
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
                    NumsAndIndexes.THREE_HUNDRED_FORTY_FIVE.getValue())), connection.createStatement());
        } catch (SQLException e) {
            Logger.getInstance().error("sql exception occurred in the method deleteAndCheckValuesTest()");
        }
        Assert.assertTrue(deletedRows.isEmpty(), "sorry, the table is not empty");
        Logger.getInstance().info("deletion completed successfully");
    }
}
