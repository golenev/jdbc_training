import com.mysql.cj.exceptions.AssertionFailedException;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;
import project.ProjectCRUD;
import org.testng.Assert;
import org.testng.annotations.Test;
import project.TestEntity;
import utils.RowsName;

import static utils.TestingConfigurations.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectCRUDTest extends CRUDAbstractTest {
    private final ProjectCRUD projectCRUD = new ProjectCRUD();


    @Test
    void justTest(ITestResult result) {
        Assert.assertTrue(true);
    }


    @Test
    void readAllRowsTest() throws SQLException {
        List<TestEntity> allRows = projectCRUD.read(getSqlQuery("/justSelect"), getStatement());
        Assert.assertFalse(allRows.isEmpty());
        System.out.println(allRows.get(2).toString());
    }


    @Test
    void searchRepeatsTest() throws SQLException, IOException, ClassNotFoundException {
        PreparedStatement rowSelector = getConnection().prepareStatement(getSqlQuery("/patternSearchForId"));
        List<Integer> result = new ArrayList<>();
        ResultSet resultSet = getStatement().executeQuery(getSqlQuery("/selectRepeats"));
        while (resultSet.next()) {
            int id = resultSet.getInt(RowsName.ID.getValue());
            rowSelector.setInt(1, id);
            ResultSet row = rowSelector.executeQuery();
            Assert.assertTrue(row.next());
            TestEntity test = new TestEntity(row);
            System.out.println(id);
            System.out.println(test);

            // Assert.assertTrue(test.insert());

        }

    }

    @Test
    void addTestResultTest() throws ClassNotFoundException, SQLException {

        projectCRUD.create("INSERT INTO `union_reporting`.`test` (`id`, `name`, `status_id`, `method_name`, `project_id`, `session_id`, `start_time`, `end_time`, `env`, `browser`) VALUES ('346', 'qweqwe', '3', 'qweasd', '5', '12', '2022-02-22 10:45:06', '2022-02-22 22:22:22', 'qweqwe', 'chrome');");
    }

    @Test
    void deleteValue() throws SQLException {
        projectCRUD.delete("delete from test where id = 346");
    }

    @Test
    void updateValue() throws SQLException {
        projectCRUD.update("update test SET name = 'updateName' where id = 345 ;");
    }

}
