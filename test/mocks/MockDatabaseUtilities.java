package mocks;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.DatabaseUtilitiesInterface;

public class MockDatabaseUtilities implements DatabaseUtilitiesInterface{

    private String lastSqlUsed = "default";
    private ArrayList<String> sqlHistory = new ArrayList<>();

    private static MockDatabaseUtilities instance = null;

    public static MockDatabaseUtilities getInstance(){
        if (instance == null) {
            instance = new MockDatabaseUtilities();
        }
        return instance;
    }

    private MockDatabaseUtilities(){};

    public String getLastSqlUsed(){
        return this.lastSqlUsed;
    }

    public ArrayList<String> getSqlHistory(){
        return this.sqlHistory;
    }

    public void reset(){
        this.lastSqlUsed = "";
        this.sqlHistory = new ArrayList<>();
    }

    @Override
    public ResultSet query(String sql) throws SQLException {
        this.sqlHistory.add(sql);
        this.lastSqlUsed = sql;
        return new MockResultSet();
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        this.lastSqlUsed = sql;
        this.sqlHistory.add(sql);
        return true;
    }   
}
