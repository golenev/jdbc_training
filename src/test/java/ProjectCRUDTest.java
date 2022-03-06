import org.testng.annotations.DataProvider;
import project.ProjectCRUD;
import org.testng.Assert;
import org.testng.annotations.Test;
import project.ProjectEntity;
import test.TestEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectCRUDTest extends CRUDAbstractTest {
    private final ProjectCRUD projectCRUD = new ProjectCRUD();


    @Test
    void readAllRowsTest() throws SQLException {
        List<ProjectEntity> allRows = projectCRUD.read("select * from project", getStatement());
        Assert.assertFalse(allRows.isEmpty());
    }

    @DataProvider(name = "dataProviderOneRowTest")
    public static Object[][] dataProviderOneRowTest() {
        return new Object[][]{{3, "Bercut"}, {4, "Megafon"}};
    }

    @Test(dataProvider = "dataProviderOneRowTest")
    void readOneRowTest(Integer id, String name) throws SQLException {

        List<ProjectEntity> result = projectCRUD.read("select * from project where id =" + id, getStatement());
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals(result.size(), 1);
        Assert.assertEquals(result.get(0).getName(), name);

    }

    @DataProvider(name = "dataProviderRandomNumber")
    public static Object[][] dataProviderRandomNumber() {
        return new Object[][]{{11}, {22}, {33}, {44}};
    }

    @Test(dataProvider = "dataProviderRandomNumber")
        void test(Integer value) throws SQLException {
    List<TestEntity> result = new ArrayList<>();
        ResultSet resultSet = getStatement().executeQuery("select id,name from test where id like '%" + value + "%' limit 10");
        while (resultSet.next()) {
            result.add(new TestEntity(resultSet.getInt("id"), resultSet.getString("name")));
        }
        System.out.println("++++++++++++++++++++");
        result.stream().map((TestEntity::getId)).forEach(System.out::println);
    }

}
