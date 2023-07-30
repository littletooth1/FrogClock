package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Music extends Data{
	
	private String musicName;
	private boolean available = true;
	private int price;
	private final static String tableName = "MUSIC";
    private static final String INSERT_MUSIC_QUERY = "INSERT INTO %s (musicName,available, price) " + "VALUES ('%s', '%s','%s');";
    private static final String UPDATE_MUSIC_QUERY = "UPDATE %s SET available = 'false' where musicName = '%s'";


	public Music(String name, boolean available, int price) {
		this.musicName = name;
		this.available = available;
		this.price = price;
	}

	public Music(ResultSet resultSet) throws SQLException {
		musicName = resultSet.getString("musicName");
		available = resultSet.getBoolean("available");
		price = resultSet.getInt("price");
	}
	
	public void setTaskName(String newTaskName) {
		musicName = newTaskName;
	}
	
	public void setIsActive(boolean newIsAvailable) {
		available = newIsAvailable;
	}
	
	public String getTaskName() {
		return musicName;
	}
	

	@Override
	public String getInsertQuery() {
		
		return String.format(INSERT_MUSIC_QUERY, tableName, musicName, available, price);
	}

	@Override
	public String getUpdateQuery(String musicName) {
		
		String s =  String.format(UPDATE_MUSIC_QUERY, tableName, musicName);
		System.out.println(s);
		return s;


}
}
