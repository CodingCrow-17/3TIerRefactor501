package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseUtilitiesInterface {
    public ResultSet runQuery(Connection connection, String sql) throws SQLException;
    public boolean execute(Connection connection, String sql) throws SQLException;
}
