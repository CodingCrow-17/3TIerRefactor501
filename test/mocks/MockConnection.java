package mocks;

import java.sql.Connection;

import controller.MSAccessDatabaseConnectionInterface;

public class MockConnection implements MSAccessDatabaseConnectionInterface{

    @Override
    public Connection getConnection() {
        return null;
    }

    @Override
    public void closeConnection() {
    }
    
}
