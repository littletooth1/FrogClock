package application.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import DatabaseConnection.DatabaseAccessor;

public class Music extends Data{
	
	private int id;
	private String musicName;
	private boolean available = true;
	private int price;
	private String url;

	private final static String tableName = "MUSIC";
    private static final String INSERT_MUSIC_QUERY = "INSERT INTO %s (id, musicName,available, price, url) " + "VALUES (NULL,'%s', '%s','%s','%s');";
    private static final String UPDATE_MUSIC_QUERY = "UPDATE %s SET available = 'true' where musicName = '%s'";
    private static final String SELECT_MUSIC_QUERY = "SELECT * FROM MUSIC where id = %d";


	public Music(String name, boolean available, int price, String url) {
		this.musicName = name;
		this.available = available;
		this.price = price;
		this.url = url;
	}

	public Music(ResultSet resultSet) throws SQLException {
		id = resultSet.getInt("id");
		musicName = resultSet.getString("musicName");
		available = resultSet.getBoolean("available");
		price = resultSet.getInt("price");
		url = resultSet.getString("url");
	}
	
	public void setTaskName(String newTaskName) {
		musicName = newTaskName;
	}
	
	public void setAvailable(boolean newAvailable) {
		available = newAvailable;
	}
	
	public String getMusicName() {
		return musicName;
	}
	
	public int getMusicId() {
		return id;
	}
	
	public String getMusicURL() {
		return url;
	}
	

	@Override
	public String getInsertQuery() {
		return String.format(INSERT_MUSIC_QUERY, tableName, musicName, available, price, url);
	}

	@Override
	public String getUpdateQuery(String id) {
		String s =  String.format(UPDATE_MUSIC_QUERY, tableName, id);
		System.out.println(s);
		return s;

	}
	
	
	public static String updateQuery(int id) {
		String s =  String.format(UPDATE_MUSIC_QUERY, tableName, id);
		System.out.println(s);
		return s;
	}

	
	
	//add statement argument
	public static List<Music> getBoughtMusic(DatabaseAccessor db, Statement statement){
		String sql = "SELECT * From MUSIC WHERE available = 'true'";
		List<Music> boughtMusics = new ArrayList<>(); 

		try {
            ResultSet resultSet = Data.excuateQuerySql(sql, db, statement);
            while (resultSet.next()) {
            	boughtMusics.add(new Music(resultSet));
            }
            return boughtMusics;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getErrorCode());
        }
        return null;
	}


	//Get music by id
	public static Music gettMusic(DatabaseAccessor db, Statement statement, int id) {
		String s =  String.format(SELECT_MUSIC_QUERY, id);
		System.out.println(s);
		ResultSet resultSet = Data.excuateQuerySql(s, db, statement);
		Music music = null;
		try {
			music = new Music(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return music;
	}

	
	//update availability
	public static void updateAvailability(DatabaseAccessor db, Statement statement, int id) throws SQLException {
		String s =  String.format(SELECT_MUSIC_QUERY, id);
		ResultSet resultSet = Data.excuateQuerySql(s, db, statement);
		while(resultSet.next()) {	
			Music music1 = new Music(resultSet);
			System.out.print(music1);
			music1.updateToDB(db, music1.getMusicName());
		}
		
	}
	
}
