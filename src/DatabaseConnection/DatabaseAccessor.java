package DatabaseConnection;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.model.*;


public class DatabaseAccessor {
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS %s " + "(%s)";
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;

	public DatabaseAccessor(String url) {
		try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            statement = connection.createStatement();
            initDatabase();
            statement.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("DatabaseAccessor connection opened successfully");
	}
	
    public void createTableIfNotExists(String tableName, String[] columns) {
        try {
            String sql = String.format(CREATE, tableName, String.join(",", columns));
            statement.executeUpdate(sql);
            
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    
    public Connection getConnection() {
    	return connection;
    }

    private boolean isTableExists(String tableName) {
    	if(tableName == null) return false;
    	try {
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName, null);
			return rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    	
    }
    
    
    
    private void initDatabase() {
    	String[] taskColumns = new String[]{
                "taskName TEXT PRIMARY KEY NOT NULL",
                "isActive BOOLEAN NOT NULL"
          
        };
        

        String[] leafColumns = new String[]{
        		"taskFinishTime TEXT PRIMARY KEY NOT NULL",
                "date TEXT NOT NULL",
                "taskName TEXT NOT NULL",
                "leafGot INT NOT NULL"
                };
        // Create the USER, PRODUCT, and ORDERS tables if they don't already exist
        
        String[] musicColumns = new String[] {
        		"id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL",
        		"musicName TEXT NOT NULL",
                "available BOOLEAN NOT NULL",
                "price INT NOT NULL",
                "url TEXT NOT NULL"
        };
       
        
        String[] settingColumns = new String[] {
        		"ID TEXT PRIMARY KEY NOT NULL",
        		"sessionLength INT NOT NULL",
                "breakLength INT NOT NULL",
                "isEndOn BOOLEAN NOT NULL",
                "isBackgroundOn BOOLEAN NOT NULL",
                "bgmID INT"
                
        };
        
        if(!isTableExists("Music")) {
        	 createTableIfNotExists("MUSIC", musicColumns);

     		//Add music in this order
     		Music music1 = new Music( "The Frog Walk", true, 0 ,"resource/music/Aves - The Frog Walk.mp3");
     		music1.addToDB(this);
     		
     		Music music2 = new Music( "A Vivid Frost", false, 10 ,"resource/music/Jon Gegelman - A Vivid Frost.mp3");
     		music2.addToDB(this);
             
     		Music music3 = new Music( "Silent Night", false, 10 ,"resource/music/Jon Gegelman - Half Speed Silent Night.mp3");
     		music3.addToDB(this);  
        }
        
        if(!isTableExists("SETTING")) {
            createTableIfNotExists("SETTING", settingColumns);
    		Setting setting = new Setting(25,5,true,true,1);
    		setting.addToDB(this);
        }
        
    	createTableIfNotExists("TASK", taskColumns);
       
        if(!isTableExists("LEAF")) {
            createTableIfNotExists("LEAF", leafColumns);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String sampleTaskFinishTime = LocalDateTime.now().format(dateFormatter) + " " + "00:00:00";
            String sampleDate = LocalDateTime.now().format(dateFormatter);
    		Leaf leaf = new Leaf(sampleTaskFinishTime, sampleDate,"Try Frog Clock", 10);
    		leaf.addToDB(this);
        }
        
    }

}
