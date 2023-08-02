package application.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Leaf extends Data{
	
	private final static String tableName = "LEAF";
	private String taskFinishTime;
	private String date;
	private String taskName;
	private int leafGot;

	
	
    private static final String INSERT_LEAF_QUERY = "INSERT INTO %s (taskFinishTime, date, taskName, leafGot) " + "VALUES ('%s', '%s','%s', '%s');";
    private static final String UPDATE_LEAF_QUERY = "UPDATE %s SET leafNumber = '%s' where userName = '%s'";


	public Leaf(ResultSet resultSet) throws SQLException {
		taskFinishTime = resultSet.getString("taskFinishTime");
		date = resultSet.getString("date");
		taskName = resultSet.getString("taskName");
		leafGot = resultSet.getInt("leafGot");
		
	}
	
	public Leaf(String taskFinishTime, String date, String taskName, int leafGot) {
		this.taskName = taskName;
		this.date = date;
		this.taskFinishTime = taskFinishTime;
		this.leafGot = leafGot;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setTDate(String date) {
		this.date = date;
	}
	public void setTasFinishTime(String taskFinishTime) {
		this.taskFinishTime = taskFinishTime;
	}
	public void setIsActive(int leafGot) {
		this.leafGot = leafGot;
	}
	
	
	public String getTaskName() {
		return taskName;
	}
	
	public String getDate() {
		return date;
	}
	
	
	
	@Override
	public String getInsertQuery() {
		
		return String.format(INSERT_LEAF_QUERY, tableName, taskFinishTime, date, taskName, leafGot);
	}

	@Override
	public String getUpdateQuery(String taskFinishTime) {
		
		String s =  String.format(UPDATE_LEAF_QUERY, tableName, leafGot, taskFinishTime);
		System.out.println(s);
		return s;
	}



}
