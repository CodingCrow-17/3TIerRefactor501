package controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtilities implements DatabaseUtilitiesInterface{

	private static DatabaseUtilities instance = null;
	private MSAccessDatabaseConnection connection;

	public static DatabaseUtilities createDatabaseUtilities(){
		if (instance == null){
			instance = new DatabaseUtilities();
		}
		return instance;
	}

	private DatabaseUtilities(){
		connection = new MSAccessDatabaseConnection();
	};

    public void closeConnection() {
        if(null != connection) {
            connection.closeConnection();
        }
    }
    
    public ResultSet query(String sql) throws SQLException{
    	ResultSet resultSet = null;
    	Statement statement = null;

    	// Create JDBC Statement 
		statement = connection.createStatementThatReturns();
		
		if (statement != null) {
    		// Execute SQL; retrieve data into ResultSet
			try {
				resultSet = statement.executeQuery(sql);
    		} catch (net.ucanaccess.jdbc.UcanaccessSQLException e) {
    			//SQL exception; handle but also log
    			e.printStackTrace();
    		} 
		}
		
        return resultSet;
    }
    
    public boolean execute(String sql) throws SQLException {

    	Statement statement = null;
    	boolean result = false;

    	// Create JDBC Statement 
    	statement = connection.createStatement();

    	if (statement != null) {
    		// Execute SQL; retrieve data into ResultSet
    		try {
				result = statement.execute(sql);
    		} catch (net.ucanaccess.jdbc.UcanaccessSQLException e) {
    			//SQL exception; handle but also log
    			e.printStackTrace();
    		} 
    		
    	}

    	return result;
    }
    
	private class MSAccessDatabaseConnection{
		private final String DATABASEFLENAME = "res/LanguageAndCountryDatabase.accdb";
		private Connection currentConnection = null;

		public MSAccessDatabaseConnection(){
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
	
				String dbURL = "jdbc:ucanaccess://" + DATABASEFLENAME; 
	
				// Step 2.A: Create and get connection using DriverManager class
				this.currentConnection = DriverManager.getConnection(dbURL); 
	
			}
			catch(SQLException sqlex){
				sqlex.printStackTrace();
			}
		}

		public Statement createStatementThatReturns() throws SQLException {
			return this.currentConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}

		public Statement createStatement() throws SQLException {
			return this.currentConnection.createStatement();
		}

		public void closeConnection(){
			try {            
				if(null != currentConnection) {
					// and then finally close connection
					this.currentConnection.close();
					this.currentConnection = null;
				}
			}
			catch (SQLException sqlex) {
				sqlex.printStackTrace();
			}
		}
	}
}
