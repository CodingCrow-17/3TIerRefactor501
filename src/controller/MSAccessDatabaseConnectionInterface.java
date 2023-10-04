package controller;

import java.sql.Connection;

public interface MSAccessDatabaseConnectionInterface {
    public Connection getConnection();
    public void closeConnection();
}
