import org.testng.ITestResult;
import project.ProjectCRUD;
import org.testng.Assert;
import org.testng.annotations.Test;
import project.TestEntity;
import utils.DataBase;
import utils.RowsName;

import static utils.TestingConfigurations.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectCRUDTest extends CRUDAbstractTest {
    private final ProjectCRUD projectCRUD = new ProjectCRUD();
    private Statement statement = null;



    @Test
    void readAllRowsTest() throws SQLException {
        List<TestEntity> allRows = projectCRUD.read(getSqlQuery("/justSelect"), getStatement());
        Assert.assertFalse(allRows.isEmpty());
        System.out.println(allRows.get(2).toString());
    }


    @Test
    void secondTestCase() throws SQLException, IOException, ClassNotFoundException {
       Connection connection = DataBase.getConnectionAsSingleton();
        Statement prepareIds = connection.createStatement();
        prepareIds.executeUpdate("insert into project (name) values ('newTest" + Math.random() + "')");
        prepareIds.executeUpdate("insert into author (name, login, email) values ('newAuthor7', 'newLogin7', 'newemail7@mail.ru')");
        ResultSet resultSet1 = prepareIds.executeQuery("select id from project order by id desc;");
        resultSet1.next();
        project_id = resultSet1.getInt(1);
        resultSet1 = prepareIds.executeQuery("select id from author order by id desc;");
        resultSet1.next();
        author_id = resultSet1.getInt(1);
        statement = connection.createStatement();
        PreparedStatement rowSelector = DataBase.getConnectionAsSingleton().prepareStatement(getSqlQuery("/patternSearchForId"));
        List<Integer> result = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery(getSqlQuery("/selectRepeats"));
        while (resultSet.next()) {
            int id = resultSet.getInt(RowsName.ID.getValue());
            rowSelector.setInt(1, id);
            ResultSet row = rowSelector.executeQuery();
            Assert.assertTrue(row.next());
            TestEntity test = new TestEntity(row);
            test.setProject_id(project_id);
            test.setAuthor_id(author_id);
            System.out.println(id);
            System.out.println(test);
            Assert.assertTrue(test.insert());
        }
    }

        @Test
    void deleteValue() throws SQLException {
        projectCRUD.delete("DELETE FROM `union_reporting`.`test` WHERE `id` > 345;");
            projectCRUD.delete("DELETE FROM `union_reporting`.`author`;");
    }



}
