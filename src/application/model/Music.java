package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import DatabaseConnection.DatabaseAccessor;

public class Music extends Data{
	
	private int id;
	private String musicName;
	private boolean available = true;
	private int price;
	private String url;
	
	private final static String tableName = "MUSIC";
    private static final String INSERT_TASK_QUERY = "INSERT INTO %s (id,musicName,available, price,url) " + "VALUES (NULL,'%s', %s,'%s','%s');";
    private static final String UPDATE_TASK_QUERY = "UPDATE %s SET available = 'false' where musicName = '%s'";


	public Music(String name, boolean available, int price,String url) {
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
	
	public void setIsActive(boolean newIsAvailable) {
		available = newIsAvailable;
	}
	
	public String getMusicName() {
		return musicName;
	}
	

	@Override
	public String getInsertQuery() {
		
		return String.format(INSERT_TASK_QUERY, tableName, musicName, available, price,url);
	}

	@Override
	public String getUpdateQuery(String musicName) {
		
		String s =  String.format(UPDATE_TASK_QUERY, tableName, musicName);
		System.out.println(s);
		return s;
    }
	
	
	public static List<Music> getBoughtMusic(DatabaseAccessor db){
		String sql = "SELECT * From MUSIC WHERE available = 0";
		List<Music> boughtMusics = new ArrayList<>(); 
		try {
            ResultSet resultSet = Data.excuateQuerySql(sql, db);
            while (resultSet.next()) {
            	boughtMusics.add(new Music(resultSet));
            }
            return boughtMusics;
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getErrorCode());
        }
        return null;
	}
}
