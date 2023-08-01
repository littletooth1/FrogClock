package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Music extends Data{
	
	private String musicName;
	private boolean available = true;
	private int price;
	private String url;
	private final static String tableName = "MUSIC";
    private static final String INSERT_MUSIC_QUERY = "INSERT INTO %s (musicName,available, price, url) " + "VALUES ('%s', '%s','%s','%s');";
    private static final String UPDATE_MUSIC_QUERY = "UPDATE %s SET available = 'true' where musicName = '%s'";


	public Music(String name, boolean available, int price, String url) {
		this.musicName = name;
		this.available = available;
		this.price = price;
		this.url = url;
	}

	public Music(ResultSet resultSet) throws SQLException {
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
	
	public String getMusickName() {
		return musicName;
	}
	

	@Override
	public String getInsertQuery() {
		
		return String.format(INSERT_MUSIC_QUERY, tableName, musicName, available, price, url);
	}

	@Override
	public String getUpdateQuery(String musicName) {
		
		String s =  String.format(UPDATE_MUSIC_QUERY, tableName, musicName);
		System.out.println(s);
		return s;

}
	
	
}
