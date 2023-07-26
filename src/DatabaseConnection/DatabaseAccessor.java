package DatabaseConnection;
import java.sql.*;


public class DatabaseAccessor {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS %s " + "(%s)";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public DatabaseAccessor(String url) {
		try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            // Define column names and data types for each table
            String[] taskColumns = new String[]{
                    "taskName TEXT PRIMARY KEY NOT NULL",
                    "isActive BOOLEAN NOT NULL"
            };

            // Create the USER, PRODUCT, and ORDERS tables if they don't already exist
            createTableIfNotExists("TASK", taskColumns);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("DatabaseAccessor connection opened successfully");
	}
	
    public void createTableIfNotExists(String tableName, String[] columns) {
        try {
            statement = connection.createStatement();
            String sql = String.format(CREATE, tableName, String.join(",", columns));
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public Connection getConnection() {
    	return connection;
    }

}
