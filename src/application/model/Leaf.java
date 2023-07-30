package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Leaf extends Data{
	
	private final static String tableName = "LEAF";
	private String userName = "User1";
	private int leafNumber = 0;
	
    private static final String INSERT_LEAF_QUERY = "INSERT INTO %s (userName,leafNumber) " + "VALUES ('%s', '%s');";
    private static final String UPDATE_LEAF_QUERY = "UPDATE %s SET leafNumber = '%s' where userName = '%s'";


	public Leaf(ResultSet resultSet) throws SQLException {
		userName = resultSet.getString("userName");
		leafNumber = resultSet.getInt("leafNumber");
	}
	
	public void setTaskName(String newTaskName) {
		userName = newTaskName;
	}
	
	public void setIsActive(int newleafNumber) {
		leafNumber = newleafNumber;
	}
	
	public String getTaskName() {
		return userName;
	}
	

	@Override
	public String getInsertQuery() {
		
		return String.format(INSERT_LEAF_QUERY, tableName, userName, leafNumber);
	}

	@Override
	public String getUpdateQuery(String userName) {
		
		String s =  String.format(UPDATE_LEAF_QUERY, tableName, leafNumber, userName);
		System.out.println(s);
		return s;
	}



}
