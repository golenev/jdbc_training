import aquality.selenium.core.logging.Logger;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import listener.ListenerTest;
import org.testng.annotations.Listeners;
import project.ProjectCRUD;
import org.testng.Assert;
import org.testng.annotations.Test;
import project.RowsValues;
import project.TestEntity;
import utils.DataBase;
import utils.RowsName;
import static utils.TestingConfigurations.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@Listeners(ListenerTest.class)
public class ProjectCRUDTest {
    private final ProjectCRUD projectCRUD = new ProjectCRUD();
    private Statement statement = null;

    @Test
    void firstTestCase() {
        Assert.assertTrue(true);
        Connection connection = DataBase.getConnectionAsSingleton();
        List<TestEntity> metaDataRow = null;
        try {
            metaDataRow = projectCRUD.read((getSqlQuery("/justSelect")), connection.createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Logger.getInstance().info(metaDataRow.get(metaDataRow.size() - 1).toString());
        Assert.assertFalse(metaDataRow.isEmpty());
        Assert.assertEquals(metaDataRow.get(metaDataRow.size() - 1).getName(), RowsValues.DONALD_TRUMP.getValue(), "sorry, the data doesn't match");
        Assert.assertEquals(metaDataRow.get(metaDataRow.size() - 1).getMethod_name(), RowsValues.MOST_SMART_METHOD.getValue(), "sorry, the data doesn't match");
        Assert.assertEquals(metaDataRow.get(metaDataRow.size() - 1).getEnv(), RowsValues.JDBC_CONNECTOR.getValue(), "sorry, the data doesn't match");
    }

    @Test
    void secondTestCase() {
        Connection connection = DataBase.getConnectionAsSingleton();
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
            statement = connection.createStatement();
            PreparedStatement rowSelector = DataBase.getConnectionAsSingleton().prepareStatement(getSqlQuery("/patternSearchForId"));
            ResultSet resultSet = statement.executeQuery(getSqlQuery("/selectRepeats"));
            while (resultSet.next()) {
                int id = resultSet.getInt(RowsName.ID.getValue());
                rowSelector.setInt(1, id);
                ResultSet row = rowSelector.executeQuery();
                Assert.assertTrue(row.next(), "sorry, something went wrong");
                TestEntity test = new TestEntity(row);
                test.setProject_id(project_id);
                test.setAuthor_id(author_id);
                Logger.getInstance().info(String.valueOf(id));
                Logger.getInstance().info(String.valueOf(test));
                Assert.assertTrue(test.insert(), "sorry, the data doesn't match");
            }
            Logger.getInstance().info("start deleting values from the table");
            projectCRUD.delete(getSqlQuery("/deleteFromTestWhereIdMore450"));
            projectCRUD.delete(getSqlQuery("/deleteAuthorTable"));
            List<TestEntity> deletedRows = projectCRUD.read((getSqlQuery("/deleteValueFromTestWhereIdMore345")), connection.createStatement());
            Assert.assertTrue(deletedRows.isEmpty(), "sorry, the table is not empty");
            Logger.getInstance().info("deletion completed successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
