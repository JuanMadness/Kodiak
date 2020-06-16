package main.java.xyz.rzer.kodiak.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncQuery {

    private Connection connection;
    private String select;
    private Statement sqlStatement;
    private CompletableFuture<ResultSet> completableFuture;



    public AsyncQuery(Connection pConnection, String pSelect) {
        connection = pConnection;
        select = pSelect;
    }



    /**
     * Runs query async
     */
    public void runAsync() {
        completableFuture = CompletableFuture.supplyAsync(() -> {
            try {
                sqlStatement = connection.createStatement();
                return sqlStatement.executeQuery(select);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        });
    }



    /**
     * Blocks until query is done.
     * @return ResultSet from query
     */
    public ResultSet getResult() {
        if (completableFuture != null) {
            try {
                return completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    /**
     * Completes async query with null and closes connection
     */
    public void close() {
        try {
            if (completableFuture != null) {
                completableFuture.complete(null);
                sqlStatement.cancel();
                sqlStatement.close();
            }
            connection.close();
        } catch (SQLException e) {
            //e.printStackTrace();
        }
    }



    /**
     * Completes async query with null
     */
    public void stop() {
        if (completableFuture != null) {
            completableFuture.complete(null);
            try {
                sqlStatement.cancel();
                sqlStatement.close();
            } catch (SQLException e) {
                //e.printStackTrace();
            }
        }
    }
}
