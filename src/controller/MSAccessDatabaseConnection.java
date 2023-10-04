package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
 
public class MSAccessDatabaseConnection implements MSAccessDatabaseConnectionInterface{
    private static MSAccessDatabaseConnection instance = null;

    private String msAccessDatabaseName;
    private Connection currentConnection = null;

    public static MSAccessDatabaseConnection createConnection(String msAccessDatabaseName) {
        if (instance == null){
            instance = new MSAccessDatabaseConnection(msAccessDatabaseName);
            return instance;
        }
        return instance;
    }

    private MSAccessDatabaseConnection(String msAccessDatabaseName){
        this.msAccessDatabaseName = msAccessDatabaseName;
    };

    public Connection getConnection(){
        // Step 1: Loading or registering Oracle JDBC driver class
        try {
 
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
        }
        catch(ClassNotFoundException cnfex) {
 
            System.out.println("Problem in loading or "
                    + "registering MS Access JDBC driver");
            cnfex.printStackTrace();
        }
 
        // Step 2: Opening database connection
        try {
 
            String dbURL = "jdbc:ucanaccess://" + this.msAccessDatabaseName; 
 
            // Step 2.A: Create and get connection using DriverManager class
            currentConnection = DriverManager.getConnection(dbURL); 
 
        }
        catch(SQLException sqlex){
            sqlex.printStackTrace();
        }
    
        return currentConnection;
    }

    public void closeConnection(){
        try {            
        	if(null != currentConnection) {
                // and then finally close connection
                currentConnection.close();
            }
        }
	    catch (SQLException sqlex) {
	        sqlex.printStackTrace();
	    }
    }
}
