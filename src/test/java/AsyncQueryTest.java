import org.junit.Assert;
import org.junit.Test;
import xyz.rzer.kodiak.sql.AsyncQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AsyncQueryTest {

    @Test
    public void execute() {
        try {
            Class.forName("org.h2.Driver");
            Connection tConnection = DriverManager.getConnection("jdbc:h2:mem:data;");
            Statement tStatement = tConnection.createStatement();
            tStatement.execute("create table asyncTest (test varchar(50))");
            tStatement.execute("insert into asyncTest(test) values('test1'), ('test2')");

            AsyncQuery tAsyncQuery = new AsyncQuery(tConnection, "select * from asyncTest");
            tAsyncQuery.runAsync();
            ResultSet tResultSet = tAsyncQuery.getResult();

            Assert.assertNotNull(tResultSet);

            tResultSet.next();
            String tResultString = tResultSet.getString(1);
            Assert.assertEquals("AsyncQuery ResultSet", "test1", tResultString);

            tAsyncQuery.stop();
            tAsyncQuery.close();


            //System.out.println(tConnection.isClosed());
            Assert.assertTrue(tConnection.isClosed());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
