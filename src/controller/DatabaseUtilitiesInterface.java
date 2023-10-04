package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseUtilitiesInterface {
    public ResultSet query(String sql) throws SQLException;
    public boolean execute(String sql) throws SQLException;
}
