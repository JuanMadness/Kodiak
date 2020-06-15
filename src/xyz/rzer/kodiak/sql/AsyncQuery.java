package xyz.rzer.kodiak.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AsyncQuery {

    private Connection connection;
    private String select;

    public AsyncQuery(Connection pConnection, String pSelect) {
        connection = pConnection;
        select = pSelect;
    }



    /**
     * Runs query async
     */
    public void runAsync() {

    }



    /**
     * Blocks until query is done.
     * @return ResultSet from query
     */
    public ResultSet getResult() {
        return null;
    }



    /**
     * Stops thread and closes connection
     */
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    /**
     * Stops thread
     */
    public void stop() {

    }
}
