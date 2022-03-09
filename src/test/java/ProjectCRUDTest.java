import com.mysql.cj.exceptions.AssertionFailedException;
import org.testng.annotations.DataProvider;
import project.ProjectCRUD;
import org.testng.Assert;
import org.testng.annotations.Test;
import project.TestEntity;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectCRUDTest extends CRUDAbstractTest {
    private final ProjectCRUD projectCRUD = new ProjectCRUD();


    @Test
    void justTest() {
        Assert.assertTrue(true);
    }


    @Test
    void readAllRowsTest() throws SQLException {
        List<TestEntity> allRows = projectCRUD.read("select * from test", getStatement());
        Assert.assertFalse(allRows.isEmpty());
        System.out.println(allRows.get(2).toString());
    }

    @DataProvider(name = "dataProviderOneRowTest")
    public static Object[][] dataProviderOneRowTest() {
        return new Object[][]{{3, "KS-4353 Verify that Connect Partner can be saved with required fields"}, {4, "KS-1806 Verify that RTB Bidder Configuration can be saved"}};
    }

    @Test(dataProvider = "dataProviderOneRowTest")
    void readOneRowTest(Integer id, String name) throws SQLException {

        List<TestEntity> result = projectCRUD.read("select * from test where id =" + id, getStatement());
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getName(), name);

    }


    @Test
    void searchRepeatsTest() throws SQLException, IOException, ClassNotFoundException {
        int projectId = 123;
        int authorId = 456;
        PreparedStatement rowSelector = getConnection().prepareStatement("select * from test where id = ?");
           /* PreparedStatement rowUpdater = getConnection().prepareStatement("insert into test (`name`, `status_id`, " +
                    "`method_name`, `project_id`, `session_id`, `start_time`, `end_time`, `env`, `browser`, 'author_id')" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?)");*/
        List<Integer> result = new ArrayList<>();
        ResultSet resultSet = getStatement().executeQuery("select  id from test  where \n" +
                "id like '%0%0%' or\n" +
                "id like '%1%1%' or\n" +
                "id like '%2%2%' or\n" +
                "id like '%3%3%' or\n" +
                "id like '%4%4%' or\n" +
                "id like '%5%5%' or\n" +
                "id like '%6%6%' or\n" +
                "id like '%7%7%' or\n" +
                "id like '%8%8%' or\n" +
                "id like '%9%9%' \n" +
                " limit 10");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            rowSelector.setInt(1, id);
            ResultSet row = rowSelector.executeQuery();
            Assert.assertTrue(row.next());
            TestEntity test = new TestEntity(row);

            Assert.assertTrue(test.insert());

        }

    }

    @Test
    void addTestResultTest() throws ClassNotFoundException {
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
